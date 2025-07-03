<template>
    <div class="home-bg">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="btnA-area">
                    <div v-for="(item,index) in buttonList" :key="index">
                        <el-col class="btn-area-block" v-if="item.availableFlg == '1'">
                            <router-link :to="item.menuDispUrl == null ? '' : item.menuDispUrl">
                                <el-button type="primary"
                                    :style="[
                                    {borderColor: (item.buttonColor)},
                                    {fontSize: (item.fontSize + 'px')},
                                    {margin: '6px 0 0 6px'},
                                    {height: '130px'},
                                    {width: '130px'},
                                    {color: (item.fontColor)},
                                    {backgroundColor:(item.buttonColor)},
                                    {boxShadow: ('0 4px 10px 0' + item.buttonColor + '80')},
                                    ]"
                                    class="btn-position">
                                    <div class="sum-icon" v-if="item.menuButtonName === '承認一覧'">{{approvalSum}}</div>
                                    <div class="btn-icon-position" style="font-size: 40px"><i :class="item.menuIcon"></i></div>
                                    <div class="btn-text-position">{{item.menuButtonName}}</div>
                                </el-button>
                            </router-link>
                        </el-col>
                    </div>
                </div>
                <div class="todo-area">
                    <div class="flex-title">
                        <div class="top-title">TO DO</div>
                        <div class="top-title-small">もっと見る</div>
                    </div>
                    <el-row>
                        <div style="margin-top:60px;text-align: center;font-size: 16px;color: #888;">工事中</div>
                    </el-row>
                </div>
                <div class="right-area">
                    <el-row class="statistics-area">
                        <el-row>
                            <div class="flex-title">
                                <div class="top-title">統計</div>
                                <el-dropdown>
                                    <span class="el-dropdown-link">
                                        to do<i class="el-icon-arrow-down el-icon--right"></i>
                                    </span>
                                    <el-dropdown-menu slot="dropdown">
                                        <el-dropdown-item>勤怠申請</el-dropdown-item>
                                        <el-dropdown-item>慶弔申請</el-dropdown-item>
                                        <el-dropdown-item>交通費申請</el-dropdown-item>
                                    </el-dropdown-menu>
                                </el-dropdown>
                            </div>
                            <el-row>
                                <div style="margin-top:60px;text-align: center;font-size: 16px;color: #888;">工事中</div>
                            </el-row>
                        </el-row>
                    </el-row>
                    <el-row class="info-area">
                        <div class="flex-title" style="margin-bottom: 20px">
                            <div class="top-title">お知らせ</div>
                            <div class="top-title-small" @click="toNewsList()">もっと見る</div>
                        </div>
                        <div v-for="(item, index) in noticeData" :key="index">
                            <!-- 2022/6/17 加賀田　「&& index < 7」を追加 -->
                          <el-col v-if="newsFlg(item.noticePublicationEndDate) && index < 11 " :span='24' style="margin-top:10px">
                            <el-row>
                                <el-col :span='5' class="my-info1">
                                    <span class="my-info-text1">{{ item.noticePublicationStartDate.split(' ')[0] }}</span>
                                </el-col>
                                <el-col :span='2' class="my-info1">
                                    <span v-if="newShow(item.noticePublicationStartDate,item.noticeNewPeriod)" class="new-text red-text">new</span>
                                    <span v-if="!newShow(item.noticePublicationStartDate,item.noticeNewPeriod)" style="opacity: 0" class="new-text red-text">new</span>
                                </el-col>
                                <el-col :span='16' class="my-info1">
                                    <span class="my-info-text" @click="toNewsDetailed(item.noticeNumber)">{{ item.noticeTitle }}</span>
                                </el-col>
                            </el-row>
                          </el-col>
                        </div>
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
import { getInfo, getNoticeList, getButton } from '@/api/user'
import { getSelectWf } from '@/api/workFlow'
import { getUserInfo, setUserDetailedInfo } from '@/utils/auth'

