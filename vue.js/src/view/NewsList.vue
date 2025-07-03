<template>
    <div class="home-bg">
        <commonHeader></commonHeader>
        <div class="home">
            <div class="home-main">
                <div class="list">
                    <div style="margin-top: 40px">
                        <div v-for="(item, index) in noticeData" :key="index">
                            <el-row v-if="newsFlg(item.noticePublicationEndDate)" :gutter="50" class="news-card">
                                <el-col :span="4" style="text-align: right;">
                                    <div class="news-time-d">{{ item.noticePublicationStartDate.split(' ')[0].split('/')[2] }}</div>
                                    <div class="news-time-ym">{{ item.noticePublicationStartDate.split(' ')[0].split('/')[0] + '/' + item.noticePublicationStartDate.split(' ')[0].split('/')[1] }}</div>
                                    <div v-if="newShow(item.noticePublicationStartDate,item.noticeNewPeriod)" class="news-time-new">new</div>
                                </el-col>
                                <el-col :span="20">
                                    <span class="news-title" @click="toNewsDetailed(item.noticeNumber)">{{ item.noticeTitle }}</span>
                                    <span class="news-title-small">
                                      {{ item.noticeContents.substring(0,150) + '...' }}
                                    </span>
                                    <span class="news-title-more" @click="toNewsDetailed(item.noticeNumber)">
                                        READ MORE
                                    </span>
                                </el-col>
                            </el-row>
                        </div>
                        <el-row class="btnArea">
                            <el-col :span="12" class="flex-start">
                                <div class="back-btn" @click="toTop()">戻る</div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>
        </div>
        <commonFootter></commonFootter>
    </div>
</template>

<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { getNoticeList } from '@/api/user'
import { getUserInfo} from '@/utils/auth'
export default {
    name: "NewsList",
    components: {
        commonHeader,
        commonFootter
    },
    data() {
        return {
            noticeData: []
        }
    },
    created() {
        this.getNotice()
    },
    methods: {
        newsFlg (endTime) {
            let timestamp = new Date().getTime()
            let time = new Date(endTime).getTime();
            return timestamp < time
        },
        newShow (startTime, newPeriod) {
            let timestamp = new Date().getTime()
            let time = new Date(startTime).getTime();
            console.log(timestamp)
            console.log(time)
            console.log(time + newPeriod * 24 * 60 * 60 * 1000)
            console.log(timestamp > (time + newPeriod * 24 * 60 * 60 * 1000))
            return timestamp < (time + newPeriod * 24 * 60 * 60 * 1000)
        },
        toTop() {
            this.$router.push({
                path: '/toppage'
            })
        },
        toNewsDetailed(number) {
            this.$router.push({
                path: '/NewsDetailed',
                query: { noticeNumber: number, mode: 2 },
            })
        },
        getNotice() {
            let data = {
                companyCode: getUserInfo().companyCode
            }

            getNoticeList(data).then(res => {
                console.log(res.data)
                this.noticeData = res.data
            }).catch(error => {
                console.log('getInfo fail')
            });
        }
    }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.list {
    height: auto;
    background-color: #fff;
    // border: 1px #dcdcdc solid;
    box-shadow: 0 3px 23px 0 #95959530;
    border-radius: 6px;
    padding: 20px 30px;

    .news-card {
        padding: 20px 0px;
        height: 200px;
        width: 100%;
        margin-bottom: 90px;
    }

    .news-time-d {
        font-family: "Microsoft YaHei";
        font-size: 46px;
        font-weight: bolder;
        color: #888888;
        line-height: 58px;
    }

    .news-time-ym {
        // margin-right: 5px;
        // display: block;
        font-size: 14px;
        font-weight: bold;
        color: #acacac;
    }

    .news-time-new {
        margin-top: 6px;
        float: right;
        background-color: #fd5555;
        height: 20px;
        width: 54px;
        color: #fff;
        font-size: 14px;
        line-height: 18px;
        text-align: center;
    }

    .news-title {
        font-size: 28px;
        font-weight: bolder;
        color: #444;
        cursor: pointer;
    }

    .news-title:hover {
        text-decoration: underline
    }

    .news-title-small {
        margin-top: 9px;
        display: block;
        font-size: 14px;
        // font-weight: bold;
        color: #888;
    }

    .news-title-more {
        width: 90px;
        margin-top: 30px;
        display: block;
        font-size: 14px;
        font-weight: bold;
        color: #444;
        cursor: pointer;
        line-height: 30px;

    }

    .news-title-more:hover {
        border-bottom: 2px #444 solid;
    }

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
