<template>
    <div class="home-bg">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="apply-type-area">
                    <el-row class="tac">
                        <!-- 申請リスト -->
                        <el-col :span="24">
                        <el-menu
                            style="height: 50px"
                            background-color="#545c64">
                            <el-menu-item index="1" @click="newStepChange(0)">
                                <span slot="title" class="apply-all-list"><i class="fas fa-clipboard-list"></i>&#0160;申請一覧</span>
                            </el-menu-item>
                        </el-menu>
                            <el-menu style="height: 600px"
                                :default-active="applyFlg"
                                @open="handleOpen"
                                @close="handleClose"
                                @select="handleSelect">
                                <!-- <el-menu-item type="success" index="1" @click="newStepChange(0)">
                                    <span slot="title" class="apply-all-list"><i class="fas fa-list-alt"></i>&#0160;申請一覧</span>
                                </el-menu-item> -->
                                <el-dropdown index="2" @command="holidayStepChange" style="height: 46px;">
                                    <span class="el-dropdown-holiday-link menu-icon">
                                        休暇申請<i class="el-icon-arrow-down el-icon--right"></i>
                                    </span>
                                    <el-dropdown-menu  slot="dropdown" class="leave-dropdown">
                                        <el-dropdown-item command="010">有給休暇申請　　</el-dropdown-item>
                                        <el-dropdown-item command="020">代休申請</el-dropdown-item>
                                        <el-dropdown-item command="030">特別休暇申請</el-dropdown-item>
                                        <el-dropdown-item command="040">夏季休暇申請</el-dropdown-item>
                                    </el-dropdown-menu>
                                </el-dropdown>

                                <el-menu-item index="4" @click="newStepChange(21)" class="apply-all-menu">
                                    <span class="other-apply" slot="title">休日出勤申請</span>
                                </el-menu-item>
                                <el-menu-item index="5" @click="newStepChange(22)" class="apply-all-menu">
                                    <span class="other-apply" slot="title">深夜残業申請</span>
                                </el-menu-item>
                                <el-menu-item index="3" @click="newStepChange(23)" class="apply-all-menu">
                                    <span class="other-apply" slot="title">振休出勤申請</span>
                                </el-menu-item>
                                <el-menu-item index="6" @click="newStepChange(31)" class="apply-all-menu">
                                    <span class="other-apply" slot="title">月末精算申請</span>
                                </el-menu-item>
                                <el-dropdown index="7" @command="allowanceStepChange" style="height: 46px;">
                                    <span class="el-dropdown-allowance-link menu-icon">
                                        手当申請<i class="el-icon-arrow-down el-icon--right"></i>
                                    </span>
                                    <el-dropdown-menu  slot="dropdown" class="leave-dropdown">
                                        <el-dropdown-item command="043">出張手当申請</el-dropdown-item>
                                        <el-dropdown-item command="042">リーダー手当申請</el-dropdown-item>
                                        <el-dropdown-item command="041">資格手当申請</el-dropdown-item>
                                        <el-dropdown-item command="040">慶弔見舞金申請</el-dropdown-item>
                                    </el-dropdown-menu>
                                </el-dropdown>
                                <!-- <el-menu-item index="11" @click="newStepChange(90)" class="apply-all-menu">
                                    <span class="other-apply" slot="title">その他申請</span>
                                </el-menu-item>  -->
                            </el-menu>
                            <!-- 戻るボタン -->
                            <el-row>
                                <el-col :span="24">
                                    <div class="back-btn" @click="closeA()" style="margin-top: 20px; margin-left: 60px">戻る</div>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
                <div>
                    <applyList v-bind:userData="userData" v-if="step == '0'" @changePage="stepChange"></applyList>
                    <paidHoliday v-bind:userData="userData" v-if="step == '020' && (holidayCategory == '010'|| holidayCategory == '011')"> </paidHoliday>
                    <dayoff v-bind:userData="userData" v-if="step == '020' && (holidayCategory == '020'|| holidayCategory == '021')" > </dayoff>
                    <specialHoliday v-bind:userData="userData" v-if="step == '020' && holidayCategory == '030'" > </specialHoliday>
                    <summerHoliday v-bind:userData="userData" v-if="step == '020' && holidayCategory == '040'" > </summerHoliday>
                    <!-- <substituteHoliday v-bind:userData="userData" v-if="step == '020' && (holidayCategory == '050'|| holidayCategory == '051')" > </substituteHoliday> -->
                    <exchangedWorkday v-bind:userData="userData" v-if="step == '023'"></exchangedWorkday>
                    <holidayWork v-bind:userData="userData" v-if="step == '021'"></holidayWork>
                    <overtime v-bind:userData="userData" v-if="step == '022'"></overtime>
                    <transportationExpenses v-bind:userData="userData" v-if="step == '031'"></transportationExpenses>
                    <allowanceBusinessTrip v-bind:userData="userData" v-if="step == '043'"></allowanceBusinessTrip>
                    <allowanceProject v-bind:userData="userData" v-if="step == '042'"></allowanceProject>
                    <allowanceQualification v-bind:userData="userData" v-if="step == '041'"></allowanceQualification>
                    <congratulatoryOrCondolencePayments v-bind:userData="userData" v-if="step == '040'"></congratulatoryOrCondolencePayments>
                    <!-- <otherApplications v-bind:userData="userData" v-if="step == '090'"></otherApplications> -->
                </div>
            </div>
        </div>
        <commonFootter></commonFootter>
    </div>
