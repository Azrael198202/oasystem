<template>
    <div class="home">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="resumeMaintenance-area">
                    <div class="resumeMaintenance-header">
                        <el-row>
                            <el-col :span="10">
                                <span class="card-title-big">履歴経歴書検索</span>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 20px">
                            <div>
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title1" >所属：</span>
                                    <el-select size="small"
                                        v-model="departmentCode" 
                                        placeholder="所属"
                                        style="width: 120px"
                                        clearable>    
                                        <el-option
                                            v-for="item in departmentKbnList"
                                            :key="item.departmentCode"
                                            :label="item.departmentName"
                                            :value="item.departmentCode">
                                            {{item.departmentName}}                                              
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>                                
                                    </el-select>
                                </el-col>
                                <el-col :span="5" class="flex-start">
                                    <span class="select-title1"></span>
                                    <el-input size="small" :disabled="this.resumeNullFlg === '0'" v-model="userIdName" placeholder="社員番号/社員名"  style="width: 220px">
                                    </el-input>
                                </el-col>
                                <el-col :span="4.5" class="flex-start">
                                    <span class="select-title1">役割：</span>
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="responsible" 
                                        placeholder="役割" 
                                        style="width: 100px"
                                        clearable>
                                        <el-option
                                            v-for="item in responsibleCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbn">
                                            {{item.categoryKbnName}}
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title1">年齢：</span>
                                    <el-input size="small" v-model="age" style="width: 60px" maxLength="2"
                                    @input="(v) => (age = v.replace(/[^\d]/g, ''))">
                                    </el-input>
                                </el-col>
                                <el-col :span="3.5" class="flex-start ">
                                    <span class="select-title1">開発年数：</span>
                                    <el-input size="small" :disabled="this.resumeNullFlg === '0'" v-model="experienceYears" style="width: 60px;margin-right:2px"
                                    @input="(v) => (experienceYears = v.replace(/[^\d]/g, ''))"
                                    maxLength="2">
                                    </el-input>
                                    <span class="select-title1">以上</span>
                                </el-col>
                                 

                            </div>
                        </el-row>
                        <el-row>
                            <el-col :span="1.9" class="resume-title1"><div >技術能力</div></el-col>
                            <el-col :span="22" style="font-size:12px;margin-top: 10px ;margin-left:10px;"><div>技術能力( A、指導できる　B、作業できる　C、ソースが読める　D、不可 )</div></el-col>
                        </el-row>
                        <el-row style="margin-left:10px;">
                            <div>
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title1">OS：</span>
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="os" 
                                        placeholder="OS"
                                        style="width: 100px">    
                                        <el-option
                                            v-for="item in OsCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbnAbbreviation">
                                            {{item.categoryKbnName}}                                              
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>                                
                                    </el-select>
                                </el-col>
                                <el-col :span="2" class="flex-start">
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="osKbn" 
                                        placeholder="" 
                                        style="width: 100px">
                                        <el-option
                                            v-for="item in TcCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbn">
                                            {{item.categoryKbnName}}
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title1">言語：</span>
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="pg" 
                                        placeholder="言語"
                                        style="width: 100px">    
                                        <el-option
                                             v-for="item in LgCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbnAbbreviation">
                                            {{item.categoryKbnName}}                                               
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>                                
                                    </el-select>
                                </el-col>
                                <el-col :span="2" class="flex-start">
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="pgKbn" 
                                        placeholder="" 
                                        style="width: 100px">
                                        <el-option
                                            v-for="item in TcCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbn">
                                            {{item.categoryKbnName}}
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title1">DB：</span>
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="db" 
                                        placeholder="DB"
                                        style="width: 100px">    
                                        <el-option
                                             v-for="item in DBCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbnAbbreviation">
                                            {{item.categoryKbnName}}                                                
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>                                
                                    </el-select>
                                </el-col>
                                <el-col :span="2" class="flex-start">
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small" 
                                        v-model="dbKbn" 
                                        placeholder="" 
                                        style="width: 100px">
                                        <el-option
                                            v-for="item in TcCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbn">
                                            {{item.categoryKbnName}}
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title1">その他：</span>
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="etc" 
                                        placeholder="その他"
                                        style="width: 100px">    
                                        <el-option
                                             v-for="item in EctCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbnAbbreviation">
                                            {{item.categoryKbnName}}                                               
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>                                
                                    </el-select>
                                </el-col>
                                <el-col :span="2" class="flex-start">
                                    <el-select :disabled="this.resumeNullFlg === '0'"
                                        size="small"
                                        v-model="etcKbn" 
                                        placeholder="" 
                                        style="width: 100px">
                                        <el-option
                                            v-for="item in TcCategory"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbn">
                                            {{item.categoryKbnName}}
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                            </div>
                        </el-row>
                        <el-row>
                            <el-col :span="1.9" class="resume-title1"><div >参加工程</div></el-col>
                        </el-row>
                        <el-row>
                            <div style="margin-left:10px;display: flex;flex-wrap: wrap">
                                    <el-checkbox-button size="mini" border :disabled="this.resumeNullFlg === '0'" v-model="requirementsFlg" true-label='0' false-label='1' >要件・調査 </el-checkbox-button>
                                    <el-checkbox-button size="mini" border :disabled="this.resumeNullFlg === '0'" v-model="basicFlg" true-label='0' false-label='1' >基本設計 </el-checkbox-button>
                                    <el-checkbox-button size="mini" border :disabled="this.resumeNullFlg === '0'" v-model="specificFlg" true-label='0' false-label='1' >詳細設計 </el-checkbox-button>
                                    <el-checkbox-button size="mini" border :disabled="this.resumeNullFlg === '0'" v-model="codingFlg" true-label='0' false-label='1' >コーディング </el-checkbox-button>
                                    <el-checkbox-button size="mini" border :disabled="this.resumeNullFlg === '0'" v-model="unitTestFlg" true-label='0' false-label='1' >単体テスト </el-checkbox-button>
                                    <el-checkbox-button size="mini" border :disabled="this.resumeNullFlg === '0'" v-model="interfaceTestFlg" true-label='0' false-label='1' >結合テスト </el-checkbox-button>
                                    <el-checkbox-button size="mini" border :disabled="this.resumeNullFlg === '0'" v-model="systemTestFlg" true-label='0' false-label='1' >運用テスト </el-checkbox-button>
                                    <div class="flex-end" style="margin-left:200px">
                                        <el-checkbox style="margin-right:20px" size="small" v-model="resumeNullFlg" border true-label='0' false-label='1' >履歴無し </el-checkbox>
                                        <div class="green-btn"  @click="search()">検索</div>
                                    </div>
                                </div>

                        </el-row>

                        <!-- 履歴経歴書検索（上部） -->
                        <el-row style="margin-top: 10px">

                            <!-- テーブル -->
                            <el-table
                                ref="singleTable"
                                @selection-change="handleSelectionChange"
                                :data="userResumeList"
                                :header-cell-style="{textAlign: 'center',color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '30px'}"
                                :row-style="{height: '45px'}"
                                style="width: 100%"
                                height="510px"
                                border>
                                <el-table-column
                                type="selection"
                                property="selection"
                                width="70"  
                                align="center"                
                            >
                            </el-table-column>
                            <el-table-column
                                        label="No."
                                        width="110"
                                        type="index"
                                        sortable
                                        align="center">
                                    </el-table-column>
                                <el-table-column
                                    prop="userId"
                                    label="社員番号"
                                    width="200"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="userName"
                                    label="社員名"
                                    width="270"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="departmentName"
                                    label="所属"
                                    width="270"
                                    sortable
                                    align="center">
                                    
                                </el-table-column>
                                <el-table-column
                                    prop="age"
                                    label="年齢"
                                    width="150"
                                    sortable
                                    align="center">
                                </el-table-column>

                                <el-table-column width="110" align="center" label="詳細">
                                    <template slot-scope="scope">
                                        <el-button size="small" icon="el-icon-more" circle @click="editBtn(scope.row)"></el-button>
                                    </template >
                                    </el-table-column>
                            </el-table>
                        </el-row>
                        <!-- 下部ボタン -->
                        
                    </div>
                </div>
            </div>
        </div> 
        <div class="btn-fixed1">
            <div class="btn-fixed-main">
                <div class="btn-fixed-main-flex">
                    <div class="flex-start">
                        <div class="back-btn" @click="closeA()" >戻る</div>
                    </div>
                    <div class="flex-end" :style="this.pdfFlg">
                        <div class="pdf-btn1" style="margin-left: 20px" @click="exportPDF()">PDF</div>
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
    import { getUserInfo } from '@/utils/auth';
    import { Message } from 'element-ui';
    import { searchResume, deleteResume, exportPDF,getUserList ,exportPDFs } from "@/api/resume.js";
    import { Avatar, DatePicker } from "element-ui";
    import { getCategory } from '@/api/category'
    import { getDepartment } from '@/api/department';

    export default {
        name: "Resume",
        components: {
            commonHeader,
            commonFootter
        },
        
        data() {
            return {
                userResumeList: [],
                currentResumeVerKbn: '',
                currentUserId:'',
                currentUserName:'',
                resumeVerKbn: '',
                resumeVerName: '',
                updTime: '',
                remark:'',
                os: '',
                osKbn: '',
                pg: '',
                pgKbn: '',
                db: '',
                dbKbn: '',
                etc: '',
                etcKbn: '',
                requirementsFlg: '',
                basicFlg: '',
                specificFlg: '',
                codingFlg: '',
                unitTestFlg: '',
                interfaceTestFlg: '',
                systemTestFlg: '',
                resumeNullFlg: '',
                age: '',
                experienceYears: '',
                responsible: '',
                userIdName: '',
                departmentCode: '',
                pdfFlg: '',

                // Pdfボタン　ダウンロード表現　表示/非表示
                exportPdfLoading: false,
                TcCategory: {index: '',categoryKbnName: '' ,categoryKbn: '' },
                OsCategory: {index: '',categoryKbnName: '' ,categoryKbn: '',categoryKbnAbbreviation: '' },
                DBCategory: {index: '',categoryKbnName: '' ,categoryKbn: '',categoryKbnAbbreviation: '' },
                LgCategory: {index: '',categoryKbnName: '' ,categoryKbn: '',categoryKbnAbbreviation: '' },
                EctCategory: {index: '',categoryKbnName: '' ,categoryKbn: '',categoryKbnAbbreviation: '' },
                responsibleCategory: {index: '',categoryKbnName: '' ,categoryKbn: '' },
                departmentKbnList: [],
                selectStatus:'',
                transportationSelectStatus:'',
                travelSelectStatus:'',
                //社員番号＆社員名
                formSearch: {
                    search_id: ''
                },
                input:'',
                value: '',
                //テーブル
                bulkExport:[],
                //チェックボックス
                multipleSelection: [],
    
            }
        },

        computed: {
        },

        async created(){
            //所属リスト取得
            let data ={companyCode:getUserInfo().companyCode}
            getDepartment(data).then(res => {
                this.departmentKbnList = res.data
                this.departmentKbnList.unshift(blockArray)
            }).catch(error => {
            })

            let responsibleKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '073'
            }
            getCategory(responsibleKbnData).then(res => {
                console.log(res)
                this.responsibleCategory = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })

            let TcKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '072'
            }
            getCategory(TcKbnData).then(res => {
                console.log(res)
                this.TcCategory = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            let OSKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '074'
            }
            getCategory(OSKbnData).then(res => {
                console.log(res)
                this.OsCategory = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            let DBKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '075'
            }
            getCategory(DBKbnData).then(res => {
                console.log(res)
                this.DBCategory = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            let LgKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '076'
            }
            getCategory(LgKbnData).then(res => {
                console.log(res)
                this.LgCategory = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            let EctKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '077'
            }
            getCategory(EctKbnData).then(res => {
                console.log(res)
                this.EctCategory = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            await this.getSerchData()
        },
        methods: {
            
            //チェックボックス選択時実行する
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            search(){
                const that = this
                let data = {
                    companyCode: getUserInfo().companyCode,
                    os: this.os,
                    osKbn: this.osKbn,
                    pg: this.pg,
                    pgKbn: this.pgKbn,
                    db: this.db,
                    dbKbn: this.dbKbn,
                    etc: this.etc,
                    etcKbn: this.etcKbn,
                    requirementsFlg: this.requirementsFlg,
                    basicFlg: this.basicFlg,
                    specificFlg: this.specificFlg,
                    codingFlg: this.codingFlg,
                    unitTestFlg: this.unitTestFlg,
                    interfaceTestFlg: this.interfaceTestFlg,
                    systemTestFlg: this.systemTestFlg,
                    resumeNullFlg: this.resumeNullFlg,
                    age: this.age,
                    experienceYears: this.experienceYears,
                    responsible: this.responsible,
                    userIdName: this.userIdName,
                    departmentCode: this.departmentCode,
                }

        
                getUserList(data).then(res => {
                    console.log(res)
                    that.userResumeList = res.data
                    this.multipleSelection = []
                    sessionStorage.setItem("resume-search-data",JSON.stringify(data))
                    if(this.resumeNullFlg == '0'){
                        this.pdfFlg = 'display:none';
                    } else {
                        this.pdfFlg = '';
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            //画面読み込み時に検索条件がセッションストレージに存在するかを確認し存在した場合に検索条件をセットする
            async getSerchData(){
                let serchData = await JSON.parse(sessionStorage.getItem("resume-search-data"))
                if(serchData !== null){
                    this.companyCode = serchData.companyCode
                    this.os = serchData.os
                    this.osKbn = serchData.osKbn
                    this.pg = serchData.pg
                    this.pgKbn = serchData.pgKbn
                    this.db = serchData.db
                    this.dbKbn = serchData.dbKbn
                    this.etc = serchData.etc
                    this.etcKbn = serchData.etcKbn
                    this.requirementsFlg = serchData.requirementsFlg
                    this.basicFlg = serchData.basicFlg
                    this.specificFlg = serchData.specificFlg
                    this.codingFlg = serchData.codingFlg
                    this.unitTestFlg = serchData.unitTestFlg
                    this.interfaceTestFlg = serchData.interfaceTestFlg
                    this.systemTestFlg = serchData.systemTestFlg
                    this.resumeNullFlg = serchData.resumeNullFlg
                    this.age = serchData.age
                    this.experienceYears = serchData.experienceYears
                    this.responsible = serchData.responsible
                    this.userIdName = serchData.userIdName
                    this.departmentCode = serchData.departmentCode
                    this.search()
                }
            },

            //　編集ボタン押下
            editBtn(row){
                this.btnDetalFlg= true
                // 編集画面open
                this.$router.push({
                    path: '/resume',
                    // query: { companyCode: row.companyCode, userId: row.userId},
                    query: { companyCode: row.companyCode, userId: row.userId, userName: row.userName},
                })  
            },

            exportPDF(){
                if(this.exportLoading == true){
                     return
                }
                this.exportLoading = true

                let data = {
                    companyCode: getUserInfo().companyCode,
                    userIdList: []
                }

                if (this.multipleSelection.length == 0){
                    Message({
                            message: '社員を選択してください。',
                            type: 'error',
                            duration:3000
                        })
                    this.exportLoading = false
                    return
                }

                //　読み込み中のアニメーション
                const loading = this.$loading({
                    lock: true,
                    text: '読み込み中...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });

                // 処理対象を取得する
                for (let index = 0; index < this.multipleSelection.length; index++) {
                    const element = this.multipleSelection[index]
                    data.userIdList.push(element.userId)
                }

                exportPDFs(data).then(res => {
                    let blob = new Blob([res.data], {type: "application/zip"})
                    const href = URL.createObjectURL(blob) 
                    const a = document.createElement('a')
                    a.style.display = 'none'
                    a.href = href
                    a.download = "技術者経歴書.zip"
                    a.click()
                    console.log(res.data)
                    //読み込み中アニメーション非表示
                    setTimeout(() => {
                        loading.close();
                    }, 100);
                    this.exportLoading = false
                }).catch(error => {
                    this.exportLoading = false
                    console.log(error)
                });
            },

            //  戻るボタン押下
            closeA() {
                this.$router.push({
                    path: '/toppage'                  
                });
            }
        }
    }
</script>

<style lang="scss" scope>
    @import '../style/index.scss';
    .resumeMaintenance-area {
        height: 100%;
        width: 100%;
        background-color: #fff;
        box-shadow: 0 3px 23px 0 #95959530;
        padding: 30px 30px;
        border-radius: 6px;

        .resumeMaintenance-header {
            padding: 0px 30px;
        }
    }
    .select-title1 {
        white-space: nowrap;
        font-size: 14px;
        margin-top: 8px;
    }
    // 編集ボタン
    .resumeAdd-btn {
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #4685ff;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(70, 133, 255, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .resumeAdd-btn:hover {
        opacity: 0.6;
    }
    // 新規ボタン
    .new-btn {
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #54c336;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(84, 195, 54, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .new-btn:hover {
        opacity: 0.6;
    }
    // PDFボタン
    .pdf-btn1 {
        margin-left: 12px;
        transition: 0.5s;
        width: 76px;
        height: 26px;
        background-color: #fff;
        border: 2px #ff0000 solid;
        font-size: 14px;
        line-height: 26px;
        text-align: center;
        box-shadow: 0 4px 6px 0 rgba(250, 0, 0, 0.2);
        border-radius: 30px;
        cursor: pointer;
        color: #ff0000;
    }
    .pdf-btn1:hover {
        transition: 0.5s;
        background-color: #fbd4d4;
    }
    // 削除ボタン
    .delete-btn {
        margin-right: 40px;
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #4e4c4c;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(54, 51, 51, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .delete-btn:hover {
        opacity: 0.6;
    }
    // 詳細ボタン
    .detail-btn {
        margin-right: 0px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #a19fa1;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(122, 122, 122, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .detail-btn:hover {
        opacity: 0.6;
    }
    .centerPosition{
        text-align: center;
    }
    .rightPosition{
        text-align:  right;
    }
    .new-period{
        width: 130px;
    }
    .input-title{
        width: 335px;
    }
    .input-contents{
        width: 560px;
    }
    .appended-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .el-upload--picture-card{
        font-size: 16px !important;
    }
    .el-upload-list--picture-card .el-upload-list__item-thumbnail{
        font-size: 16px;
    }
    .title-over{
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .fix_margin_bottom{
        margin-bottom: -22px;
    }
    .checkbox-btn {
        margin: 10px;
    }
    .resume-title1{
        font-size: 16px;
        font-weight: bold;
        line-height: 35px;
    }
    .btn-fixed1 {
        // z-index: 2;
        width: 100%;
        height: 30px;
        padding: 5px 0;
        background-color: #fff;
        box-shadow: 0 3px 23px 0 #95959530;
        // height:60px;
        position: fixed;
        bottom: 50px;
        .btn-fixed-main {
            width: 1300px;
            margin: 0 auto;
        }
        .btn-fixed-main-flex {
            display: flex;
            justify-content: space-between;
        }
    }
    .centerPosition{
        text-align: center;
    }
</style>