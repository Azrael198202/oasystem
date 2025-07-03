<template>
    <div class="login">
        <div class="login-main">
            <div class="login-card" id="loginCard">
                <el-row style="height:60px;">
                    <!-- TODO:本番に使う 2/3 <img src="@/assets/images/logo.png" alt=""> -->
                    <img src="@/assets/images/logo.png" alt="">
                </el-row>
                <el-form ref="loginForm" :rules="rules" :model="loginForm" label-width="0px">
                    <el-form-item prop="input_id">
                        <el-input v-model="loginForm.input_id" placeholder="社員番号" maxlength="20"></el-input>
                    </el-form-item>
                    <el-form-item prop="input_password">
                        <el-input placeholder="パスワード" v-model="loginForm.input_password" show-password @keyup.enter.native="handleLogin" maxlength="20"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <!-- <el-checkbox v-model="loginForm.checked">ログイン状態保持</el-checkbox> -->
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" style="width: 100%" @click.native.prevent="handleLogin" :loading="loading">ログイン</el-button>
                        <el-button type="text" style="font-size: 12px" @click="findPassword()">パスワードをお忘れの方</el-button>
                    </el-form-item>
                    <div style="font-size: 14px;color: #666">
                        <div>お問い合わせはこちらまで</div>
                        <div>
                            <a style="color: #409EFF" href="mailto:lw.system.syanaikanri@leadingwin.co.jp">lw.system.syanaikanri@leadingwin.co.jp</a>
                        </div>
                    </div>
                </el-form>
            </div>
            <!-- <el-row class="bottom-c">
                <span>© Copyright 株式会社 「リーディング・ウィン」 All Rights Reseved.</span>
            </el-row> -->
            <commonFootter></commonFootter>
        </div>

    </div>
</template>

<script>
import { agentLogin } from '@/api/login'
import { getInfo } from '@/api/user'
import { setUserDetailedInfo } from '@/utils/auth'
import { getCategory, getHoliday } from '@/api/category'
import commonFootter from "@/layout/commonFootter.vue";
import { MessageBox, Message } from 'element-ui'

export default {
    name: "Login",
    components: {
        commonFootter
    },
    focus:function() {
        let _this = this
        setTimeout(function() {
            let pannel = document.getElementById('loginCard')
            pannel.scrollIntoView(true)
        },200)
    },
    data() {
        const validateUsername = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('社員番号は必須入力です。'))
            } else {
                callback()
            }
        }
        const validatePassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('パスワードは必須入力です。'))
            } else {
                callback()
            }
        }
        return {
            kbn: [],
            loginInfo: [],
            loading: false,
            loginForm: {
                input_id: '',
                input_password: '',
                checked: false
            },
            rules: {

                input_id: [
                    { required: true, trigger: 'blur', validator: validateUsername },
                ],
                input_password: [
                    { required: true, trigger: 'blur', validator: validatePassword },
                    { min: 6, max: 20, message: '６～２０桁を入力してください。', trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
      findPassword() {
          this.$router.push({
            path: '/registered',
            query: { step: '1' }
          })
      },
     handleLogin() {
        this.loading = true;
        this.$refs['loginForm'].validate(valid => {
            if (valid) {
                if (this.loginForm.input_id.length !== 3) {
                  this.loginForm.input_id = (Array(3).join('0') + this.loginForm.input_id).slice(-3)
                }
                let _this = this;
                var data = {
                    companyCode: '000001',
                    userId: _this.loginForm.input_id,
                    passWord: _this.loginForm.input_password,
                    // isRemeber: _this.loginForm.checked
                }
                 agentLogin(data).then(res => {
                    if(res.status == 400) {
                        Message({
                            message: '社員番号またはパスワードが正しくありません',
                            type: 'error',
                            duration: 4 * 1000
                        })
                        this.loading = false;
                        return
                    }else if(res.status == 602) {
                        Message({
                            message: '社員番号：' + _this.loginForm.input_id + 'は存在しません。',
                            type: 'error',
                            duration: 4 * 1000
                        })
                        this.loading = false;
                        return
                    }
                    this.loginInfo = res.data
                    const loginForm = {
                        userInfo: {
                            userName: this.loginInfo.loginUserInfo.userName,
                            userId: this.loginInfo.loginUserInfo.userId,
                            accountKbn: this.loginInfo.loginUserInfo.accountKbn,
                            companyCode: this.loginInfo.loginUserInfo.companyCode,
                            permissionKbn: this.loginInfo.loginUserInfo.permissionKbn,
                            accountKbn: this.loginInfo.loginUserInfo.accountKbn,
                            imageUrl:this.loginInfo.loginUserInfo.imageUrl
                        },
                        // checked: _this.loginForm.checked,
                        token: this.loginInfo.token
                    }
                    let data = {
                        companyCode: res.data.loginUserInfo.companyCode,
                        userId: res.data.loginUserInfo.userId
                    }
                    getInfo(data).then( res => {
                        setUserDetailedInfo(res.data)
                         _this.$store.dispatch('user/login', loginForm).then(() => {
                            let kbnM = { 
                                companyCode: this.loginInfo.loginUserInfo.companyCode,
                                categoryClassNumber: '000'
                            }
                            getCategory(kbnM).then(res => {
                                this.loading = false;
                                if(res.data[0].categoryKbn == '999' && this.loginInfo.loginUserInfo.permissionKbn !== '999') {
                                    Message({
                                        message: 'システムメンテナンス中のため、ご利用できません。しばらくお待ちください。',
                                        type: 'error',
                                        duration: 4 * 1000
                                    })
                                }else {
                                    _this.$router.push({
                                        path: '/toppage'
                                    })
                                }
                            }).catch(error => {
                                this.loading = false;
                                console.log(error)
                            })
                        }).catch((error) => {
                            this.loading = false;
                            console.log(error)
                        })
                    }).catch(error => {
                        this.loading = false;
                        console.log(error);
                    });
                }).catch(error => {
                    this.loading = false;
                    console.log(error);
                });
            } else {
                this.loading = false;
                console.log('error submit!!')
                return false
            }
        })
     },
    getUserInfo(dataInfo){
        let data = {
            companyCode: dataInfo.companyCode,
            userId: dataInfo.userId
        }
        getInfo(data).then( res => {
            setUserDetailedInfo(res.data)
            // console.log(res.data)
        }).catch(error => {
            alert('社員番号またはパスワードが正しくありません');
            console.log(error);
        });
    }
   }
}
</script>

<style lang="scss" scoped>
@import '../style/index.scss';

.el-input {
    width: 100% !important;
}

.el-row {
    margin-bottom: 20px;

    &:last-child {
      margin-top: 40px;
    }
}

</style>
