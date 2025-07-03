<template>
  <div class="edit-profile">
    <commonHeader></commonHeader>
    <div class="form-container">
      <el-card>
        <div slot="header">
          <span>個人情報編集</span>
        </div>

        <el-row :gutter="20">
          <!-- 左カラム：基本情報 -->
          <el-col :span="12">
            <el-form :model="form" :rules="rules" ref="profileForm" label-width="140px">
              <el-form-item label="社員番号">
                <el-input v-model="form.userId" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="社員名">
                <el-input v-model="form.userName" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="旧パスワード" prop="old_password">
                <el-input type="password" v-model="form.old_password" autocomplete="new-password"></el-input>
              </el-form-item>

              <el-form-item label="新パスワード" prop="new_password">
                <el-input type="password" v-model="form.new_password" autocomplete="new-password"></el-input>
              </el-form-item>

              <el-form-item label="確認パスワード" prop="confirm_password">
                <el-input type="password" v-model="form.confirm_password" autocomplete="new-password"></el-input>
              </el-form-item>

              <el-form-item label="プロフィール画像">
                <el-upload
                  class="avatar-uploader"
                  action="#"
                  :auto-upload="false"
                  :show-file-list="false"
                  :on-change="handleAvatarChange"
                  :before-upload="beforeAvatarValidate"
                  ref="avatarUpload">
                  <img
                    v-if="previewImageUrl"
                    :src="previewImageUrl"
                    class="avatar"
                    @error="handleAvatarError"
                  />
                  <img
                    v-else
                    :src="defaultAvatar"
                    class="avatar"
                  />
                </el-upload>
              </el-form-item>
            </el-form>
          </el-col>

          <!-- 右カラム：詳細情報 -->
          <el-col :span="12">
            <el-form :model="form" label-width="140px">
              <el-form-item label="生年月日">
                <el-date-picker
                  v-model="form.birthday"
                  @change="val => console.log('誕生日変更:', val)"
                  type="date"
                  placeholder="選択してください"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>

              <el-form-item label="性別">
                <el-select v-model="form.sex" placeholder="選択してください" style="width:80px">
                  <el-option label="男性" value="1"></el-option>
                  <el-option label="女性" value="2"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="電話番号">
                  <el-input
                    v-model="form.tel"
                    @input="onTelInput"
                    maxlength="13"
                    style="width:140px"
                    placeholder="例）080-1234-5678" />
              </el-form-item>

              <el-form-item label="郵便番号">
                <el-input
                    v-model="form.zip"
                    @input="onZipInput"
                    maxlength="8"
                    style="width:120px"
                    placeholder="例）813-0001" />
              </el-form-item>

              <el-form-item label="都道府県">
                <el-select v-model="form.address_prefecture" placeholder="選択してください" style="width:110px">
                    <el-option
                    v-for="pref in prefectures"
                    :key="pref"
                    :label="pref"
                    :value="pref"
                    />
                </el-select>
              </el-form-item>

              <el-form-item label="市区町村">
                <el-input v-model="form.address_municipality"></el-input>
              </el-form-item>

              <el-form-item label="町名">
                <el-input v-model="form.address_town"></el-input>
              </el-form-item>

              <el-form-item label="番地・建物名">
                <el-input v-model="form.address_detail"></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>

        <!-- 保存ボタン -->
        <el-row>
          <el-col :span="24">
            <div style="text-align: right; margin-top: 20px;">
              <el-button type="primary" @click="submitForm">保存</el-button>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </div>
    <commonFootter></commonFootter>
  </div>
</template>

<script>
import { agentLogin } from '@/api/login'
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { getUserInfo,getUserDetailedInfo,setUserInfo, getToken } from '@/utils/auth';
import {  } from "@/api/profiles.js";
import { resetPwd ,setUserDetail, updateAvatar} from '@/api/user'

