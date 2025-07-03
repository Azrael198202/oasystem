<template>
    <div>
        <commonHeader></commonHeader>
        <div class="home">
            <div class="home-main">
                <div class="list-card">
                    <div class="list-card-text">
                        <el-row>
                            <el-col>
                                <span class="card-title-big">出退勤入力</span>
                            </el-col>
                        </el-row>
                        <el-row v-if="tcSelectFlg" style="margin: 40px 0 10px">
                            <el-row>
                                <el-col>
                                    <div class="flex-between">
                                        <div class="flex-start">
                                            <div class="my-info">
                                                <span class="my-info-title">グループ名：</span>
                                                <el-select v-model="selGroupCode" placeholder="" @change="getGroupMemberInfo()">
                                                    <el-option
                                                        v-for="item in groupList"
                                                        :key="item.groupCode"
                                                        :label="item.groupName"
                                                        :value="item.groupCode"
                                                        >
                                                        {{item.groupName}}
                                                    </el-option>
                                                </el-select>
                                            </DIV>
                                        </DIV>
                                    </DIV>
                                </el-col>
                            </el-row>
                            <el-row><el-col>&nbsp;</el-col></el-row>
                            <el-row>
                                <el-col>
                                    <div class="flex-between">
                                        <div class="flex-start">
                                            <div class="my-info">
                                                <span class="my-info-title">社員名：</span>
                                                <el-select v-model="selGroupUserId" placeholder="" @change="getUser()">
                                                    <el-option
                                                        v-for="item in groupMemberList"
                                                        :key="item.groupUserId"
                                                        :label="item.groupUserName"
                                                        :value="item.groupUserId"
                                                        >
                                                        {{item.groupUserName}}
                                                    </el-option>
                                                </el-select>
                                            </div>
                                            <div class="my-info">
                                                <span class="my-info-title">社員番号：</span>
                                                <span class="my-info-text">{{ selAgentCode }}</span>
                                            </div>
                                            <div class="my-info">
                                                <span class="my-info-title">部門：</span>
                                                <span class="my-info-text">{{ selDepartmentName }}</span>
                                            </div>
                                            <div>
                                                <el-tooltip class="item" effect="dark" content="詳細情報" placement="top">
                                                    <div class="my-info-all" @click="userSettingInfoOpen()">
                                                        <i class="fas fa-info"></i>
                                                    </div>
                                                </el-tooltip>
                                            </div>
                                        </div>
                                    </div>
                                </el-col>
                            </el-row>
                        </el-row>
                        <el-row v-else style="margin: 40px 0 10px">
                            <div class="flex-between">
                                <div class="flex-start">
                                    <div class="my-info">
                                        <span class="my-info-title">社員名：</span>
                                        <span class="my-info-text">{{ selUserName }}</span>
                                    </div>
                                    <div class="my-info">
                                        <span class="my-info-title">社員番号：</span>
                                        <span class="my-info-text">{{ selAgentCode }}</span>
                                    </div>
                                    <div class="my-info">
                                        <span class="my-info-title">部門：</span>
                                        <span class="my-info-text">{{ selDepartmentName }}</span>
                                    </div>
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="詳細情報" placement="top">
                                            <div class="my-info-all" @click="userSettingInfoOpen()">
                                                <i class="fas fa-info"></i>
                                            </div>
                                        </el-tooltip>
                                    </div>
                                </div>
                            </div>
                        </el-row>
                        <el-row v-if="colorFlg == 1">
                            <el-col :span="24">
                                <div class="time-text" style="text-align: center; font-size: 26px; font-weight: bold; !important">
                                    <font color="#fd5555">{{nowDate}} {{nowTime}}</font>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row v-else-if="colorFlg == 2">
                            <el-col :span="24">
                                <div class="time-text" style="text-align: center; font-size: 26px; font-weight: bold; !important">
                                    <font color="#4685ff">{{nowDate}} {{nowTime}}</font>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row v-else>
                            <el-col :span="24">
                                <div class="time-text" style="text-align: center; font-size: 26px; font-weight: bold; !important">
                                    <font color="#000000">{{nowDate}} {{nowTime}}</font>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24" class="time-area mt-10">
                                <table>
                                    <tr>
                                        <td width="150px" align="right">
                                            <div class="orange-border-btn" style="width: 140px; height: 60px;line-height: 60px; font-size: 20px;" @click="workStartBtn()">
                                                出勤
                                            </div>
                                        </td>
                                        <td width="50px"></td>
                                        <td width="150px" align="left">
                                            <div class="green-border-btn" style="width: 140px; height: 60px;line-height: 60px; font-size: 20px;" @click="workEndBtn()">
                                                退勤
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24" align="center">
                                <table>
                                    <tr height="20px"><td></td></tr>
                                    <tr height="50px">
                                        <td width="150px" align="right">
                                            <el-input v-model="workStart1" v-if="visitFlg1" placeholder="出勤１" maxlength="5" readOnly="true"><span>{{ workStart1 }}</span></el-input>
                                        </td>
                                        <td width="50px"></td>
                                        <td width="150px" align="left">
                                             <el-input v-model="workEnd1" v-if="visitFlg1" placeholder="退勤１" maxlength="5" readOnly="true"><span>{{ workEnd1 }}</span></el-input>
                                        </td>
                                    </tr>
                                    <tr height="50px">
                                        <td width="150px" align="right">
                                            <el-input v-model="workStart2" v-if="visitFlg2" placeholder="出勤２" maxlength="5" readOnly="true"><span>{{ workStart2 }}</span></el-input>
                                        </td>
                                        <td width="50px"></td>
                                        <td width="150px" align="left">
                                             <el-input v-model="workEnd2" v-if="visitFlg2" placeholder="退勤２" maxlength="5" readOnly="true"><span>{{ workEnd2 }}</span></el-input>
                                        </td>
                                    </tr>
                                    <tr height="50px">
                                        <td width="150px" align="right">
                                            <el-input v-model="workStart3" v-if="visitFlg3" placeholder="出勤３" maxlength="5" readOnly="true"><span>{{ workStart3 }}</span></el-input>
                                        </td>
                                        <td width="50px"></td>
                                        <td width="150px" align="left">
                                             <el-input v-model="workEnd3" v-if="visitFlg3" placeholder="退勤３" maxlength="5" readOnly="true"><span>{{ workEnd3 }}</span></el-input>
                                        </td>
                                    </tr>
                                </table>
                            </el-col>
                        </el-row>
                        <el-row class="btn-area">
                            <el-col class="flex-start">
                                <div class="back-btn" @click="closeA()">戻る</div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>
        </div>

        <el-dialog
            title="詳細情報"
            :visible.sync="dialogUserInfo"
            width="640px"
            :close-on-click-modal="true"
            class="dialogUserInfoClass"
            >
            <el-row>
                <el-col :span='6' class="my-info">
                    <span class="name-title">{{ selUserName }}</span>
                    <span class="my-info-title">様</span>
                </el-col>
                <el-col :span='6' class="my-info" style="margin-top: 10px">
                   <span class="my-info-title">社員番号：</span>
                    <span class="my-info-text">{{ selAgentCode }}</span>
                </el-col>
                <el-col :span='6' class="my-info" style="margin-top: 10px">
                    <span class="my-info-title">部門：</span>
                    <span class="my-info-text">{{ selDepartmentName }}</span>
                </el-col>
            </el-row>

            <el-row class="info-card">
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">計算単位</div>
                        <span class="my-info-text" style="margin-left: 30px">{{ settingInfo.workEveryTime == '' || settingInfo.workEveryTime == null ? '' : settingInfo.workEveryTime }}(分)</span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">所定労働時間</div>
                        <span class="my-info-text" style="margin-left: 30px">{{ settingInfo.workPrescribedTime }}</span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">始業終業</div>
                        <span class="my-info-text" style="margin-left: 30px">{{ settingInfo.workStartTime }} ~ {{ settingInfo.workEndTime }}</span>

                    </el-col>
                </el-row>
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">休憩時間</div>
                        <span class="my-info-text" style="margin-left: 30px" v-if="restFlg1">{{ settingInfo.workBreakStart1 }} ~ {{ settingInfo.workBreakEnd1 }}</span>
                        <span class="my-info-text" style="margin-left: 14px" v-if="restFlg2">{{ settingInfo.workBreakStart2 }} ~ {{ settingInfo.workBreakEnd2 }}</span>
                        <span class="my-info-text" style="margin-left: 14px" v-if="restFlg3">{{ settingInfo.workBreakStart3 }} ~ {{ settingInfo.workBreakEnd3 }}</span>
                    </el-col>
                </el-row>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogUserInfo = false">キャンセル</el-button>
            </span>
        </el-dialog>

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

        <commonFootter></commonFootter>
    </div>
