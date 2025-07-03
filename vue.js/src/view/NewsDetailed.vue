<template>
    <div class="home-bg">
        <commonHeader></commonHeader>
        <div class="home">
            <div class="home-main">
                <el-row>
                    <el-col :span="12" class="flex-start">
                        <div class="back-btn" @click="toBack()" style="margin-bottom: 20px">戻る</div>
                    </el-col>
                </el-row>
                <div class="newsD-card">
                    <el-row style="text-align: center">
                        <span class="card-title-big">{{noticeData.noticeTitle}}</span>
                    </el-row>
                    <el-row style="margin-top: 50px">
                        <pre><span class="card-title-small">{{noticeData.noticeContents}}</span></pre>
                    </el-row>
                    <el-row style="text-align: center">
                        <img class="el-upload-list__item-thumbnail" v-if="picDialogVisible1" :src="noticeData.noticeAttachment1" alt="">
                    </el-row>
                    <el-row style="text-align: center">
                        <img class="el-upload-list__item-thumbnail" v-if="picDialogVisible2" :src="noticeData.noticeAttachment2" alt="">
                    </el-row>
                    <el-row style="text-align: center">
                        <img class="el-upload-list__item-thumbnail" v-if="picDialogVisible3" :src="noticeData.noticeAttachment3" alt="">
                    </el-row>
                </div>
            </div>
        </div>
        
        <commonFootter></commonFootter>
    </div>
</template>

<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { getNoticeDetil } from '@/api/user'
import { getUserInfo} from '@/utils/auth'
export default {
    name: "NewsDetailed",
    components: {
        commonHeader,
        commonFootter
    },
    data() {
        return {
            mode: 0,
            noticeData:'',
            noticeNumber: '',
            picDialogVisible1:false,
            picDialogVisible2:false,
            picDialogVisible3:false,
            noticePublicationStartDate: '',
            noticeTitle: '',
            noticeAttachment1 : '',
            noticeAttachment2 : '',
            noticeAttachment3 : ''
        }
    },
    created() {
        this.mode = this.$route.query.mode
        this.getNewsDetailed(this.$route.query.noticeNumber)
    },
    methods: {
      toBack() {
            switch(this.mode) {
                case '1' :
                this.$router.push({
                    path: '/toppage'
                })
                break;
                case '2' :
                this.$router.push({
                    path: '/newsList'
                })
                break;
                case '3' :
                if(getUserInfo().permissionKbn == "500" || getUserInfo().permissionKbn == "550" || getUserInfo().permissionKbn == "999"){
                    this.$router.push({
                        path: '/noticeMaintenance'
                    })
                }
                else{
                    this.$router.push({
                        path: '/toppage'
                    })
                }
                break;
                default:
                    this.$router.push({
                    path: '/toppage'
                })
                break;
            }
            
        },
      getNewsDetailed(number) {
        let data = {
            companyCode: getUserInfo().companyCode,
            noticeNumber:number
        }
        getNoticeDetil(data).then(res => {
            console.log(res.data)
            this.noticeData = res.data
            if(res.data.noticeAttachment1 != '' && res.data.noticeAttachment1 != null){
                this.picDialogVisible1 = true;
            }
            if(res.data.noticeAttachment2 != '' && res.data.noticeAttachment2 != null){
                this.picDialogVisible2 = true;
            }
            if(res.data.noticeAttachment3 != '' && res.data.noticeAttachment3 != null){
                this.picDialogVisible3 = true;
            }

        }).catch(error => {
          console.log('getInfo fail')
        });
      }
    }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.newsD-card {
    height: auto;
    min-height: 500px;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    // border: 1px #dcdcdc solid;
    border-radius: 6px;
    padding: 20px 100px;

    .list-card-text {
        height: auto;
        padding: 0 30px;
    }
}

.card-title-small {
    color: #444;
    line-height: 30px;

}

.btnArea {
    height: 90px;
    background-color: #fff;
    margin-top: 20px;
    padding: 0px 30px;
    position: sticky !important;
    bottom: 30px;
    z-index: 4;
    padding-top: 20px;
}

</style>