</template>

<script>
    import commonHeader from "@/layout/commonHeader.vue";
    import commonFootter from "@/layout/commonFootter.vue";
    import applyList from "./applyType/ApplyList.vue";
    import paidHoliday from "./applyType/PaidHoliday.vue";
    import dayoff from "./applyType/DayOff.vue";
    import specialHoliday from "./applyType/SpecialHoliday.vue";
    import summerHoliday from "./applyType/SummerHoliday.vue";
    // import substituteHoliday from "./applyType/SubstituteHoliday.vue";
    import exchangedWorkday  from "./applyType/ExchangedWorkday.vue";
    import holidayWork  from "./applyType/HolidayWork.vue";
    import overtime from "./applyType/Overtime.vue";
    import transportationExpenses from "./applyType/TransportationExpenses.vue";
    import allowanceBusinessTrip from "./applyType/AllowanceBusinessTrip.vue";
    import allowanceProject from "./applyType/AllowanceProject.vue";
    import allowanceQualification from "./applyType/AllowanceQualification.vue";
    import congratulatoryOrCondolencePayments from "./applyType/CongratulatoryOrCondolencePayments.vue";
    import otherApplications from "./applyType/OtherApplications.vue";
    import applyHeader from './component/applyHeader.vue';
    import { insertLeaveRequest, searchRequest, insertRequest, updateLaRequest, deleteRequest } from '@/api/requestList'
    import { getUserInfo, getUserDetailedInfo, setUserDetailedInfo } from '@/utils/auth'
    import { getInfo, getUserDetail } from '@/api/user'
    import { getCategory, getHoliday } from '@/api/category'
    import { getSelectWf, getWorkFlowList } from '@/api/workFlow'
    import { Message } from 'element-ui';
    import { getWfList, insertWf, deleteWf, updateWf, updateadmitWf, updatelastadmitWf } from '@/api/wfList'
    import { getRequestAttendanceByList } from '@/api/requestAttendanceList'
    import { sendreqMailApi } from '@/api/findPass'
    import { insertData,getRequestOvertime,deleteOvertime } from '@/api/overTime'
    import { deleteImageTemporary } from '@/api/transportationExpenses';




    export default {
        name: "ApplyAll",
        components: {
            commonHeader,
            commonFootter,
            applyHeader,
            applyList,
            paidHoliday,
            dayoff,
            specialHoliday,
            summerHoliday,
            // substituteHoliday,
            exchangedWorkday,
            holidayWork,
            overtime,
            transportationExpenses,
            allowanceBusinessTrip,
            allowanceProject,
            allowanceQualification,
            congratulatoryOrCondolencePayments,
            otherApplications,
        },
        created() {
            this.userInfoInit()
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            if(this.changebyFlg == "3"){
                this.stepChange(this.rowdata.requestTypeKbn,this.rowdata.holidayTypeKbn)
            }
            //初期表示された場合、テンプ画像を削除する
            //若しくは、月末精算（保存・申請ボタン押下後）から申請一覧へリフレッシュした時、テンプ画像を削除する
            deleteImageTemporary(getUserInfo().userId).then(res => {
                console.log(res)
            }).catch(error => {
                console.log(error)
            })
        },

        data() {
            return {
                applyFlg: '1',
                step: '0',
                rowdata: [],
                changebyFlg: '',
                holidayCategory: '',
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
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
                this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            },
            userInfoInit() {
                this.userData.userName = getUserInfo().userName
                this.userData.userId = getUserInfo().userId
                this.userData.departmentName = getUserDetailedInfo().departmentName
                this.userData.companyCode = getUserInfo().companyCode
                this.userData.permissionKbn = getUserInfo().permissionKbn
                console.log(this.userData)
            },
            //申請一覧切り替え
            stepChange: function(page,requestOverview) {
                if(page == '020'){
                    this.holidayCategory = requestOverview
                }
                this.step = page
            },
            //新規申請
            newStepChange(data) {
                sessionStorage.setItem("changeFlg",'1')
                this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
                this.step = data
                //サブメニュで「申請一覧ボタン」クリックした場合、テンプ画像を削除する
                if(data == 0){
                    deleteImageTemporary(getUserInfo().userId).then(res => {
                        console.log(res)
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            //休暇申請
            holidayStepChange: function(data) {
                sessionStorage.setItem("changeFlg",'1')
                this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
                this.holidayCategory=data
                this.step = '020'
            },
            //手当申請
            allowanceStepChange: function(data) {
                sessionStorage.setItem("changeFlg",'1')
                this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
                this.step = data
            },
            // トップページ戻るボタン
            closeA() {
                this.$router.push({
                    path: '/toppage'
                })
            }
        }
    }
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.el-menu-item {
    font-size: 16px !important;
    padding: 0 10px 0 30px !important;
}

.el-menu {
    border-right: none;
}

.el-menu-item.is-active {
    color: #409EFF;
}

.el-menu-item:focus, .el-menu-item:hover {
    outline: 0;
    background-color: .3s;
    color: .3s;

}

.apply-type-area {
    width: 200px;
    height: 740px;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    border-radius: 6px;
    padding: 20px 0px;
    margin-right: 10px;
}

.apply-area {
    width: 1070px;
    margin-right: 10px;
    height: 740px;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    border-radius: 6px;
    padding: 20px 0px;
}

.apply-leave {
    margin-top: 50px;
}

.option-box .el-input__inner {
    width: 300px;
}

.textarea-box .el-textarea__inner{
    width: 600px;
    height: 100px !important;
}
// 理由種類
.reason-option .el-input__inner {
    // border-radius: 6px !important;
    width: 300px;
}
// 休暇期間ボックス
.leave-term .el-input__inner {
    width: 300px !important;
}


.leave-btn {
    padding: 20px 120px 0 120px;
}

.el-submenu__title {
    padding-left: 30px !important;
    font-size: 16px;
}

// 休暇申請ドロップダウン
.el-dropdown-holiday-link {
    cursor: pointer;
    // color: #409EFF;
    margin-left: 30px;
    font-size: 16px;
    float: right;
    cursor: pointer;
    color: #444;
    line-height: 70px;
    font-weight: bold;
}
// 休暇申請ドロップダウン
.el-dropdown-allowance-link {
    cursor: pointer;
    // color: #409EFF;
    margin-left: 30px;
    font-size: 16px;
    float: right;
    cursor: pointer;
    color: #444;
    line-height: 70px;
    font-weight: bold;
}
.el-dropdown.is-active {
    color: #409EFF;
}

// 申請一覧
.apply-all-list {
    cursor: pointer;
    font-size: 16px;
    font-weight: bold;
    // color: #000000;
    color: #FFFFFF;
}

// 申請一覧メニュー
// .apply-all-menu {
//     cursor: pointer;
//     font-size: 16px;
//     font-weight: bold;
// }

.other-apply {
    cursor: pointer;
    font-size: 16px;
    font-weight: bold
}

.menu-icon .el-icon--right {
    margin-left: 55px;
}

.dialog-head .el-dialog__header {
    padding: 0px 20px 10px;
}

.rout {
    margin-right: 40px;
    padding: 10px;
    background-color: #f5f5f5;
    height: 500px;
    overflow-y: scroll
}

// 承認者検索一覧
.searchUserInfoArea {
    height: auto;
    max-height: 220px;
    overflow-y: scroll;
}
.approval-area {
    width:100%;
    height: auto;
    padding: 10px;
    margin: 10px 0;
    background-color: #f5f5f5;

    .approval-area-title {
        color: #888;
        line-height: 40px;
    }

    .approval-area-text {
        color: #444;
        font-weight: bold;
        line-height: 40px;
        margin-right: 20px;
        font-size: 16px;
    }
    .approval-area-notext {
        color: #fd5555;
        line-height: 40px;
        font-weight: bold;
    }
}

.searchUserInfo-btn {
    transition: 0.5s;
    width: 380px;
    height: 60px;
    box-shadow: 0 2px 10px 0 rgba(92, 92, 92, 0.2);
    background-color: #fff;
    border-radius: 6px;
    padding: 10px 20px;
    margin: 6px 10px 14px;
    cursor: pointer;
    border: 2px #fff solid;

    i {
        font-size: 18px;
        color: #888;
    }
}

.searchUserInfo-btn:hover {
    transition: 0.5s;
    box-shadow: 0 3px 20px 0 rgba(104, 104, 104, 0.3);
    border: 2px #ffa811 solid;
}

// 全日・午前・午後オプション
.leave-option {
    display: flex;
}
.work-time {
    margin-left: 90px;
}

.leave-num {
   display: flex;
   float: right;
   margin-right: 50px;
}

.leave-info {
    height: 35px;
    line-height: 35px;
}

.dayoff-info {
    margin-left: 30px;
}
</style>
