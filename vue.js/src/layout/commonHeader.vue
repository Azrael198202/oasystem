<template>
  <div class="header-wrap">
      <div class="header-main">
          <el-row align="right">
              <el-col :span="6" class="header-logo">
                  <!-- TODO:本番に使う 1/3 <img src="@/assets/images/logo.png" alt="" @click="logoClick()"> -->
                  <img src="@/assets/images/logo.png" alt="" @click="logoClick()">
              </el-col>
              <el-col :span="11" class="header-text">
                  <div class="header-text-span">
                      <span class="header-font-green">
                          夢、専念そしてチャレンジ精神で、お客様と共に成長する
                      </span>
                  </div>
                  <div class="header-text-span">
                      <span class="header-font-black">
                        With the dream ,focus and spirit to challenge ,grow together with our every valued client
                      </span>
                  </div>
              </el-col>
              <el-col :span="7" class="header-name flex-end exclusive">
<!-- 2021/08/04 ADD START -->
<!--
                <div class="orange-border-btn" style="margin: 18px 20px 0 0" @click="workStartBtn()">
                    出勤
                </div>
                <div class="green-border-btn" style="margin: 18px 30px 0 0" @click="workEndBtn()">
                    退勤
                </div>
-->
<!-- 2021/08/04 ADD END -->
                <div class="header-name-img" style="margin-right: 20px">
                    <img :src="imageUrl" alt="">
                </div>
                <el-dropdown>
                    <span class="dropdown-link">
                        {{userName}}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <!-- <el-dropdown-item>マイページ</el-dropdown-item> -->
                        <el-dropdown-item @click.native.prevent="manualopen()">マニュアル</el-dropdown-item>
                        <el-dropdown-item @click.native.prevent="workopen()">社内就業規則</el-dropdown-item>
                        <el-dropdown-item @click.native.prevent="salaryopen()">給与規定</el-dropdown-item>
                        <el-divider ></el-divider>
                        <el-dropdown-item @click.native.prevent="OpenProfile()">個人設定</el-dropdown-item>
                        <el-dropdown-item @click.native.prevent="OpenPaidLeaveHistory()">有休情報</el-dropdown-item>
                        <el-dropdown-item @click.native.prevent="OpenDayOffHistory()">代休情報</el-dropdown-item>
                        <el-divider></el-divider>
                        <el-dropdown-item @click.native.prevent="logout()">ログアウト</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>

              </el-col>
          </el-row>
      </div>
      <el-dialog
            title=""
            :visible.sync="dialogWork"
            width="400px"
            :close-on-click-modal="true"
            >
            <span>
                {{msg}}
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogWork = false">取 消</el-button>
                <el-button type="primary" @click=" workRest() ? workStartSet() : workEndSet()">確 定</el-button>
            </span>
        </el-dialog>
        <paidLeaveHistory v-bind:userData="userData" :HeaderFlg="true" ref="onPaidLeaveHistory"></paidLeaveHistory>
        <dayOffHistory v-bind:userData="userData" :HeaderFlg="true" ref="onDayOffHistory"></dayOffHistory>
  </div>
</template>

