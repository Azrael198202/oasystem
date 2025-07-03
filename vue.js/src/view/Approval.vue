<template>
    <el-dialog
        title="承認者選択"
        :visible.sync="openreq"
        width="900px"
        :close-on-click-modal="true">
        <el-row justify="space-between">
            <el-col :span="8" class="rout">
                <div style="margin-bottom: 10px"><h4>現在までの承認ルート</h4></div>
                <div class="block">
                    <el-timeline :reverse='true'>
                        <el-timeline-item v-for="(item,index) in routerList" :key='index' :timestamp='item.reqAppDatetime' placement="top" :color='routerColor(item.wfStatusKbn)'>
                            <el-card>
                                <div class="flex-between"><h4>{{item.userName}}</h4><h4 :class="routerStatusColor(item.categoryKbnName)">{{item.categoryKbnName}}</h4></div>
                                <p>{{item.reqAppComment}}</p>
                            </el-card>
                        </el-timeline-item>
                    </el-timeline>
                </div>
            </el-col>
            <el-col :span="14" style="justify-content: flex-end;" >
                <div>
                    <el-form ref="formSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                        <el-form-item prop="search_id">
                            <el-row :gutter='10'>
                                <el-col :span="19">
                                    <el-input
                                        ref="searchInput"
                                        placeholder="承認者の社員番号または承認者の名前を入力してください"
                                        v-model="formSearch.search_id"
                                        @keyup.enter.native="userSearch()">
                                    </el-input>
                                </el-col>
                                <el-col :span="5">
                                    <el-button type="primary" icon="el-icon-search" style="margin-left: 10px;" @click="userSearch()">検索</el-button>
                                </el-col>
                            </el-row>
                        </el-form-item>
                    </el-form>
                    <div v-if="searchUserInfo" class="searchUserInfoArea">
                        <div v-for="(item , index) in userSearchR" :key="index">
                            <div class="searchUserInfo-btn" @click="userSearchInreq(item.firstName + ' ' + item.lastName, item.userId, item.departmentName)">
                                <el-col :span='22'>
                                    <el-row>
                                        <el-col :span='24' class="my-info">
                                            <span class="my-info-title" style="line-height: 30px">社員名前：</span>
                                            <span class="my-info-text" style="line-height: 30px">{{ item.firstName + ' ' + item.lastName }}</span>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span='10' class="my-info">
                                            <span class="my-info-title" style="line-height: 30px">社員番号：</span>
                                            <span class="my-info-text" style="line-height: 30px" >{{ item.userId }}</span>
                                        </el-col>
                                        <el-col :span='8' class="my-info">
                                            <span class="my-info-title" style="line-height: 30px">部門：</span>
                                            <span class="my-info-text" style="line-height: 30px">{{ item.departmentName }}</span>
                                        </el-col>
                                    </el-row>
                                </el-col>
                                <el-col :span='2'>
                                    <el-col :span='24' class="my-info" style="text-align: center;margin-top: 16px">
                                        <i class="fas fa-angle-right" style="font-size: 24px;color: #ffa811"></i>
                                    </el-col>
                                </el-col>
                            </div>
                        </div>
                    </div>
                    <div v-if="searchUserInfo" style="font-size: 14px;color: #444;margin: 10px 0 0 10px">
                        検索結果：<span style="font-size: 16px;color: #ffa811">{{this.userSearchR.length}}</span>名
                    </div>
                </div>
                <div class="approval-area">
                    <el-row >
                        <el-col :span='4'>
                            <div class="approval-area-title">承認者選択</div>
                        </el-col>
                        <el-col v-if="!searchUserInfomail" :span='20'>
                            <span class="approval-area-notext">未選択</span> 
                        </el-col>
                        <el-col v-if="searchUserInfomail" :span='20'>
                            <span class="approval-area-text">{{name_req}}</span>
                            <span class="approval-area-text">{{userId_req}}</span>
                            <span class="approval-area-text">{{departmentName_req}}</span>
                        </el-col>
                    </el-row>

                    <el-row v-if="!approvalFlg">
                        <el-col :span='4' style="margin-top: 10px">
                            <div class="approval-area-title">申請概要</div>
                        </el-col>
                        <el-col :span='20' style="margin-top: 10px">
                            <el-input placeholder="" v-model="requestData.requestOverview" v-if="remarkreqif">
                            </el-input>
                        </el-col>
                    </el-row>
                </div>
                
                <el-button type="primary" round v-bind:disabled="buttonFlg" @click="insertRes()" v-if="!approvalFlg"  style="margin-top: 15px">次の人へ回覧</el-button>
                <el-button type="primary" round v-bind:disabled="buttonFlg" @click="admit()" v-if="approvalFlg" style="margin-top: 15px">承認＆次の人へ回覧</el-button>
                <el-button type="primary" round v-bind:disabled="buttonFlg" @click="lastadmit()" v-if="openApproval&&approvalFlg" style="margin-top: 15px">最終承認</el-button>
            </el-col>
        </el-row>
        <span slot="footer" class="dialog-footer">
            <el-button @click="openreq = false">キャンセル</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import { Message } from 'element-ui';
    import { getUserDetail } from '@/api/user'
    import { getWfList,insertNewWf, insertWf, updateadmitWf, updatelastadmitWf } from '@/api/wfList'
    import { insertLeaveRequest, updateLaRequest} from '@/api/requestList'
    import { sendreqMailApi } from '@/api/findPass'
    import { getWorkFlowList } from '@/api/workFlow'
    import { getUserInfo } from '@/utils/auth'

    export default {
        props: ['userData','requestData'],
        name: "Approval",
        components: {

        },
        created() {
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            this.getCurrentTime()
            this.getRouterList()
            this.ApprovalSetting()
        },

        data() {
            return {
                openreq: false,
                // 承認ルート確認
                routerList: [],
                userSearchR: [],
                rowdata: [],
                searchUserInfo: false,
                searchUserInfomail: false,
                remarkreqif: true,
                searchUserInfomailnext: true,
                //承認ボタン処理
                approvalFlg: false,
                openApproval: true,
                formSearch: {
                    search_id: ''
                },
                name_req: '',
                userId_req: '',
                departmentName_req: '',
                changebyFlg: '',
                reqNum: '',
                aTime: '',
                yMd: '',
                total: 0,
                buttonFlg: false
            }
        },

        computed: {
        },
        methods: {
            ApprovalSetting(){
                let permissionKbn_user = getUserInfo().permissionKbn
                if (permissionKbn_user !== '999' && permissionKbn_user !== '550' && permissionKbn_user !== '500'){
                    this.openApproval = false
                }
            },
            getCurrentTime() {
                let yy = String(new Date().getFullYear())
                let mm = String((new Date().getMonth()+1) <10 ? '0' + (new Date().getMonth()+1) : (new Date().getMonth()+1))
                let dd = String(new Date().getDate() <10 ? '0' + new Date().getDate() : new Date().getDate());
                let hh = String(new Date().getHours() <10 ? '0' + new Date().getHours() : new Date().getHours());
                let mf = String(new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes());
                let ss = String(new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds());
                let ms = String(new Date().getMilliseconds()<10 ? '00'+new Date().getMilliseconds() : new Date().getMilliseconds()<100 ? '0'+new Date().getMilliseconds() : new Date().getMilliseconds());
                this.aTime = yy+mm+dd+hh+mf+ss+ms;
                this.yMd = yy+mm+dd
            },
            // 承認者検索
            userSearch() {
                this.userSearchR = []
                //検索項目必須
                let data = {
                    companyCode: this.userData.companyCode.trim(),
                    userId: this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', ''),
                }
                getUserDetail(data).then( res => {
                    this.userSearchR = res.data
                    this.searchUserInfo = true
                }).catch(error => {
                    console.log(error)
                })
            },
            userSearchInreq(userName_kari, agentCode_kari, departmentName_kari) {
                this.name_req=userName_kari
                this.userId_req=agentCode_kari
                this.departmentName_req=departmentName_kari
                this.searchUserInfomail = true
                //this.searchUserInfomailnext  = true
            },
            //次の人へ回覧
            insertRes() {
                if(this.userId_req == '') {
                    Message({
                        message: '承認者を選択してください',
                        type: 'error',
                        duration: 1000
                    })
                    return
                }
                this.buttonFlg = true
                this.getCurrentTime()
                this.setReqNum()
                this.requestSave()
                this.$emit('dataRegistration',this.reqNum)
                this.workFlowApply()
                this.confirmMail()
                this.openreq = false
                setTimeout(()=>{
                    this.buttonFlg = false
                    this.$router.go({path: this.$router.currentRoute.path, force: true})
                },1000)
            },
            // 承認＆次の人へ回覧
            admit() {
                if(this.userId_req == '') {
                    Message({
                        message: '承認者を選択してください',
                        type: 'error',
                        duration: 1000
                    })
                    return
                }
                this.buttonFlg = true
                this.getCurrentTime()
                this.setReqNum()
                this.admitWf()
                this.confirmMail()
                this.openreq = false
                setTimeout(()=>{
                    this.buttonFlg = false
                    this.$router.go(-1);
                },1000)
            },
            // 最終承認
            lastadmit() {
                this.buttonFlg = true
                this.getCurrentTime()
                this.setReqNum()
                this.lastadmitWf()
                this.lastConfirmMail()
                this.openreq = false
                setTimeout(()=>{
                    this.buttonFlg = false
                    this.$router.go(-1);
                },1000)
            },
            confirmMail() {
                // お知らせメール送信
                if(this.changebyFlg == "1" || this.changebyFlg == "2"){
                    if(this.requestData.requestTypeKbn == "020"){
                        let datareqmailapi = {
                            companyCode:     this.userData.companyCode,
                            userId:          this.userData.userId,
                            userAppId:       this.userId_req,
                            userName:        this.userData.userName,
                            appName:         this.name_req,
                            reqMailKbn:      '休暇申請',
                            appMailKbn:      '1',
                            requestOverview: this.requestData.requestOverview
                        }
                        sendreqMailApi(datareqmailapi).then(response => {
                            if (response.status === 602){
                                Message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1500
                                })
                            }else{
                                Message({
                                    message: 'メールを送信しました。',
                                    type: 'success',
                                    duration: 1500
                                })
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }else{
                        let datareqmailapi = {
                            companyCode:     this.userData.companyCode,
                            userId:          this.userData.userId,
                            userAppId:       this.userId_req,
                            userName:        this.userData.userName,
                            appName:         this.name_req,
                            reqMailKbn:      this.requestData.requestOverview,
                            appMailKbn:      '1',
                            requestOverview: this.requestData.requestOverview
                        }
                        sendreqMailApi(datareqmailapi).then(response => {
                            if (response.status === 602){
                                Message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1500
                                })
                            }else{
                                Message({
                                    message: 'メールを送信しました。',
                                    type: 'success',
                                    duration: 1500
                                })
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                }else if(this.requestData.requestStatusKbn == "010" || this.changebyFlg == "3"){
                    if(this.rowdata.requestTypeKbn == "020"){
                       let datasendM = {
                            companyCode:     this.userData.companyCode,
                            userId:          this.userData.userId,
                            userAppId:       this.userId_req,
                            userName:        this.rowdata.userName,
                            appName:         this.name_req,
                            reqMailKbn:      '休暇申請',
                            appMailKbn:      '1',
                            requestOverview: this.rowdata.requestOverview
                        }
                        sendreqMailApi(datasendM).then(response => {
                            if (response.status === 602){
                                Message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1500
                                })
                            }else{
                                Message({
                                    message: 'メールを送信しました。',
                                    type: 'success',
                                    duration: 1500
                                })
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }else{
                        let datasendM = {
                            companyCode:     this.userData.companyCode,
                            userId:          this.userData.userId,
                            userAppId:       this.userId_req,
                            userName:        this.rowdata.userName,
                            appName:         this.name_req,
                            reqMailKbn:      this.requestData.requestOverview,
                            appMailKbn:      '1',
                            requestOverview: this.requestData.requestOverview
                        }
                        sendreqMailApi(datasendM).then(response => {
                            if (response.status === 602){
                                Message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1500
                                })
                            }else{
                                Message({
                                    message: 'メールを送信しました。',
                                    type: 'success',
                                    duration: 1500
                                })
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                }
            },
            lastConfirmMail() {
                // お知らせメール送信
                if(this.rowdata.requestTypeKbn == "020") {
                    let datareqmailapi = {
                        companyCode:     this.userData.companyCode,
                        userId:          this.userData.userId,
                        userAppId:       this.userData.userId,
                        userName:        '',
                        appName:         this.userData.userName,
                        reqMailKbn:      '休暇申請',
                        appMailKbn:      '3',
                        requestOverview: this.rowdata.requestOverview,
                        requestNumber:   this.reqNum
                    }
                    sendreqMailApi(datareqmailapi).then(response => {
                    if (response.status === 602){
                        Message({
                                message: response.msg,
                                type: 'error',
                                duration: 1500
                            })
                        }else{
                            Message({
                                message: 'メールを送信しました。',
                                type: 'success',
                                duration: 1500
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }else{
                    let datareqmailapi = {
                        companyCode:     this.userData.companyCode,
                        userId:          this.userData.userId,
                        userAppId:       this.userData.userId,
                        userName:        '',
                        appName:         this.userData.userName,
                        reqMailKbn:      this.requestData.requestOverview,
                        appMailKbn:      '3',
                        requestOverview: this.requestData.requestOverview,
                        requestNumber:   this.reqNum
                    }
                    console.log(datareqmailapi)
                    sendreqMailApi(datareqmailapi).then(response => {
                        if (response.status === 602){
                            Message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1500
                                })
                        }else{
                            Message({
                                message: 'メールを送信しました。',
                                type: 'success',
                                duration: 1500
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                    if(this.requestData.requestTypeKbn == '031') {
                        this.reqNum = this.requestData.requestNumber
                    }
                }else if(this.userData.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.requestData.requestNumber
                }else if(this.changebyFlg == "3"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            applyDialogOpen(Flg){
                //0の時に申請処理、1の時に承認処理
                this.approvalFlg = Flg == 1 ? true : false;
                this.openreq = true
            },
            requestSave(){
                let data = {
                    companyCode:      this.requestData.companyCode,
                    requestNumber:    this.reqNum,
                    requestUserId :   this.userData.userId,
                    requestDate:      this.yMd,
                    requestTypeKbn:   this.requestData.requestTypeKbn,
                    requestOverview:  this.requestData.requestOverview,
                    requestStatusKbn: '003'
                }
                insertLeaveRequest(data).then(res => {
                }).catch(error => {
                    console.log(error)
                })
            },
            // 次の人へ回覧の処理
            workFlowApply(){
                let reqData = {
                    companyCode:   this.userData.companyCode,
                    requestNumber: this.reqNum
                }
                getWfList(reqData).then(res =>{
                    //申請ルートの順番取得
                    if (res.data) {
                        this.total = res.data.length
                    } else {
                        this.total = 0
                    }
                    let insertDataInsWf = {
                        companyCode:      this.userData.companyCode,
                        requestNumber:    this.reqNum,
                        total:            this.total,
                        reqAppComment:    '',
                        reqRequestUserId: this.userData.userId,
                        reqApproveUserId: this.userId_req,                   
                    }
                    //　最初に作るデータ（申請,承認）
                    insertNewWf(insertDataInsWf).then(res =>{
                    }).catch(error => {
                        console.log(error)
                    })
                }).catch(error => {
                    console.log(error)
                })
            },
            // 承認＆次の人へ回覧の処理
            admitWf(){
                let reqData = {
                    companyCode:   this.userData.companyCode,
                    requestNumber: this.rowdata.requestNumber
                }
                getWfList(reqData).then(res =>{
                    //申請ルートの順番取得
                    if (res.data) {
                        this.total = res.data.length
                    } else {
                        this.total = 0
                    }
                    let dataadmitwf = {
                        companyCode:   '000001',
                        requestNumber: this.reqNum,
                        reqAppOrder:   this.total,
                        updateUser:    this.rowdata.reqAppUserId
                    }
                    // 承認済
                    updateadmitWf(dataadmitwf).then((res) => {
                    }).catch(error => {
                        console.log(error)
                    })
                    let reqDataInsWf = {
                        companyCode:   this.userData.companyCode,
                        requestNumber: this.reqNum,
                        reqAppOrder:   this.total + 1,
                        reqAppComment: '',
                        reqAppUserId:  this.userId_req,
                        reqAppUpUserId: getUserInfo().userId,
                    }
                    // 承認
                    insertWf(reqDataInsWf).then((res) => {                    
                    }).catch(error => {
                        console.log(error)
                    })
                }).catch(error => {
                    console.log(error)
                })
            },
            lastadmitWf(){
                let dataLareq = {
                    companyCode:   this.userData.companyCode,
                    requestNumber: this.reqNum,
                    updateUser:    this.rowdata.reqAppUserId
                }
                updateLaRequest(dataLareq).then((res) => {
                }).catch(error => {
                    console.log(error)
                })
                this.getCurrentTime()
                let reqData = {
                    companyCode:   this.userData.companyCode,
                    requestNumber: this.reqNum
                }
                getWfList(reqData).then(res =>{
                    //申請ルートの順番取得
                    if (res.data) {
                        this.total = res.data.length
                    } else {
                        this.total = 0
                    }
                    let datawpdatewf = {
                        companyCode:   '000001',
                        requestNumber: this.reqNum,
                        reqAppOrder:   this.total,
                        updateUser:    this.userData.userId
                    }
                    // 最終承認
                    updatelastadmitWf(datawpdatewf)
                    Message({
                        message: '最終承認しました。',
                        type: 'success',
                        duration: 1500
                    })
                }).catch(error => {
                    console.log(error)
                })
            },
            getRouterList(){
                let data
                if(this.changebyFlg == "1"){
                    data = {
                        companyCode:   this.userData.companyCode,
                        requestNumber: ''
                    }
                }else{
                    data = {
                        companyCode:   this.userData.companyCode,
                        requestNumber: this.rowdata.requestNumber
                    }
                }
                getWorkFlowList(data).then(res => {
                    this.routerList = res.data
                }).catch(error => {
                    console.log(error)
                })
            },
            // 承認ルート状態の色
            routerStatusColor (categoryKbnName) {
                switch(categoryKbnName) {
                    case '未承認':
                        return 'blue-text'
                    case '承認完了':
                        return 'green-text'
                    case '差戻':
                        return 'red-text'
                    case '申請済み':
                        return 'orange-text'
                    case '承認済':
                        return 'orange-text'
                }
            },
            routerColor (wfStatusKbn) {
                switch (wfStatusKbn) {
                    case '012':
                        return '#ffa811'
                    case '020':
                        return '#4685ff'
                    case '021':
                        return '#fd5555'
                    case '025':
                        return '#ffa811'
                    case '900':
                        return '#54c336'
                }
            },
        }
    }
</script>

<style lang="scss" scope>

</style>