export default {
    name: "Toppage",
    components: {
        commonHeader,
        commonFootter
    },
    created() {
        sessionStorage.removeItem("resume-search-data")
        sessionStorage.removeItem("recognize-serch-data")
        this.getRequest()
        this.getUserInfo()
        this.getNotice()
    },
    data() {
        return {
            userDetailedInfo: '',
            noticeData:[],
            noticeNumber: '',
            noticePublicationStartDate: '',
            noticeTitle: '',
            approvalSum: 0,
            buttonList:[]
        }
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
        getRequest () {
            let data = {
                companyCode: getUserInfo().companyCode,
                reqAppUserId: getUserInfo().userId,
                wfStatusKbn: '020',
                requestTypeKbn: ''
            }
            getSelectWf(data).then(res => {
                console.log(res)
                this.approvalSum = res.data.length
            }).catch(error => {

            })
        },
        onServiceList() {
            this.$router.push({
                path: '/attendance'
            })
        },
        getUserInfo() {
            let data1 = {
                companyCode: getUserInfo().companyCode,
                permissionKbn: getUserInfo().permissionKbn
            }
            getButton(data1).then(res => {
                console.log(res.data)
                this.buttonList = res.data
            }).catch(error => {
                console.log('getButton fail')
            });
            let data = {
                companyCode: getUserInfo().companyCode,
                userId: getUserInfo().userId
            }
            getInfo(data).then(res => {
                console.log(res.data)
                setUserDetailedInfo(res.data)
            }).catch(error => {
                console.log('getInfo fail')
            });
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
        },
        toNewsList() {
            this.$router.push({
                path: '/newsList'
            })
        },
        toNewsDetailed(number) {
            this.$router.push({
                path: '/NewsDetailed',
                query: { noticeNumber: number, mode: 1 },
            })
        }
    }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.btnA-area {
    display: flex;
    // align-items: center;
    justify-content: start;
    align-content: flex-start;
    // flex-flow:row wrap;
    flex-wrap: wrap;
    width: 340px;
    height: 760px;
    background-color: #fff;
    // border: 1px #dcdcdc solid;
    border-radius: 6px;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 30px;
    margin-right: 20px;

    .btn-area-block {
        width: 144px;
        height: 144px;
        margin-bottom: 10px;
    }

    .el-button {
        white-space: pre-wrap !important;
        line-height: 20px;
    }
}

.todo-area {
    width: 460px;
    margin-right: 20px;
    height: 760px;
    background-color: #fff;
    // border: 1px #dcdcdc solid;
    border-radius: 6px;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 20px;
}

.right-area {
    width: 490px;
    height: 700px;
}

.statistics-area {
    width: 480px;
    height: 260px;
    background-color: #fff;
    // border: 1px #dcdcdc solid;
    border-radius: 6px;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 20px;
    margin-bottom: 20px;

    .el-dropdown-link {
        line-height: 22px !important;
        color: #888;
        font-weight: normal;
    }
}

.flex-title {
    display: flex;
    justify-content: space-between;
}

.info-area {
    width: 480px;
    height: 520px;
    background-color: #fff;
    // border: 1px #dcdcdc solid;
    border-radius: 6px;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 20px;
}

.top-title {
    font-size: 18px;
    color: #444;
    font-weight: bold;
    line-height: 22px;
}

.top-title-small {
    font-size: 14px;
    color: #888;
    font-weight: normal;
    cursor: pointer;
}

.top-title-small:hover {
    color: #4685ff;
}
.my-info1 {
    font-size: 14px;
    white-space:nowrap;
    overflow:hidden;
    text-overflow:ellipsis;
    .my-info-title {
        color: #888;
        // font-weight: bold;
        line-height: 30px;
    }

    .my-info-text {
        color: #444;
        cursor: pointer;
        font-weight: bold;
        line-height: 30px;

    }
    .my-info-text1 {
        color: #888;
        // font-weight: bold;
        line-height: 30px;
    }

    .new-text {
        font-weight: bold;
        line-height: 26px;
    }
}

.text-wrapper {
  word-break: break-all;
  word-wrap: break-word
}
.btn-position {
    position: relative;
}
.sum-icon {
    height: 30px;
    width: 30px;
    line-height: 30px;
    background-color: #fd5555;
    position: absolute;
    border-radius: 50%;
    top: -10px;
    right: -10px;
}
.btn-icon-position {
    // position: absolute;
    // top: 16px;
    // left: 50px;
    margin: 0 auto;
    margin-top: -30px;
}
.btn-text-position {
    height: 40px;
    width: 90px;
    position: absolute;
    bottom: 10px;
    left: 20px;
}
</style>
