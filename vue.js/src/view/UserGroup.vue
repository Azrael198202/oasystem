<template>
    <div>
        <commonHeader></commonHeader>
        <div class="home">
            <div class="home-main">
                <div class="list-card">
                    <div class="list-card-text">
                        <el-row>
                            <el-col>
                                <span class="card-title-big">社員グループ設定</span>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span='24'>
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
                                            <span class="my-info-title">新規グループの場合は空白で保存してください。</span>
                                        </DIV>
                                    </DIV>
                                </DIV>
                            </el-col>
                        </el-row>
                        <el-row><el-col>&nbsp;</el-col></el-row>
                        <el-row>
                            <el-col :span='12'>
                                <el-form ref="formSearch" :rules="formRuleSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                                    <el-form-item prop="search_id">
                                        <el-row :gutter='10'>
                                            <el-col :span='19'>
                                                <el-input
                                                    ref="searchInput"
                                                    placeholder="社員番号または社員名を入力してください"
                                                    v-model="formSearch.search_id"
                                                    @keyup.enter.native="userSearch()"
                                                    >
                                                </el-input>
                                            </el-col>
                                            <el-col :span='5'>
                                                <el-button type="primary" icon="el-icon-search" style="margin-left: 10px;" @click="userSearch()">検索</el-button>
                                            </el-col>
                                        </el-row>
                                    </el-form-item>
                                </el-form>
                            </el-col>
                            <el-col :span='12'>
                                <span>グループ内メンバー</span>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span='12'>
                                <div v-if="searchFromListFlg" class="searchUserInfoArea">
                                    <div v-for="(item , index) in groupFromList" :key="index">
                                        <div class="searchUserInfo-btn" @click="moveFromList(index)">
                                            <el-col :span='22'>
                                                <el-row>
                                                    <el-col :span='24' class="my-info">
                                                        <span class="my-info-title">社員名前：</span>
                                                        <span class="my-info-text">{{ item.firstName + item.lastName }}</span>
                                                    </el-col>
                                                </el-row>
                                                <el-row>
                                                    <el-col :span='10' class="my-info">
                                                        <span class="my-info-title">社員番号：</span>
                                                        <span class="my-info-text">{{ item.userId }}</span>
                                                    </el-col>
                                                    <el-col :span='9' class="my-info">
                                                        <span class="my-info-title">部門：</span>
                                                        <span class="my-info-text">{{ item.departmentName }}</span>
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
                                <div v-if="searchFromListFlg" style="font-size: 16px;color: #444;margin: 10px 0 0 10px">
                                    検索結果：<span style="font-size: 20px;color: #ffa811">{{this.groupFromList.length}}</span>名
                                </div>
                            </el-col>
                            <el-col :span='12'>
                                <div v-if="searchToListFlg" class="searchUserInfoArea">
                                    <div v-for="(item , index) in groupToList" :key="index">
                                        <div class="searchUserInfo-btn" @click="moveToList(index)">
                                            <el-col :span='2'>
                                                <el-col :span='24' class="my-info" style="text-align: center;margin-top: 16px">
                                                    <i class="fas fa-angle-left" style="font-size: 24px;color: #ffa811"></i>
                                                </el-col>
                                            </el-col>
                                            <el-col :span='22'>
                                                <el-row>
                                                    <el-col :span='24' class="my-info">
                                                        <span class="my-info-title">社員名前：</span>
                                                        <span class="my-info-text">{{ item.firstName + item.lastName }}</span>
                                                    </el-col>
                                                </el-row>
                                                <el-row>
                                                    <el-col :span='9' class="my-info">
                                                        <span class="my-info-title">社員番号：</span>
                                                        <span class="my-info-text">{{ item.userId }}</span>
                                                    </el-col>
                                                    <el-col :span='8' class="my-info">
                                                        <span class="my-info-title">部門：</span>
                                                        <span class="my-info-text">{{ item.departmentName }}</span>
                                                    </el-col>
                                                </el-row>
                                            </el-col>
                                        </div>
                                    </div>
                                </div>
                                <div v-if="searchToListFlg" style="font-size: 16px;color: #444;margin: 10px 0 0 10px">
                                    検索結果：<span style="font-size: 20px;color: #ffa811">{{this.groupToList.length}}</span>名
                                </div>
                            </el-col>
                        </el-row>

                        <el-row class="btn-area">
                            <el-col class="flex-start">
                                <div class="back-btn" @click="closeA()">キャンセル</div>
                                <div class="green-btn" @click="modifyBtn($event)">保存</div>
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
import { getGroup, getGroupMember, updateGroup, deleteGroup } from '@/api/group'
import { getInfo, getUserDetail } from '@/api/user'
import { getCategory } from '@/api/category'
import { getUserInfo, getUserDetailedInfo } from '@/utils/auth'
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

    created() {
        // ログインユーザ情報取得
        this.userInfoInit()

        // グループ情報取得
        this.getGroupList()

        // ユーザ情報検索
        this.userDataSearch("")

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
            companyCode_user: '',
            agentCode:  '',
            groupList: [],
            selGroupCode: '',
            searchFromListFlg: true,
            searchToListFlg: false,
            groupFromList: [],
            groupToList: [],
            msg: '',
            code: '',
           formSearch: {
                search_id: ''
            },
            formRuleSearch: {

            }
        }
    },

    mounted: function()  {
　　　　let timerId = setInterval(this.updateTime, 1000); 
　　}, 

    methods:{
        // ログインユーザ情報取得
        userInfoInit() {
            this.agentCode = getUserInfo().userId
            this.companyCode_user = getUserInfo().companyCode
        },

        // グループ情報取得
        getGroupList() {
            /*
            let data = {
                companyCode: this.companyCode_user
            }
            getGroup(data).then(res => {
                this.groupList = []
                if(res.data == '' || res.data == null) {
                    this.tcSelectFlg = false
                    Message({
                        message: 'グループ情報データ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                } else {
                    for (let i = 0; i < res.data.length; i++){
                        this.groupList.push(res.data[i])
                    }
                }
            }).catch(error => {
                console.log(error)
            });
            */
            // フロントサイド単体検証用//////////////////////////////////////////////////////
            this.groupList = [{groupCode:"",groupName:""},
                              {groupCode:"000001",groupName:"グループ１"},
                              {groupCode:"000002",groupName:"グループ２"},
                              {groupCode:"000003",groupName:"グループ３"}
                             ] 
            // フロントサイド単体検証用//////////////////////////////////////////////////////
        },

        // ユーザ情報検索(検索ボタン)
        userSearch() {
            this.$refs.formSearch.validate(valid => {
                this.userDataSearch(this.$refs.searchInput.value)
            })
        },

        // ユーザ情報検索
        userDataSearch(str) {
                let data = {
                    companyCode: this.companyCode_user,
                    userId: str
                }
                getUserDetail(data).then( res => {
                    this.groupFromList = res.data
                    this.searchFromListFlg = true
                }).catch(error => {
                    console.log(error);
                });
        },

        // グループメンバー情報取得
        getGroupMemberInfo() {
            /*
            let data = {
                companyCode: this.companyCode_user,
                groupCode: this.selGroupCode
            }
            getGroupMember(data).then(res => {
                if(res.data == '' || res.data == null) {
                    this.tcSelectFlg = false
                    Message({
                        message: 'グループメンバー情報データ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                } else {
                    for (let i = 0; i < res.data.length; i++){
                        this.groupMemberList.push(res.data[i])
                    }
                    this.searchToListFlg = true
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
            */
            // フロントサイド単体検証用//////////////////////////////////////////////////////
            if(this.selGroupCode == "000001") {
                this.groupToList = [{userId:"053",firstName:"劉",lastName:"佳波",departmentName:"東京事業部"},
                                    {userId:"121",firstName:"長嶺",lastName:"幸史",departmentName:"九州事業部"},
                                    {userId:"198",firstName:"森",lastName:"正彦",departmentName:"九州事業部"}
                                   ]
            } else if(this.selGroupCode == "000002") {
                this.groupToList = [{userId:"076",firstName:"金",lastName:"瑛",departmentName:"東京事業部"},
                                    {userId:"129",firstName:"劉",lastName:"巍",departmentName:"営業"},
                                    {userId:"206",firstName:"穴澤",lastName:"宏幸",departmentName:"東京事業部"}
                                   ]
            } else if(this.selGroupCode == "000003")  {
                this.groupToList = [{userId:"051",firstName:"李",lastName:"培琦",departmentName:"東京事業部"},
                                    {userId:"109",firstName:"落合",lastName:"恭平",departmentName:"東京事業部"},
                                    {userId:"211",firstName:"村口",lastName:"文敏",departmentName:"九州事業部"}
                                   ]
            } else  {
                this.groupToList = []
            }
            this.searchToListFlg = true
            // フロントサイド単体検証用//////////////////////////////////////////////////////
        },

        // キャンセルボタン押下
        closeA() {
            this.$router.push({
                path: '/toppage'
            })
        },

        // 保存ボタン押下
        modifyBtn(arg) {

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
    border: 1px #dcdcdc solid;
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
    width: 100%;
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