<script>
import pdf from 'vue-pdf'
import { mapGetters, Store } from 'vuex'
import { workStart, workEnd, workStartSetting, workEndSetting } from '@/api/attendance'
import { MessageBox, Message } from 'element-ui'
import { getUserInfo, getUserDetailedInfo } from '@/utils/auth'
import paidLeaveHistory from "../view/PaidLeaveHistory.vue";
import dayOffHistory from "../view/DayOffHistory.vue";
export default {
    components: {
            paidLeaveHistory,
            dayOffHistory,
        },
    created() {
        this.userInfoInit()
    },
    data() {
        return {
            imageUrl:'',
            msg: '',
            code: '',
            dialogWork: false,
            userName:  getUserDetailedInfo() !== undefined ? getUserDetailedInfo().firstName + ' ' +getUserDetailedInfo().lastName : '',
            userData: {
                userName: '',
                userId: '',
                departmentName: '',
                companyCode: '',
                permissionKbn: '',
            }
        }
    },
    methods: {
        workopen() {
            window.open('https://oa.leadingwin.net/static/%E5%B0%B1%E6%A5%AD%E8%A6%8F%E5%89%87.pdf');
            //window.open('https://oatest.leadingwin.net/static/%E5%B0%B1%E6%A5%AD%E8%A6%8F%E5%89%87.pdf');
            // window.open('http://localhost:8989/static/%E5%B0%B1%E6%A5%AD%E8%A6%8F%E5%89%87.pdf');
        },
        manualopen() {
            window.open('https://oa.leadingwin.net/static/社内管理システム操作手順書.pdf');
            //window.open('https://oatest.leadingwin.net/static/社内管理システム操作手順書.pdf');
            //window.open('http://localhost:8989/static/社内管理システム操作手順書.pdf');
        },
        /* 20240520 総務より追加*/
        salaryopen() {
            window.open('https://oa.leadingwin.net/static/給与規定.pdf');
            //window.open('https://oatest.leadingwin.net/static/給与規定.pdf');
            //window.open('http://localhost:8989/static/給与規定.pdf');
        },
        logoClick() {
            this.$router.push({
                path: '/toppage'
            })
        },
        async logout() {
            await this.$store.dispatch('user/logout');
            this.$router.push('/login')
        },
        workStartBtn() {
            let data = {
                companyCode: getUserInfo().companyCode,
                userId: getUserInfo().userId,
                workDateYm: ''
            }
            workStart(data).then(res => {
                if(res.status == 200) {
                    if(res.msg === 'I_0006') {
                        Message({
                            message: res.data,
                            type: 'error',
                            duration: 4 * 1000
                        })
                        return
                    }
                    if(res.msg !== '' && res.msg !== null && res.msg !== undefined) {
                        this.msg = res.data
                        this.code = res.msg
                        this.dialogWork = true
                    }else {
                        Message({
                            message: '出勤時間を記録しました。本日もよろしくお願いします。',
                            type: 'success',
                            duration: 4 * 1000
                        })
                    }
                } else {
                    Message({
                        message: '出勤失敗しました',
                        type: 'error',
                        duration: 4 * 1000
                    })
                }
                // Message({
                //     message: '出勤成功しました',
                //     type: 'success',
                //     duration: 4 * 1000
                // })
            }).catch(error => {
                console.log(error)
            })
        },

        workRest() {
            if(this.code == 'I_0008' || this.code == 'I_0009' || this.code == 'I_0010') {
                return false
            }
            return true
        },

        workEndBtn() {
            let data = {
                companyCode: getUserInfo().companyCode,
                userId: getUserInfo().userId,
                workDateYm: this.dateSum()
            }
            workEnd(data).then(res => {
              if(res.status == 200) {
                if (res.msg === 'I_0006' || res.msg === 'I_0007'){
                  Message({
                            message: res.data,
                            type: 'error',
                            duration: 4 * 1000
                        })
                  return

                }else if (res.msg !== '' && res.msg !== null && res.msg !== undefined) {
                    this.msg = res.data
                        this.code = res.msg
                        this.dialogWork = true
                }else {
                        Message({
                            message: '退勤時間を記録しました。お疲れ様でした。',
                            type: 'success',
                            duration: 4 * 1000
                        })
                }
              } else if(res.status == 602) {
                  Message({
                            message: res.msg,
                            type: 'error',
                            duration: 4 * 1000
                        })
                }
              else {
                    Message({
                        message: '退勤失敗しました',
                        type: 'error',
                        duration: 4 * 1000
                    })
              }
                // Message({
                //     message: '退勤時間を記録しました。お疲れ様でした。',
                //     type: 'success',
                //     duration: 4 * 1000
                // })
            }).catch(error => {
                console.log(error)
            })
        },

        dateSum() {
            let nowDate = new Date();
            let year = nowDate.getFullYear();
            let month = nowDate.getMonth() + 1;
            let iMonth = ''
            if(String(month).length == 1) {
                iMonth = '0' + month;
            }else {
                iMonth = month;
            }
            return String(year) + iMonth
        },

        workStartSet() {
            let data = {
                companyCode: getUserInfo().companyCode,
                userId: getUserInfo().userId,
                workDateYm: this.code
            }
            workStartSetting(data).then(res => {
                this.dialogWork = false
                Message({
                    message: '出勤時間を記録しました。本日もよろしくお願いします。',
                    type: 'success',
                    duration: 4 * 1000
                })
            }).catch( error => {
                this.dialogWork = false
                Message({
                    message: '出勤失敗しました',
                    type: 'error',
                    duration: 4 * 1000
                })
            })
        },

        workEndSet() {
            let data = {
                companyCode: getUserInfo().companyCode,
                userId: getUserInfo().userId,
                workDateYm: this.code
            }
            workEndSetting(data).then(res => {
                this.dialogWork = false
                if (res.status == 602){
                    Message({
                        message: res.msg,
                        type: 'error',
                        duration: 4 * 1000
                    })
                    return
                }
                Message({
                    message: '退勤時間を記録しました。お疲れ様でした。',
                    type: 'success',
                    duration: 4 * 1000
                })
            }).catch( error => {
                this.dialogWork = false
                Message({
                    message: '退勤失敗しました',
                    type: 'error',
                    duration: 4 * 1000
                })
            })
        },

        userInfoInit() {
            this.userData.userName = getUserInfo().userName
            this.userData.userId = getUserInfo().userId
            this.userData.departmentName = getUserDetailedInfo().departmentName
            this.userData.companyCode = getUserInfo().companyCode
            this.userData.permissionKbn = getUserInfo().permissionKbn
            this.imageUrl = getUserInfo().imageUrl && getUserInfo().imageUrl !== '' ? getUserInfo().imageUrl :  require('@/assets/images/man.png');
            console.log(this.userData)
        },

        OpenPaidLeaveHistory(){
            this.$refs.onPaidLeaveHistory.paidLeaveHistoryOpen()
        },

        OpenProfile(){
            this.$router.push({
                path: '/personalInfo',
                query: { userId: this.currentUserId,userName: this.currentUserName },
            })  
        },

        OpenDayOffHistory(){
            this.$refs.onDayOffHistory.dayOffHistoryOpen()
        }
    }
}
</script>

