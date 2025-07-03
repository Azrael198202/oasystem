<template>
    <div>
       <commonHeader></commonHeader>
       <div class="top-home">
         <div class="top-home-main">
            <div class="recognize-area">
                <div class="recognize-header">
                    <el-row>
                        <span class="card-title-big" style="line-height: 60px">パスワード更新</span>
                    </el-row>
                    <el-row style="margin-top: 30px">
                        <el-col :span="6">
                            <el-input placeholder="パスワード" v-model="password" style="margin-right: 10px"></el-input>
                        </el-col>
                        <el-col style="margin-left: 20px" :span="6">
                            <el-button type="primary" @click="pwreset()" plain>全員更新</el-button>
                        </el-col>
                    </el-row>
                    <el-row style="margin-top: 10px;color: #888">
                        <p>※ローカルのみで使う</p>
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
import { resetPassWordAllUser } from '@/api/user'
import { Message } from 'element-ui'
export default {
 name: 'Pwreset',
 components: {
   commonHeader,
   commonFootter
 },
 created() {

 },
 data () {
   return {
     password: '',
   }
 },
 methods: {
    pwreset() {
        if (this.password == '' || this.password == null) {
            Message({
                message: 'パスワードを入力してください',
                type: 'error',
                duration: 4 * 1000
            })
        } else {
            resetPassWordAllUser(this.password).then((res) => {
                console.log(res.data)
                Message({
                    message: '全員更新しました',
                    type: 'success',
                    duration: 4 * 1000
                })
                this.password = ''
            }).catch(error => {
                console.log(error)
            })
        }
    },
 }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.recognize-area {
    height: 760px;
    width: 100%;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 0px;
    border-radius: 6px;

    .recognize-header {
        padding: 0px 30px;
    }
}

.approval_route {
    padding: 20px;
    height: 350px;
    overflow-y: scroll
 }

 .option-title {
     font-size: 14px;
     line-height: 40px;
 }
</style>
