<template>
    <div class="login">
        <div class="login-main">
            <div class="login-card">
                <el-row style="height:80px;">
                    <!-- TODO:本番に使う 3/3 <img src="@/assets/images/logo.png" alt=""> -->
                    <img src="@/assets/images/logo.png" alt="">
                </el-row>
                <el-form ref="form" :rules="formRules" :model="form" label-width="0px" @submit.native.prevent>
                    <el-row class="pass-title">
                         <span>
                            パスワード再設定
                        </span>
                    </el-row>
                    <div v-if="step == '1'">
                        <el-form-item prop="find_id">
                            <el-input v-model="form.find_id" placeholder="社員番号" maxlength="20" @keyup.enter.native="send()"></el-input>
                        </el-form-item>
                        <div style="margin: 40px 0 10px">
                            <el-button type="primary" @click="send()" style="width: 100%">送信</el-button>
                        </div>
                        <el-button plain style="width: 100%" @click="toLoginPage()">戻る</el-button>
                    </div>
                    <div v-if="step == '3'">
                        <el-form-item prop="reset_pass" >
                            <el-input v-model="form.reset_pass" placeholder="パスワード" show-password maxlength="20"></el-input>
                        </el-form-item>
                        <el-form-item prop="reset_pass2">
                            <el-input v-model="form.reset_pass2" placeholder="パスワード確認" show-password maxlength="20" @keyup.enter.native="reset()"></el-input>
                        </el-form-item>
                        <div style="margin: 40px 0 10px">
                            <el-button type="primary" @click="reset()" style="width: 100%">パスワード修正</el-button>
                        </div>
                    </div>
                </el-form>
            </div>
            <el-row class="bottom-c">
                <span>© Copyright 株式会社 「リーディング・ウィン」 All Rights Reseved.</span>
            </el-row>
        </div>

    </div>
</template>

<script>
import { sendMailApi, savePwd, sendreqMailApi } from '@/api/findPass'
import { MessageBox, Message } from 'element-ui'
import { findPwd } from '@/api/user'
export default {
    name: "Registered",
    created() {
        if (this.$route.query.step == '1') {
            this.step = this.getQueryVariable('step')
        }else if(this.$route.query.step == '3') {
            this.step = this.getQueryVariable('step')
            this.token = this.getQueryVariable('token')
        }
    },
    data() {
        const validateFindUsername = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('社員番号を入力してください'))
            } else {
                callback()
            }
        }
        // 验证密码格式
        const validatePassword = (rule, value, callback) => {
            if (value === '') {
                    callback(new Error('パスワードを入力してください'))
                } else {
                    callback()
            }
        }
        // 验证两次输入的密码是否一致
        const validateConfirmPwd = (rule, value, callback) => {
        if (!this.confirmNewPwd(value)) {
            callback(new Error('パスワードが一致しません。'))
        } else {
            callback()
        }
        }
        return {
            token: '',
            step: '',
            form: {
                find_id: '',
                reset_pass: '',
                reset_pass2: '',
            },
            formRules: {
                find_id: [
                    { required: true, trigger: 'blur', validator: validateFindUsername },
                ],
                reset_pass: [
                    { required: true, trigger: 'blur', validator: validatePassword },
                    { min: 6, max: 20, message: '６～２０桁を入力してください。', trigger: 'blur' }
                ],
                reset_pass2: [
                    { required: true, trigger: 'blur', validator: validateConfirmPwd },
                    { min: 6, max: 20, message: '６～２０桁を入力してください。', trigger: 'blur' }
                ],
            }
        }
    },
    methods: {

        toLoginPage() {
            this.$router.push({
                path: '/login'
            })
        },

        // 点击发送邮件
        send() {
            const thisObj = this
            thisObj.serverErrors = []
            thisObj.$refs.form.validate(valid => {
                if (valid) {
                const formdata = {
                    companyCode: '000001',
                    userId: thisObj.form.find_id
                }
                sendMailApi(formdata).then(response => {
                  if (response.status === 602)
                  {
                    Message({
                        message: response.msg,
                        type: 'error',
                        duration: 4 * 1000
                    })
                  }else{
                    Message({
                        message: 'メール送信しました',
                        type: 'success',
                        duration: 4 * 1000
                    })
                  }

                    this.$router.push({
                        path: '/login'
                    })
                }).catch(error => {
                    console.log(error)
                })
                }
            })
        },
        reset() {
            const thisObj = this
            thisObj.serverErrors = []
            thisObj.$refs.form.validate(valid => {
                if (valid) {
                const formdata = {
                    token: thisObj.token,
                    newPwd: thisObj.form.reset_pass,
                    confirmPwd: thisObj.form.reset_pass2
                }
                findPwd(formdata).then(response => {
                    Message({
                        message: 'パスワード修正しました',
                        type: 'success',
                        duration: 4 * 1000
                    })
                    this.$router.push({
                        path: '/login'
                    })
                }).catch(error => {
                    console.log(error)
                })
                }
            })
        },
        // 用于获取url参数
        getQueryVariable(variable) {
            var query = window.location.search.substring(1)
            var vars = query.split('&')
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split('=')
                if (pair[0] === variable) {
                return pair[1]
                }
            }
            return false
        },
        // 验证两次输入的密码是否一致
        confirmNewPwd(value) {
            return value === this.form.reset_pass
        },

    }
}
</script>

<style lang="scss" scoped>
@import '../style/index.scss';

.el-row {
    margin-bottom: 20px;

    &:last-child {
      margin-top: 40px;
    }
}

.pass-title {
    text-align: center;
    font-size: 18px;
    color: #444;
    font-weight: bold;
}

</style>