</template>

<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { getAttendanceDetail,　getAtndStting, workStart, workEnd, workStartSetting, workEndSetting } from '@/api/attendance'
import { getUserPermission } from '@/api/clockInAndOut'
import { getGroup, getGroupMember } from '@/api/group'
import { getInfo, getUserDetail } from '@/api/user'
import { getCategory, getHoliday } from '@/api/category'
import { getUserInfo, getUserDetailedInfo } from '@/utils/auth'
import { formatDate } from '@/utils/formatDate'
import Papa from 'papaparse'

import Encoding from 'encoding-japanese';
import { Message } from 'element-ui';

export default {
    name: "clockInAndOut",
    components: {
        commonHeader,
        commonFootter,
        Papa
    },

    filters: {
        formatDate : function (time) {
            let date = new Date(time)
            return formatDate(date, 'hh:mm')
        }
    },

    created() {
        // ログインユーザ情報取得
        this.userInfoInit()

        // ユーザ情報全件取得
        this.getUserList()

        // 現在の年月日時分秒を取得
        this.updateTime()

        // 休日情報を取得
        this.getHoliday()

        // 日時表示判定
        this.setDateTimeClolor()

        // 出退勤回数取得
        this.getCategory()

        // メニューマスタ取得
        this.getMenuInfo()

    },

    data() {
        const validateFindUsername = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('必須入力です。'))
            } else {
                callback()
            }
        }
        return {
            iYear: '',
            iMonth: '',
            iDay: '',
            iWeek: '',
            nowTime: '',
            nowDate: '',
            mNowDate: '',
            holidayFlg: false,
            colorFlg: 0,
            companyCode_user: '',
            userName:  '',
            agentCode:  '',
            departmentName:  '',
            permissionKbn: '',
            selUserName:  '',
            selAgentCode:  '',
            selDepartmentName:  '',
            selGroupUserId: '',
            tcSelectFlg: false,
            dialogUserInfo: false,
            textareaValue: '',
            textareaValue_bk: '',
            dayValue: '',
            restFlg1: true,
            restFlg2: true,
            restFlg3: true,
            workStart1: '',
            workEnd1: '',
            workStart2: '',
            workEnd2: '',
            workStart3: '',
            workEnd3: '',
            visitFlg1: false,
            visitFlg2: false,
            visitFlg3: false,
            settingInfo: [],
            holidayDate: [],
            groupList: [],
            selGroupCode: '',
            selGroupName: '',
            groupMemberList: [],
            userList: [],
            msg: '',
            code: '',
            dialogWork: false
        }
    },

    mounted: function()  {
　　　　let timerId = setInterval(this.updateTime, 1000); 
　　}, 

    methods:{
        // ログインユーザ情報取得
        userInfoInit() {
            this.userName = getUserInfo().userName
            this.agentCode = getUserInfo().userId
            this.departmentName = getUserDetailedInfo().departmentName
            this.companyCode_user = getUserInfo().companyCode
            this.permissionKbn = getUserDetailedInfo().permissionKbn
        },

        // ユーザ情報取得
        getUser() {
            let data = {
                companyCode: this.companyCode_user,
                userId: this.selGroupUserId
            }
            getInfo(data).then(res => {
                if(res.data == '' || res.data == null || res.data.length == 0) {
                    Message({
                        message: 'ユーザ情報データ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                } else {
                    this.selUserName = res.data.firstName + "　" + res.data.lastName
                    this.selAgentCode = res.data.userId
                    this.selDepartmentName = res.data.departmentName
                    this.getSettingInfo()
                    this.getAttendanceInfo()
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
        },

        // ユーザ情報全件取得
        getUserList() {
                let data = {
                    companyCode: this.companyCode_user,
                    userId: ""
                }
                getUserDetail(data).then( res => {
                    this.userList = res.data
                }).catch(error => {
                    console.log(error);
                });
        },

        // メニューマスタ取得
        getMenuInfo() {
            let data = {
                companyCode: this.companyCode_user,
                permissionKbn: this.permissionKbn
            }
            getUserPermission(data).then(res => {
                if(res.data == '' || res.data == null || res.data.length == 0) {
                    this.tcSelectFlg = false
                    Message({
                        message: 'メニューマスタデータ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                } else {
                    this.tcSelectFlg = false
                    for (let i = 0; i < res.data.length; i++) {
                        if (res.data[i].menuNumber == "01") {
                            if (res.data[i].optionValue1 == "1") {
                                this.tcSelectFlg = true
                                continue
                            }
                        }
                    }
                    
                    // 権限による処理分岐
                    if (this.tcSelectFlg == true) {
                        // グループ情報取得
                        this.getGroupInfo()

                        // グループ未選択時の状態を設定
                        this.groupMemberList = []
                        this.selUserName = ""
                        this.selAgentCode = ""
                        this.selDepartmentName = ""

                    } else {
                        // 対象ユーザ情報設定
                        this.selUserName = this.userName
                        this.selAgentCode = this.agentCode
                        this.selDepartmentName = this.departmentName

                        // グループ関連項目を初期化
                        this.groupList = []
                        this.groupMemberList = []

                        // 勤怠設定情報を取得
                        this.getSettingInfo()

                        // 当日の勤怠表情報を取得
                        this.getAttendanceInfo()

                    }
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
        },

        // グループ情報取得
        getGroupInfo() {
            let data = {
                companyCode: this.companyCode_user
            }
            getGroup(data).then(res => {
                this.groupList = []
                if(res.data == '' || res.data == null) {
                    Message({
                        message: 'グループ情報データ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                } else {
                    this.groupList = [{groupCode:"", groupName:""}]
                    for (let i = 0; i < res.data.length; i++){
                        let item = {
                                    groupCode:res.data[i].groupCode,
                                    groupName:res.data[i].groupName
                                   }
                        this.groupList.push(item)
                    }
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
        },

        // グループメンバー情報取得
        getGroupMemberInfo() {
            this.selGroupUserId = ""
            this.groupMemberList = []
            this.selUserName = ""
            this.selAgentCode = ""
            this.selDepartmentName = ""
            this.workStart1 = ""
            this.workEnd1 = ""
            this.workStart2 = ""
            this.workEnd2 = ""
            this.workStart3 = ""
            this.workEnd3 = ""
            if (this.selGroupCode == null || this.selGroupCode == "") {
                return
            }
            for (let i = 0; i < this.groupList.length; i++){
                if(this.groupList[i].groupCode == this.selGroupCode){
                    this.selGroupName = this.groupList[i].groupName
                    continue
                }
            }
            let data = {
                companyCode: this.companyCode_user,
                groupCode: this.selGroupCode,
                groupName: this.selGroupName
            }
            getGroupMember(data).then(res => {
                if(res.data == '' || res.data == null) {
                    Message({
                        message: 'グループメンバー情報データ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                } else {
                    for (let i = 0; i < res.data.length; i++){
                        let retUserName = ""
                        for (let j = 0; j < this.userList.length; j++) {
                            if (res.data[i].groupUserId == this.userList[j].userId) {
                                retUserName = this.userList[j].firstName + "　" + this.userList[j].lastName
                                continue
                            }
                        }
                        let item = {
                                    groupUserId:res.data[i].groupUserId,
                                    groupUserName:retUserName
                                   }
                        this.groupMemberList.push(item)
                    }
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
        },

        // 現在の年月日時分秒取得
        updateTime: function() { 
            let currentDate = new Date()
            const week = ['（日）', '（月）', '（火）', '（水）', '（木）',  '（金）', '（土）']
            this.iYear = currentDate.getFullYear().toString()
            this.iMonth = this.strComplete(currentDate.getMonth() + 1)
            this.iDay = this.strComplete(currentDate.getDate())
            this.iWeek = currentDate.getDay()
            this.nowTime = this.strComplete(currentDate.getHours()) + ':' + this.strComplete(currentDate.getMinutes()) + ':' + this.strComplete(currentDate.getSeconds())
            this.nowDate = this.iYear + '年' + this.iMonth + '月' + this.iDay + '日' + week[this.iWeek]
            this.mNowDate = this.iYear.toString() + this.iMonth.toString() + this.iDay.toString()
            this.setDateTimeClolor()
        },

        // 勤怠設定情報取得
        getSettingInfo() {
            let settingDate = {
                companyCode: this.companyCode_user,
                userId: this.selAgentCode,
                applicationDate: String(this.iYear) + this.strComplete(this.iMonth)
            }
            getAtndStting(settingDate).then(res => {
                this.settingInfo = []
                if(res.data == '' || res.data == null) {
                    Message({
                        message: '勤怠設定情報データ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                }else {
                    this.settingInfo = res.data[res.data.length - 1]
                    this.restFlg1 = this.settingInfo.workBreakStart1 == '' || this.settingInfo.workBreakStart1 == null || this.settingInfo.workBreakEnd1 == '' || this.settingInfo.workBreakEnd1 == null ? false :true
                    this.restFlg2 = this.settingInfo.workBreakStart2 == '' || this.settingInfo.workBreakStart2 == null || this.settingInfo.workBreakEnd2 == '' || this.settingInfo.workBreakEnd2 == null ? false :true
                    this.restFlg3 = this.settingInfo.workBreakStart3 == '' || this.settingInfo.workBreakStart3 == null || this.settingInfo.workBreakEnd3 == '' || this.settingInfo.workBreakEnd3 == null ? false :true
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
        },

        // 出退勤回数取得
        getCategory() {
            let kbnDate = {
                companyCode: this.companyCode_user,
                categoryClassNumber: '012'
            }
            getCategory(kbnDate).then(res => {
                if(res.data == '' || res.data == null) {
                    Message({
                        message: '区分マスタデータ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                } else {
                    if (res.data[0].categoryKbnName == "1"){
                        this.visitFlg1 = true
                        this.visitFlg2 = false
                        this.visitFlg3 = false
                    } else if (res.data[0].categoryKbnName == "2"){
                        this.visitFlg1 = true
                        this.visitFlg2 = true
                        this.visitFlg3 = false
                    } else {
                        this.visitFlg1 = true
                        this.visitFlg2 = true
                        this.visitFlg3 = true
                    }
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
        },

        // 当日の勤怠表情報取得
        getAttendanceInfo() {
            let settingDate = {
                companyCode: this.companyCode_user,
                userId: this.selAgentCode,
                workDateYm: String(this.iYear) + this.strComplete(this.iMonth)
            }
            getAttendanceDetail(settingDate).then(res => {
                if(res.data == '' || res.data == null) {
                    this.workStart1 = ""
                    this.workEnd1 = ""
                    this.workStart2 = ""
                    this.workEnd2 = ""
                    this.workStart3 = ""
                    this.workEnd3 = ""
                }else {
                    let attendanceInfo = []
                    for (let i = 0; i < res.data.length; i++){
                        if (res.data[i].workDate == this.mNowDate) {
                            attendanceInfo.push(res.data[i])
                            continue
                        }
                    }
                    if (attendanceInfo.length == 0) {
                        this.workStart1 = ""
                        this.workEnd1 = ""
                        this.workStart2 = ""
                        this.workEnd2 = ""
                        this.workStart3 = ""
                        this.workEnd3 = ""
                    } else {
                        this.workStart1 = attendanceInfo[0].workStart1
                        this.workEnd1 = attendanceInfo[0].workEnd1
                        this.workStart2 = attendanceInfo[0].workStart2
                        this.workEnd2 = attendanceInfo[0].workEnd2
                        this.workStart3 = attendanceInfo[0].workStart3
                        this.workEnd3 = attendanceInfo[0].workEnd3
                     }
                }
            }).catch(error => {
                console.log(error)
            })
        },

        // 休日情報取得
        getHoliday() {
            let hDate = {
                companyCode: this.companyCode_user,
                holidayYear: this.iYear
            }
            getHoliday(hDate).then(res => {
                for(let i = 0; i < res.data.length; i++) {
                    if(res.data[i].holidayDate == this.mNowDate) {
                        this.holidayFlg = true
                        continue
                    }
                }
                this.setDateTimeClolor()
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
        },

        // 日時表示判定
        setDateTimeClolor() {
            if (this.holidayFlg == true || this.iWeek == 0){
                this.colorFlg = 1
            } else if (this.iWeek == 6){
                this.colorFlg = 2
            } else {
                this.colorFlg = 0
            }
        },

        // ２桁先頭０埋め
        strComplete(data) {
            let data_str = String(data)
            if(data_str.length == 1) {
                return "0" + data_str
            }else {
                return data_str
            }
        },

        // 詳細情報ダイアログ表示
        userSettingInfoOpen() {
            if (this.tcSelectFlg == false) {
                this.dialogUserInfo = true
            } else {
                if (this.selAgentCode != null && this.selAgentCode != "") {
                    this.dialogUserInfo = true
                }
            }
        },

        // 戻るボタン押下
        closeA() {
            this.$router.push({
                path: '/toppage'
            })
        },

        // 出勤ボタン押下
        workStartBtn() {
            if (this.tcSelectFlg == true) {
                if (this.selAgentCode == null || this.selAgentCode == "") {
                    return
                }
            }
            let data = {
                companyCode: this.companyCode_user,
                userId: this.selAgentCode,
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
                        if (this.tcSelectFlg == false) {
                            // 勤怠情報を最新にする
                            this.getAttendanceInfo()
                        } else {
                            // 社員名未選択状態にする
                            this.selGroupUserId = ""
                            this.selUserName = ""
                            this.selAgentCode = ""
                            this.selDepartmentName = ""
                            this.workStart1 = ""
                            this.workEnd1 = ""
                            this.workStart2 = ""
                            this.workEnd2 = ""
                            this.workStart3 = ""
                            this.workEnd3 = ""
                        }
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
            }).catch(error => {
                console.log(error)
            })
        },

        // 出退勤再入力判定
        workRest() {
            if(this.code == 'I_0008' || this.code == 'I_0009' || this.code == 'I_0010') {
                return false
            }
            return true
        },

        // 退勤ボタン押下
        workEndBtn() {
            if (this.tcSelectFlg == true) {
                if (this.selAgentCode == null || this.selAgentCode == "") {
                    return
                }
            }
            let data = {
                companyCode: this.companyCode_user,
                userId: this.selAgentCode,
                workDateYm: String(this.iYear) + this.strComplete(this.iMonth)
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
                    if (this.tcSelectFlg == false) {
                        // 勤怠情報を最新にする
                        this.getAttendanceInfo()
                    } else {
                        // 社員名未選択状態にする
                        this.selGroupUserId = ""
                        this.selUserName = ""
                        this.selAgentCode = ""
                        this.selDepartmentName = ""
                        this.workStart1 = ""
                        this.workEnd1 = ""
                        this.workStart2 = ""
                        this.workEnd2 = ""
                        this.workStart3 = ""
                        this.workEnd3 = ""
                    }
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
            }).catch(error => {
                console.log(error)
            })
        },

        // 出勤再入力
        workStartSet() {
            let data = {
                companyCode: this.companyCode_user,
                userId: this.selAgentCode,
                workDateYm: this.code
            }
            workStartSetting(data).then(res => {
                if (this.tcSelectFlg == false) {
                    // 勤怠情報を最新にする
                    this.getAttendanceInfo()
                } else {
                    // 社員名未選択状態にする
                    this.selGroupUserId = ""
                    this.selUserName = ""
                    this.selAgentCode = ""
                    this.selDepartmentName = ""
                    this.workStart1 = ""
                    this.workEnd1 = ""
                    this.workStart2 = ""
                    this.workEnd2 = ""
                    this.workStart3 = ""
                    this.workEnd3 = ""
                }
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

        // 退勤再入力
        workEndSet() {
            let data = {
                companyCode: this.companyCode_user,
                userId: this.selAgentCode,
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
                if (this.tcSelectFlg == false) {
                    // 勤怠情報を最新にする
                    this.getAttendanceInfo()
                } else {
                    // 社員名未選択状態にする
                    this.selGroupUserId = ""
                    this.selUserName = ""
                    this.selAgentCode = ""
                    this.selDepartmentName = ""
                    this.workStart1 = ""
                    this.workEnd1 = ""
                    this.workStart2 = ""
                    this.workEnd2 = ""
                    this.workStart3 = ""
                    this.workEnd3 = ""
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
        }

    }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.d-row {
    height: 65px;
}

.holiday-bg {
    // color: #4685ff;
    height: 65px;
    background-color: #f5f5f5 !important;
}

.saturday-cell {
    color: #4685ff;
    // background-color: #f3f3f3 !important;
}

.sunday-cell {
    color: #fd5555 !important;
    // background: #f3f3f3 !important;
}

.el-table thead {
    color: #fff;

}

.has-gutter {
background-color: #eee;
}


.list-card {
    height: auto;
    background-color: #fff;
    // border: 1px #dcdcdc solid;
    box-shadow: 0 3px 23px 0 #95959530;
    border-radius: 6px;
    padding: 20px 0px;
    margin-bottom: 60px;

    .list-card-text {
        height: auto;
        padding: 0 30px;
    }
}

.time-area {
    display: flex;
    justify-content: space-around;

    .time-text {
        margin-top: 30px;
        font-size: 18px;
        font-weight: bolder;
        color: #444;
        line-height: 30px;
    }

    .time-btn-left {
        border-radius: 50%;
        background-color: #f2f2f2;
        width: 30px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        color: rgb(66, 219, 112);
        cursor: pointer;
    }

    .time-btn-right{
        border-radius: 50%;
        background-color: #f2f2f2;
        width: 30px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        color: rgb(66, 219, 112);
        cursor: pointer;
    }
}

.searchUserInfo-btn {
    width: 440px;
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
    box-shadow: 0 3px 20px 0 rgba(104, 104, 104, 0.3);
    border: 2px #ffa811 solid;
}

.my-info-all {
    border-radius: 50%;
    background-color: #fff;
    border: 2px #444 solid;
    width: 30px;
    height: 30px;
    text-align: center;
    line-height: 30px;
    color: #444;
    font-size: 14px;
    cursor: pointer;
}

.my-info-all:hover {
    color: #444;
    transform: translateY(-2px);
    transition: transform 0.2s;
    box-shadow: 0 6px 8px 0 rgba(99, 99, 99, 0.3);
}

.btn-area {
    height: 30px;
    background-color: #fff;
    margin-top: 20px;
    padding: 0px 30px;
    // position: sticky !important;
    // bottom: 30px;
    // z-index: 4;
    // padding-top: 20px;
}

.dialogUserInfoClass {

    .name-title {
        font-size: 26px;
        color: #444;
        font-weight: bold;
    }
}

.info-card {
    background-color: #f7f7f7;
    padding: 10px 20px;
    border-radius: 4px;
    height: auto;
    margin-top: 10px;
}

.searchUserInfoArea {
    height: auto;
    max-height: 320px;
    overflow-y: scroll;
}

.el-dialog .el-textarea__inner {
    height: 100px !important;
}

.el-textarea .el-input__count {
    bottom: -20px !important;
    right: 320px !important;
}

.el-table__body {
  // 使表格兼容safari，不错位
  width: 100%;
  table-layout: fixed !important;
}
</style>
