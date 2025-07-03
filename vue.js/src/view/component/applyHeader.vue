<template>
    <div style="height: auto;padding: 0 30px;">
        <el-row>
            <el-col :span="10">
                <span class="card-title-big">{{title}}</span>
            </el-col>
            <el-col :span="14" class="mt-10 flex-end">
                <!-- <div class="white-deep-btn">
                    エクスポート
                </div> -->
            </el-col>
        </el-row>
        <el-row style="margin: 10px 0 10px">
            <div class="flex-between">
                <div class="flex-start">
                    <div class="my-info">
                        <span class="my-info-title">社員名：</span>
                        <span class="my-info-text">{{ userName }}</span>
                    </div>
                    <div class="my-info">
                        <span class="my-info-title">社員番号：</span>
                        <span class="my-info-text">{{ agentCode }}</span>
                    </div>
                    <div class="my-info">
                        <span class="my-info-title">部門：</span>
                        <span class="my-info-text">{{ departmentName }}</span>
                    </div>
                </div>
                    <div class="my-info" v-if="reqNumber != ''">
                        <span class="my-info-title">申請番号：</span>
                        <span class="my-info-text">{{ reqNumber }}</span>
                    </div>
            </div>
        </el-row>
    </div>
</template>

<script>
import { getUserDetailedInfo } from '@/utils/auth'
export default {
    name: 'applyHeader',
    // 接受父组件传递的值
    props: {
        title: {
            type: String
        },
        reqNumber: {
            type: String,
            default: ''
        }
    },
    // data() {
    //     return {
    //         userName: getUserDetailedInfo() !== undefined ? getUserDetailedInfo().firstName + ' ' +getUserDetailedInfo().lastName : '',
    //         agentCode: getUserDetailedInfo() !== undefined ? getUserDetailedInfo().userId : '',
    //         departmentName: getUserDetailedInfo() !== undefined ? getUserDetailedInfo().departmentName : '',
    //     }
        
    // }
    created() {
        this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
        this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
        console.log(this.rowdata)
        console.log(this.changebyFlg)
    },
    data() {
        return {
            name: '',
            code: '',
            department: ''
            // userName: getUserDetailedInfo() !== undefined ? getUserDetailedInfo().firstName + ' ' +getUserDetailedInfo().lastName : '',
            // agentCode: getUserDetailedInfo() !== undefined ? getUserDetailedInfo().userId : '',
            // departmentName: getUserDetailedInfo() !== undefined ? getUserDetailedInfo().departmentName : '',
        } 
    },
    computed: {
        userName: function() {
            if(this.changebyFlg == '1') {
                return getUserDetailedInfo() !== undefined ? getUserDetailedInfo().firstName + ' ' +getUserDetailedInfo().lastName : ''
            }else if(this.changebyFlg == '2' || this.changebyFlg == '3') {
                return this.rowdata.userName
            }
        },
        agentCode: function() {
            if(this.changebyFlg == '1') {
                return getUserDetailedInfo() !== undefined ? getUserDetailedInfo().userId : ''
            }else if(this.changebyFlg == '2' || this.changebyFlg == '3') {
                return this.rowdata.requestUserId
            }
        },
        departmentName: function() {
            if(this.changebyFlg == '1') {
                return getUserDetailedInfo() !== undefined ? getUserDetailedInfo().departmentName : ''
            }else if(this.changebyFlg == '2' || this.changebyFlg == '3') {
                return this.rowdata.departmentName
            }
        }
    }
}
</script>

<style>

</style>