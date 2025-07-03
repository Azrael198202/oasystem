<template>
    <!-- 取戻ボタン -->
    <el-dialog
        title="取戻を行いますか。"
        :visible.sync="opende"
        width="300px"
        :close-on-click-modal="true">
        <span slot="footer" class="dialog-footer">
            <el-button @click="deleteRes()">確 定</el-button>
            <el-button @click="opende = false">キャンセル</el-button>
        </span>
    </el-dialog>
</template>

<script>
import { getWfList, insertWf, deleteWf, updateWf, updateadmitWf, updatelastadmitWf, deleteAllWf } from '@/api/wfList'
import { Message } from 'element-ui';
import { insertLeaveRequest, searchRequest, insertRequest, updateReturnRequest, updateLaRequest, deleteRequest } from '@/api/requestList'

export default {
    name: "GrtBackDialog",
    created() {
        this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
    },
    data() {
        return {
            opende: false,
            appcomment: '',
            rowdata: [],
            total: '',
        }
    },
    methods:{
        // 取戻ポップアップ
        applyopende() {
            this.opende = true
        },
        //取戻
        deleteRes() {
            this.updateRequest()
            this.deleteAllW()
            Message({
                message: '取り戻しました。',
                type: 'success',
                duration: 4000
            })
            setTimeout(()=>{
                this.$router.go({path: this.$router.currentRoute.path, force: true})
            },100)
        },
        // 申請テーブル更新（回覧中→保存）
        updateRequest(){
            let returnReqData = {
                companyCode: this.rowdata.companyCode,
                requestNumber: this.rowdata.requestNumber,
            }
            updateReturnRequest(returnReqData).then((res) => {
                console.log(res.data)
                }).catch(error => {
                    console.log(error)
                })
        },
        //ワークフローテーブルのレコード削除
        deleteAllW(){
            let data = {
                companyCode: this.rowdata.companyCode,
                requestNumber: this.rowdata.requestNumber,
            }
            deleteAllWf(data).then(res =>{
                console.log(res.data)
            }).catch(error => {
                console.log(error)
            })
        }
    }
}
</script>