<style lang="scss">
@import '../style/index.scss';

.v-modal {
    z-index: 1 !important;
}
.el-divider{
    display: block;
    height: 1px;
    width: 80% !important;
    margin: 8px !important;
    padding: 0 10px !important;
}

.el-dropdown-menu{
    padding: 10px 10px !important;
    margin: 5px 0 !important;
    background-color: #FFF;
    border: 1px solid #EBEEF5;
}

.dropdown-link {
    float: right;
    cursor: pointer;
    color: #444;
    line-height: 70px;
    font-weight: bold;
}

.el-icon-arrow-down {
    font-size: 12px;
}

.header-wrap {
//   width: 100%;
  min-width: 1300px;
  height: 70px;
  background-color: #fff;
  box-shadow: 0 3px 23px 0 #95959530;
  z-index: 5;
//   position: fixed;
//   top: 0;

  .header-main {
      width: 1300px;
      margin: 0 auto;

      .header-logo {

          img {
              margin-top: 20px;
              width:260px;
              cursor: pointer;
          }
      }

      .header-text-span {
          height: 30px;

        &:first-child {
            margin-top: 6px;
        }

        .header-font-green {
            font-size: 12px;
            line-height: 28px;
            font-weight: bold;
            color: #3a9955;
            margin-top: 10px;
        }

        .header-font-black {
            font-size: 12px;
            line-height: 20px;
            font-weight: bold;
            color: #444;
        }
      }

      .header-name {
          height: 70px;

          .header-name-img {
            // margin-right: 14px;
            margin-top: 12px;
            width: 44px;
            height: 44px;
            border-radius: 50%;
            background-color: #fff;
            box-shadow: 0 4px 6px 0px rgba(83, 83, 83, 0.274);

            img {
                width: 44px;
                height: 44px;
            }
          }
      }
  }
}

</style>