export default {
  name: 'EditProfile',
  components: {
        commonHeader,
        commonFootter
    },
  watch: {
    'form.zip'(newZip) {
        if (newZip && newZip.length >= 7) {
        this.fetchAddressByZip(newZip);
        }
    }
   },
  data() {
    return {
      form: {
        userId: '',
        userName: '',
        old_password: '',
        new_password: '',
        confirm_password: '',
        avatarUrl: '',
        companyCode: getUserInfo().companyCode,
        birthday: '',
        sex: '',
        tel: '',
        zip: '',
        address_prefecture: '',
        address_municipality: '',
        address_town: '',
        address_detail: '',
        avatarUrl: '',
        base64String: '',
        content64Type: '',
      },
      addressError:false,
      prefectures: [
        '北海道', '青森県', '岩手県', '宮城県', '秋田県', '山形県', '福島県',
        '茨城県', '栃木県', '群馬県', '埼玉県', '千葉県', '東京都', '神奈川県',
        '新潟県', '富山県', '石川県', '福井県', '山梨県', '長野県',
        '岐阜県', '静岡県', '愛知県', '三重県',
        '滋賀県', '京都府', '大阪府', '兵庫県', '奈良県', '和歌山県',
        '鳥取県', '島根県', '岡山県', '広島県', '山口県',
        '徳島県', '香川県', '愛媛県', '高知県',
        '福岡県', '佐賀県', '長崎県', '熊本県', '大分県', '宮崎県', '鹿児島県', '沖縄県'
        ],
      previewImageUrl: '',
      defaultAvatar: require('@/assets/images/man.png'),
      rules: { }
    };
  },
  created() {
    const detail = getUserDetailedInfo();
    const formatBirthday = (b) => {
        return b && b.length === 8
            ? `${b.slice(0, 4)}-${b.slice(4, 6)}-${b.slice(6, 8)}`
            : '';
    };

    this.defaultAvatar = getUserInfo().imageUrl &&  getUserInfo().imageUrl !== '' ? getUserInfo().imageUrl : require('@/assets/images/man.png');
    console.log("getUserInfo:" , getUserInfo())

    Object.assign(this.form, {
        userId: getUserInfo().userId,
        userName: getUserInfo().userName,
        old_password: '',
        new_password: '',
        confirm_password: '',
        avatarUrl: '',
        birthday: formatBirthday(detail.birthday),
        sex: detail.sex,
        tel: detail.tel,
        zip: detail.zip,
        address_prefecture: detail.addressPrefecture,
        address_municipality: detail.addressMunicipality,
        address_town: detail.addressTown,
        address_detail: detail.addressDetail
    });
    this.originalDetail = JSON.parse(JSON.stringify(this.form));

    this.rules = {
        old_password: [
            { validator: this.validateConfirmPassword, trigger: 'blur' }
        ],
        new_password: [
            { validator: this.validateConfirmPassword, trigger: 'blur' },
            { min: 6, message: '６～２０桁を入力してください。', trigger: 'blur' }
        ],
        confirm_password: [
            { validator: this.validateConfirmPassword, trigger: 'blur' }
        ]
    };
  },
  methods: {
    hasDetailChanged() {
        const current = {
            avatarUrl: '', 
            birthday: this.form.birthday,
            sex: this.form.sex,
            tel: this.form.tel,
            zip: this.form.zip,
            address_prefecture: this.form.address_prefecture,
            address_municipality: this.form.address_municipality,
            address_town: this.form.address_town,
            address_detail: this.form.address_detail
        };

        if (current.avatarUrl !== this.originalDetail.avatarUrl ||
            current.birthday !== this.originalDetail.birthday ||
            current.sex !== this.originalDetail.sex ||
            current.zip !== this.originalDetail.zip ||
            current.address_prefecture !== this.originalDetail.address_prefecture ||
            current.address_municipality !== this.originalDetail.address_municipality ||
            current.address_town !== this.originalDetail.address_town ||
            current.address_detail !== this.originalDetail.address_detail
        )
            return true;
        else 
            return false;
    },
    onTelInput(value) {
        const input = event.target;
        const rawValue = input.value;
        const cursor = input.selectionStart;

        const digits = rawValue.replace(/[^\d]/g, '').slice(0, 11);
        let formatted = digits;
        if (digits.startsWith('03') || digits.startsWith('06')) {
            // 固定電話
            if (digits.length > 5) {
                formatted = `${digits.slice(0, 2)}-${digits.slice(2, 6)}-${digits.slice(6)}`;
            } else if (digits.length > 2) {
                formatted = `${digits.slice(0, 2)}-${digits.slice(2)}`;
            }
        } else {
            // 携帯電話
            if (digits.length > 7) {
                formatted = `${digits.slice(0, 3)}-${digits.slice(3, 7)}-${digits.slice(7)}`;
            } else if (digits.length > 3) {
                formatted = `${digits.slice(0, 3)}-${digits.slice(3)}`;
            }
        }

        this.form.tel = formatted;

        this.$nextTick(() => {
            const diff = formatted.length - rawValue.length;
            const newPos = cursor + diff;
            input.setSelectionRange(newPos, newPos);
        });
    },
    onZipInput(value) {
        const input = event.target;
        const raw = input.value.replace(/[^\d]/g, '').slice(0, 7);
        let formatted = raw;

        if (raw.length > 3) {
        formatted = `${raw.slice(0, 3)}-${raw.slice(3)}`;
        }

        this.form.zip = formatted;

        this.$nextTick(() => {
            const pos = formatted.length;
            input.setSelectionRange(pos, pos);
        });
    },
    async fetchAddressByZip(zip) {
        const cleanZip = zip.replace('-', '');
        try {
            const response = await fetch(`https://zipcloud.ibsnet.co.jp/api/search?zipcode=${cleanZip}`);
            const result = await response.json();
            if (result && result.results && result.results.length > 0) {
                const data = result.results[0];
                this.form.address_prefecture = data.address1;  // 都道府県
                this.form.address_municipality = data.address2; // 市区町村
                //this.form.address_town = data.address3;
                this.addressError = false;
            } else {
                if(cleanZip.length ===7){
                    this.$message.error('該当する住所が見つかりません');
                }
                this.addressError = true;
            }
        } catch (err) {
            console.error('郵便番号APIエラー:', err);
            this.$message.error('住所取得に失敗しました');
            this.form.addressError = 'true';
        }
    },
    handleAvatarChange(file) {
        this.avatarFile = file.raw;

        const reader = new FileReader();
        reader.onload = e => {
        this.previewImageUrl = e.target.result;
        };
        reader.readAsDataURL(file.raw);
    },
    beforeAvatarValidate(file) {
        const isImage = file.type.startsWith('image/');
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isImage) {
        this.$message.error('画像ファイルを選択してください');
        return false;
        }
        if (!isLt2M) {
        this.$message.error('画像サイズは2MB以下にしてください');
        return false;
        }
        return true;
    },
    validateOldPassword(rule, value, callback) {
        var data = {
            companyCode: '000001',
            userId: this.form.userId,
            passWord: value,
        }
        console.log("旧パスワード検証");
        agentLogin(data).then(res => {
            if(res.status == 400) {
                this.$message.error('旧パスワードが正しくありません');
                callback();
            }
        }).catch(error => {
            console.log(error);
            callback();
        });
    },
    validateConfirmPassword(rule, value, callback) {
        const { old_password, new_password, confirm_password } = this.form;

        if (old_password !== '' || new_password !== '' || confirm_password !== '') {

            if (!old_password) {
            if (rule.field === 'old_password') {
                return callback(new Error('旧パスワードを入力してください'));
            } else {
                return callback();
            }
            }

            if (!new_password) {
            if (rule.field === 'new_password') {
                return callback(new Error('新パスワードを入力してください'));
            } else {
                return callback();
            }
            }

            if (!confirm_password) {
            if (rule.field === 'confirm_password') {
                return callback(new Error('確認用パスワードを入力してください'));
            } else {
                return callback();
            }
            }

            // 新パスワードと確認用パスワードの一致検証
            if (new_password !== confirm_password) {
            if (rule.field === 'confirm_password') {
                return callback(new Error('新パスワードと一致しません'));
            } else {
                return callback();
            }
            }
        }

        return callback(); // 成功
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res.url;
      this.$message.success('アップロード成功');
    },
    handleAvatarError() {
        this.previewImageUrl = this.defaultAvatar;
    },
    async submitForm() {
      const valid = await new Promise(resolve => {
        this.$refs.profileForm.validate(valid => resolve(valid));
      });

      const message = '';

      if (!valid) return;

      if(this.addressError){
        this.$message.error('住所情報がエラー存在します。');
        return;
      }

      if (this.avatarFile) {
        try {
          const formData = new FormData();
          formData.append('userId', this.form.userId);
          formData.append('companyCode', this.form.companyCode);
          formData.append('image', this.avatarFile);

          const res = await updateAvatar(formData);
          if (res) {
            this.form.avatarUrl = res.data;
            this.previewImageUrl = res.data;

            const loginForm = {
              userInfo: {
                  userName: getUserInfo().userName,
                  userId: getUserInfo().userId,
                  accountKbn: getUserInfo().accountKbn,
                  companyCode: getUserInfo().companyCode,
                  permissionKbn: getUserInfo().permissionKbn,
                  imageUrl: res.data
              },
              // checked: _this.loginForm.checked,
              token: getToken()
            }

            setUserInfo(loginForm.userInfo);

            this.$message.success('個人画像が更新しました。');
          }

        } catch (err) {
          console.log(err);
          this.$message.error('画像のアップロードに失敗しました');
          return;
        }
      }

      if (this.hasDetailChanged()) {
        const payload = {
            companyCode: this.form.companyCode,
            userId: this.form.userId,
            birthday: this.form.birthday.replace(/-/g, ''),
            sex: this.form.sex,
            tel: this.form.tel, 
            zip: this.form.zip,
            addressPrefecture: this.form.address_prefecture,
            addressMunicipality: this.form.address_municipality,
            addressTown: this.form.address_town,
            addressDetail: this.form.address_detail,
            // image: '',
            // img64Str: this.base64String,
            // img64contentType: this.content64Type
        };
        setUserDetail(payload).then(() => {
            this.$message.success('個人情報が更新しました。');
            this.originalDetail = JSON.parse(JSON.stringify(this.form));
        });
      }

      const { old_password, new_password, confirm_password } = this.form;

      if (old_password !== '' || new_password !== '' || confirm_password !== '') {
        var data = {
            companyCode: '000001',
            userId: this.form.userId,
            passWord: this.form.old_password,
        }
        console.log("旧パスワード検証");
        agentLogin(data).then(res => {
            if(res.status == 400) {
                this.form.old_password= ""
                this.$message.error('旧パスワードが正しくありません');
                return 
            } else {
                const formdata = {
                    token: '',
                    oldPwd:this.form.old_password,
                    newPwd: this.form.new_password,
                    confirmPwd: this.form.confirm_password
                }
                resetPwd(formdata).then(response => {
                    this.form.old_password = '';
                    this.form.new_password = '';
                    this.form.confirm_password = '';
                    console.log('送信内容:', this.form);
                    this.$message.success('パスワードが更新しました。');
                }).catch(error => {
                    console.log(error)
                })
            }
        }).catch(error => {
            console.log(error);
            return
        });
      }
    },
  }
};
</script>

<style scoped>
.form-container {
  width: 900px;
  margin: 40px auto;
}
.avatar-uploader {
  width: 100px;
  height: 100px;
  border: 1px dashed #d9d9d9;
  display: inline-block;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
  text-align: center;
  line-height: 100px;
}
.avatar {
  width: 100%;
  height: 100%;
  display: block;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}
</style>
