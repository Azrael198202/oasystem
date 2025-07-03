<template>
    <div class="home">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="resumeMaintenance-area">
                    <div class="resumeMaintenance-header">
                        <el-form ref="form" :rules="rules1" :model="form" label-width= "115px" size="small">
                            <div class="resumeMaintenance-card">
                                <el-row>
                                    <el-col :span="10">
                                        <span class="card-title-big" v-if="this.$route.query.mode === '1'">新規</span>
                                        <span class="card-title-big" v-if="this.$route.query.mode === '2'">編集</span>
                                        <span class="card-title-big" v-if="this.$route.query.mode === '3'">詳細</span>
                                    </el-col>
                                </el-row>
                                <!-- テーブル -->
                                <el-row style="margin: 10px 0 10px">
                                    <div class="flex-between">
                                        <div class="flex-start">
                                            <div class="my-info">
                                                <span class="my-info-title">社員名：</span>
                                                <span class="my-info-text">{{ userName }}</span>
                                            </div>
                                            <div class="my-info">
                                                <span class="my-info-title">社員番号：</span>
                                                <span class="my-info-text">{{ userId }}</span>
                                            </div>
                                        </div>
                                        <div class="flex-end" v-if="this.$route.query.mode === '1'">
                                        <el-select v-model="resumeVer" placeholder="経歴書バージョン" size="small">
                                                <el-option
                                                    v-for="item in resumeVerList"
                                                    :key="item.resumeVerKbn"
                                                    :label="item.resumeVerKbn"
                                                    :value="item.resumeVerKbn">
                                                </el-option>
                                            </el-select>
                                            <div class="create-btn" style="margin-left: 20px" @click="resumeVerBtn()">参照</div>
                                    </div>
                                    </div>
                                    
                                </el-row>
                                <div style="display:flex;margin-top: 20px">
                                <el-form-item label-width= "220px"  label="経歴書バージョン： " prop="resumeVerKbn" required>
                                    <div style="width: 200px">
                                        <el-input v-model="form.resumeVerKbn" placeholder="" :disabled="this.$route.query.mode !== '1'">
                                        </el-input>
                                    </div>
                                </el-form-item>
                                <el-form-item label-width= "220px" label="最寄り駅： " prop="nearestStation"  required>
                                    <el-input v-model="form.nearestStation" :disabled="this.$route.query.mode === '3'"></el-input>
                                </el-form-item>
                            </div>
                            </div>
                            <div class="resumeMaintenance-card">
                                <el-row>
                                    <el-col :span="24" class="resume-title"><div >最終学歴</div></el-col>
                                </el-row>
                                <div style="display:flex;">
                                    <div>
                                        <el-form-item label-width= "220px" prop="graduationYm" label="卒業年月： " required>
                                            <div style="width: 200px">
                                                <el-date-picker
                                                    v-model="form.graduationYm"
                                                    type="month"
                                                    placeholder=""
                                                    format="yyyy 年 MM 月"
                                                    value-format="yyyyMM" :disabled="this.$route.query.mode === '3'">
                                                </el-date-picker>
                                            </div>
                                        </el-form-item>
                                    </div>
                                    <div>
                                        <el-form-item label-width= "220px" prop="degree" label="学位： " required>
                                            <el-input v-model="form.degree" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </div>
                                </div>
                                <div style="display:flex;">
                                    <el-form-item label-width= "220px" prop="schoolName" label="学校名： ">
                                        <el-input v-model="form.schoolName" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                    <el-form-item label-width= "220px" prop="major" label="専攻学科： ">
                                        <el-input v-model="form.major" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                </div>
                            </div>
                            <div class="resumeMaintenance-card">
                                <el-row>
                                    <el-col :span="24" class="resume-title"><div >資格</div></el-col>
                                </el-row>
                                <div style="display:flex;">
                                    <el-form-item label-width= "220px" prop="certification1" label="資格入力欄１： ">
                                        <el-input v-model="form.certification1" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                    <el-form-item label-width= "220px" prop="certification4" label="資格入力欄４： ">
                                        <el-input v-model="form.certification4" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                </div>
                                <div style="display:flex;">
                                <el-form-item label-width= "220px" prop="certification2" label="資格入力欄２： ">
                                    <el-input v-model="form.certification2" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                    <el-form-item label-width= "220px" prop="certification5" label="資格入力欄５： ">
                                        <el-input v-model="form.certification5" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                </div>
                                <div style="display:flex;">
                                <el-form-item label-width= "220px" prop="certification3" label="資格入力欄３： ">
                                    <el-input v-model="form.certification3" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                    <el-form-item label-width= "220px" prop="certification6" label="資格入力欄６： ">
                                        <el-input v-model="form.certification6" :disabled="this.$route.query.mode === '3'"></el-input>
                                    </el-form-item>
                                </div>
                                <div style="display:flex;">
                                    <div>
                                        <el-form-item label-width= "220px" prop="experienceYears" label="日本向け開発経験（年数）： " required>
                                            <div style="width: 200px">
                                                <el-input type="number" class="mo-input-number" v-model.number="form.experienceYears" style="width: 50px;margin-right: 10px" :disabled="this.$route.query.mode === '3'"></el-input> 年
                                            </div>
                                            
                                        </el-form-item>
                                    </div>
                                    
                                    <el-form-item label-width= "220px" prop="responsible" label="現在役割： ">
                                        <div style="width: 200px">
                                            <el-select v-model="form.responsible" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                <el-option
                                                    v-for="item in responsibleCategory"
                                                    :key="item.categoryKbn"
                                                    :label="item.categoryKbnName"
                                                    :value="item.categoryKbn">
                                                </el-option>
                                            </el-select>
                                        </div>
                                        
                                    </el-form-item>
                                </div>
                            </div>
                            <div class="resumeMaintenance-card">
                                <el-row>
                                    <el-col :span="2" class="resume-title"><div >語学能力</div></el-col>
                                    <el-col :span="22" style="font-size:12px;margin-top: 25px"><div>言語能力( １、殆ど出来ない　２、簡単な会話は可能　３、ゆっくり話せば業務打合せが大体わかる　４、まったく問題がない　５、母語 )</div></el-col>
                                </el-row>
                                <div class="resumeMaintenance-card-block">
                                    <el-row style="margin: 10px">
                                        <el-col :span="4" style="font-size:14px;"><div >【 日本語 】</div></el-col>
                                    </el-row>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgJpBusinessCon" label="業務会話： " required>
                                                    <el-select v-model="form.lgJpBusinessCon" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                        <el-option
                                                            v-for="item in LgCategory"
                                                            :key="item.categoryKbn"
                                                            :label="item.categoryKbnName"
                                                            :value="item.categoryKbn">
                                                        </el-option>
                                                    </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgJpDailyCon" label="日常会話： " required>
                                                    <el-select v-model="form.lgJpDailyCon" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgJpReading" label="読解能力： " required>
                                                    <el-select v-model="form.lgJpReading" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgJpListening" label="聴解能力： " required>
                                                    <el-select v-model="form.lgJpListening" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgJpWritting" label="書く能力： " required>
                                                    <el-select v-model="form.lgJpWritting" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </div>
                                </div>
                                <div class="resumeMaintenance-card-block">
                                    <el-row style="margin: 10px">
                                        <el-col :span="4" style="font-size:14px;"><div > 【 英語 】</div></el-col>
                                    </el-row>
                                    <div style="margin-left:10px;margin-top: 10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgEnBusinessCon" label="業務会話： " required>
                                                    <el-select v-model="form.lgEnBusinessCon" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgEnDailyCon" label="日常会話： " required>
                                                    <el-select v-model="form.lgEnDailyCon" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgEnReading" label="読解能力： " required>
                                                    <el-select v-model="form.lgEnReading" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgEnListening" label="聴解能力： " required>
                                                    <el-select v-model="form.lgEnListening" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="lgEnWritting" label="書く能力： " required>
                                                    <el-select v-model="form.lgEnWritting" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in LgCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </div>
                                </div>
                            </div>
                                
                            <div class="resumeMaintenance-card">
                                <el-row>
                                    <el-col :span="2" class="resume-title"><div >技術能力</div></el-col>
                                    <el-col :span="22" style="font-size:12px;margin-top: 25px"><div>技術能力( A、指導できる　B、作業できる　C、ソースが読める　D、不可 )</div></el-col>
                                </el-row>
                                <div class="resumeMaintenance-card-block">
                                    <el-row style="margin: 10px">
                                        <el-col :span="4" style="font-size:14px;"><div >【 OS 】</div></el-col>
                                    </el-row>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsWindows" label="Windows： " required>
                                                    <el-select v-model="form.tcOsWindows" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsUnix" label="UNIX： " required>
                                                    <el-select v-model="form.tcOsUnix" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsSolaris" label="Solaris： " required>
                                                    <el-select v-model="form.tcOsSolaris" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsHpux" label="HP-UX： " required>
                                                    <el-select v-model="form.tcOsHpux" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsAix" label="AIX： " required>
                                                    <el-select v-model="form.tcOsAix" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsLinux" label="Linux： ">
                                                    <el-select v-model="form.tcOsLinux" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsDos" label="DOS： ">
                                                    <el-select v-model="form.tcOsDos" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcOsMac" label="Mac： ">
                                                    <el-select v-model="form.tcOsMac" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" ></el-col>
                                            <el-col :span="4" ></el-col>
                                        </el-row>
                                    </div>
                                </div>
                                <div class="resumeMaintenance-card-block">
                                    <el-row style="margin: 10px">
                                        <el-col :span="4" style="font-size:14px;"><div >【 言語 】</div></el-col>
                                    </el-row>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgCobol" label="COBOL： " required>
                                                    <el-select v-model="form.tcPgCobol" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgJava" label="JAVA： " required>
                                                    <el-select v-model="form.tcPgJava" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgJsp" label="JSP： " required>
                                                    <el-select v-model="form.tcPgJsp" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgC" label="C/C++： " required>
                                                    <el-select v-model="form.tcPgC" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgVb" label="VB： " required>
                                                    <el-select v-model="form.tcPgVb" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgVc" label="VC/VC++： ">
                                                    <el-select v-model="form.tcPgVc" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgSql" label="SQL/PLSQL： ">
                                                    <el-select v-model="form.tcPgSql" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgNet" label=".NET： ">
                                                    <el-select v-model="form.tcPgNet" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgPerl" label="Perl： ">
                                                    <el-select v-model="form.tcPgPerl" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcPgXml" label="XML： ">
                                                    <el-select v-model="form.tcPgXml" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </div>
                                </div>
                                <div class="resumeMaintenance-card-block">
                                    <el-row style="margin: 10px">
                                        <el-col :span="4" style="font-size:14px;"><div >【 DB 】</div></el-col>
                                    </el-row>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcDbOracle" label="ORACLE： " required>
                                                    <el-select v-model="form.tcDbOracle" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcDbHirdb" label="HiRDB： " required>
                                                    <el-select v-model="form.tcDbHirdb" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcDbSqlserver" label="SQLServer： " required>
                                                    <el-select v-model="form.tcDbSqlserver" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcDbDb2" label="DB2： " required>
                                                    <el-select v-model="form.tcDbDb2" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcDbMysql" label="MySQL： " required>
                                                    <el-select v-model="form.tcDbMysql" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcDbPostgresql" label="PostgreSQL： ">
                                                    <el-select v-model="form.tcDbPostgresql" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcDbAccess" label="ACCESS： ">
                                                    <el-select v-model="form.tcDbAccess" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" ></el-col>
                                            <el-col :span="4" ></el-col>
                                            <el-col :span="4" ></el-col>
                                        </el-row>
                                    </div>   
                                </div>
                                <div class="resumeMaintenance-card-block">
                                    <el-row style="margin: 10px">
                                        <el-col :span="4" style="font-size:14px;"><div >【 その他 】</div></el-col>
                                    </el-row>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcEtcWeblogic" label="WebLogic： " required>
                                                    <el-select v-model="form.tcEtcWeblogic" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcEtcJ2ee" label="J2EE： " required>
                                                    <el-select v-model="form.tcEtcJ2ee" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcEtcWebsphere" label="WebSphere： " required>
                                                    <el-select v-model="form.tcEtcWebsphere" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcEtcTomcat" label="Tomcat： " required>
                                                    <el-select v-model="form.tcEtcTomcat" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcEtcJp1" label="JP１： " required>
                                                    <el-select v-model="form.tcEtcJp1" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div style="margin-left:10px;margin-top:10px">
                                        <el-row>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcEtcCosminexus" label="Cosminexus： ">
                                                    <el-select v-model="form.tcEtcCosminexus" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" >
                                                <el-form-item prop="tcEtcXamarin" label="Xamarin： ">
                                                    <el-select v-model="form.tcEtcXamarin" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in TcCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span="4" ></el-col>
                                            <el-col :span="4" ></el-col>
                                            <el-col :span="4" ></el-col>
                                        </el-row>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- <el-col style="margin-top:10px"></el-col> -->
                        
                            <div class="resumeMaintenance-card">
                                <el-row>
                                    <el-col :span="24" class="resume-title"><div >開発経歴一覧</div></el-col>
                                </el-row>
                                <el-table
                                    style="z-index: 0"
                                    ref="singleTable"
                                    :data="form.resumeDetailList"
                                    :header-cell-style="{textAlign: 'center',color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                                    highlight-current-row
                                    :row-style="{height: '62px'}"
                                    height="550px"
                                    border>
                                    <el-table-column
                                        prop="resumeSeqNo"
                                        label="No."
                                        width="110"
                                        type="index"
                                        sortable
                                        align="center">
                                    </el-table-column>
                                    
                                    <el-table-column
                                        prop="developPeriodFromTo"
                                        label="開発期間"
                                        width="240"
                                        sortable
                                        align="center">
                                    </el-table-column>
                                    <el-table-column
                                        prop="developSummarySys"
                                        label="システム名"
                                        sortable
                                        align="center">
                                    </el-table-column>
                                    <el-table-column
                                        prop="developLanguage1"
                                        label="言語"
                                        width="130"
                                        sortable
                                        align="center">
                                    </el-table-column>
                                    <el-table-column
                                        prop="developDb1"
                                        label="DB"
                                        width="130"
                                        sortable
                                        align="center">
                                    </el-table-column>
                                    <el-table-column
                                        prop="developOs1"
                                        label="OS"
                                        width="130"
                                        sortable
                                        align="center">
                                    </el-table-column>
                                    <el-table-column width="55">
                                    <template slot-scope="scope">
                                        <el-button circle size="mini" icon="el-icon-edit" @click="editDetail(scope.row)"></el-button>
                                    </template >
                                    </el-table-column>
                                    <el-table-column width="55">
                                        <template slot-scope="scope">
                                            <el-button circle size="mini" icon="el-icon-delete-solid" @click="deleteOne(scope.row)" :disabled="mode === '3'"></el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                                <div class="flex-end" style="margin-top: 20px">
                                    <div class="resumeAdd-btn" @click="insertBtn()" v-if="this.$route.query.mode !== '3'">追加</div>
                                </div>
                            </div>
                            
                            <div class="resumeMaintenance-card">
                                <el-row>
                                    <el-col :span="24" class="resume-title"><div >備考</div></el-col>
                                </el-row>
                                <el-form-item label-width= "0px" prop="remarks">
                                    <el-input :disabled="this.$route.query.mode === '3'"
                                        type="textarea"
                                        :rows="2"
                                        v-model="form.remarks">
                                    </el-input>
                                </el-form-item>
                            </div>
                            <div style="width: 100%;height: 100px"></div>
                        </el-form>
                        <!-- 下部ボタン -->
                        
                        <!-- 追加・編集ダイアログ -->
                        <el-form :inline="true" :model="ruleForm" :rules="rules2"  ref="ruleForm" label-width="150px" label-hight="500px" class="demo-ruleForm">
                            <el-dialog
                                title="開発経歴入力"
                                :visible.sync="resumeCreateDialog"
                                width="50%"
                                center>
                                <!-- 適用期間：-->
                                <div style="display: flex">   
                                    <el-form-item label-width="120px" label="開発期間 :" required>
                                        <el-col>
                                            <el-form-item prop="dialogDate">
                                                <el-date-picker
                                                    v-model="ruleForm.dialogDate"
                                                    type="daterange"
                                                    range-separator="～"
                                                    start-placeholder="開発期間From："
                                                    end-placeholder="開発期間To："
                                                    value-format="yyyyMMdd"
                                                    format="yyyy/MM"
                                                    :disabled="this.$route.query.mode === '3'">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                    </el-form-item>
                                </div>
                                <div>
                                    <el-row>
                                        <el-form-item label-width="120px" prop="developSummarySys" label="システム名：" required>
                                            <el-input v-model="ruleForm.developSummarySys" style="width: 350px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-row>
                                     <el-row>
                                            <el-form-item label-width="120px" prop="developSummarySry" label="概要：" required>
                                                <el-input v-model="ruleForm.developSummarySry" style="width: 350px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-row>
                                    <el-row>
                                            <el-form-item label-width="120px" prop="developSummaryCts" label="担当内容：" required>
                                                <el-input v-model="ruleForm.developSummaryCts" style="width: 350px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-row>
                                    <el-row>
                                            <el-form-item label-width="120px" prop="developSummaryMbs" label="メンバー数：" required>
                                                <el-input v-model.number="ruleForm.developSummaryMbs" style="width: 350px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-row>
                                    <el-row>
                                        <el-form-item prop="responsible" label="役割： " label-width="120px">
                                            <div>
                                                <el-select style="width: 350px;" v-model="ruleForm.responsible" placeholder="" :disabled="this.$route.query.mode === '3'">
                                                    <el-option
                                                        v-for="item in responsibleCategory"
                                                        :key="item.categoryKbn"
                                                        :label="item.categoryKbnName"
                                                        :value="item.categoryKbn">
                                                    </el-option>
                                                </el-select>
                                            </div>
                                        </el-form-item>
                                    </el-row>
                                </div>
                                <div class="popup-card">
                                    <el-col style="margin-left:30px">
                                        <el-form-item label-position="top" prop="developLanguage1" label="言語欄１：" label-width="85px">
                                            <el-input v-model="ruleForm.developLanguage1" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col>
                                        <el-form-item label-position="top" prop="developOs1" label="OS欄１：" label-width="80px">
                                            <el-input v-model="ruleForm.developOs1" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col>
                                        <el-form-item label-position="top" prop="developDb1" label="DB欄１：" label-width="80px">
                                            <el-input v-model="ruleForm.developDb1" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </div>
                                <div class="popup-card">
                                    <el-col style="margin-left:30px">
                                        <el-form-item label-position="top" prop="developLanguage2" label="言語欄２：" label-width="85px">
                                            <el-input v-model="ruleForm.developLanguage2" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col>
                                        <el-form-item label-position="top" prop="developOs2" label="OS欄２：" label-width="80px">
                                            <el-input v-model="ruleForm.developOs2" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col>
                                        <el-form-item label-position="top" prop="developDb2" label="DB欄２：" label-width="80px">
                                            <el-input v-model="ruleForm.developDb2" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </div>
                                <div class="popup-card">
                                    <el-col style="margin-left:30px">
                                        <el-form-item label-position="top" prop="developLanguage3" label="言語欄３：" label-width="85px">
                                            <el-input v-model="ruleForm.developLanguage3" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col>
                                        <el-form-item label-position="top" prop="developOs3" label="OS欄３：" label-width="80px">
                                            <el-input v-model="ruleForm.developOs3" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col>
                                        <el-form-item label-position="top" prop="developDb3" label="DB欄３：" label-width="80px">
                                            <el-input v-model="ruleForm.developDb3" style="width: 180px;" :disabled="this.$route.query.mode === '3'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </div>

                                <div style="margin-left:10px;display: flex;flex-wrap: wrap">
                                    <div class="checkbox-btn"><el-checkbox v-model="ruleForm.requirementsFlg" true-label='0' false-label='1' :disabled="this.$route.query.mode === '3'">要件・調査 </el-checkbox></div>
                                    <div class="checkbox-btn"><el-checkbox v-model="ruleForm.basicFlg" true-label='0' false-label='1' :disabled="this.$route.query.mode === '3'">基本設計 </el-checkbox></div>
                                    <div class="checkbox-btn"><el-checkbox v-model="ruleForm.specificFlg" true-label='0' false-label='1' :disabled="this.$route.query.mode === '3'">詳細設計 </el-checkbox></div>
                                    <div class="checkbox-btn"><el-checkbox v-model="ruleForm.codingFlg" true-label='0' false-label='1' :disabled="this.$route.query.mode === '3'">コーディング </el-checkbox></div>
                                    <div class="checkbox-btn"><el-checkbox v-model="ruleForm.unitTestFlg" true-label='0' false-label='1' :disabled="this.$route.query.mode === '3'">単体テスト </el-checkbox></div>
                                    <div class="checkbox-btn"><el-checkbox v-model="ruleForm.interfaceTestFlg" true-label='0' false-label='1' :disabled="this.$route.query.mode === '3'">結合テスト </el-checkbox></div>
                                    <div class="checkbox-btn"><el-checkbox v-model="ruleForm.systemTestFlg" true-label='0' false-label='1' :disabled="this.$route.query.mode === '3'">運用テスト </el-checkbox></div>
                                </div>

                                <!-- 入力画面下部ボタン -->
                                <div class="flex-end">    
                                    <el-form-item>
                                        <el-button type="primary" @click="resumeDetailConfirm()" v-if="this.$route.query.mode !== '3'">確定</el-button>
                                        <el-button type="primary" @click="resumeDetailClose()" v-if="this.$route.query.mode === '3'">閉じる</el-button>
                                    </el-form-item>
                                </div>
                            </el-dialog>
                        </el-form>
                    </div>
                    
                </div>
            </div>
        </div> 
        <div class="btn-fixed">
            <div class="btn-fixed-main">
                <div class="btn-fixed-main-flex">
                    <div class="flex-start">
                        <div class="back-btn" @click="closeA()" >戻る</div>
                    </div>
                    <div class="flex-end" >
                        <div class="create-btn" style="margin-left: 20px" @click="addBtn()" v-if="this.$route.query.mode === '1'">作成</div>
                        <div class="resumeAdd-btn" style="margin-left: 20px" @click="addBtn()" v-if="this.$route.query.mode === '2'">更新</div>
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
    import { getUserInfo, getUserDetailedInfo } from '@/utils/auth';
    // import { getUserDetail } from '@/api/user';
    import { getCategory } from '@/api/category'
    import { Message } from 'element-ui';
    import { insertResume,getResume,updateResume,getResumeVerList } from "@/api/resume.js";
    import { Avatar, DatePicker } from "element-ui";

    export default {
        name: "Resume",
        components: {
            commonHeader,
            commonFootter
        },
        
        data() {
            return {
              resumeCreateDialog:false,
              resumeDetailMode:'',
              resumeDetail: '',
              resumeDetailList:[],
              currentResumeVerKbn: '',
              resumeVerKbn: '',
              resumeVer: '',
              resumeVerFlg:true,
              resumeVerName: '',
              updTime: '',
              remark:'',
              mode:this.$route.query.mode,
              LgCategory: {index: '',categoryKbnName: '' ,categoryKbn: '' },
              resumeVerList: {index: '',resumeVerKbn: '' },
              TcCategory: {index: '',categoryKbnName: '' ,categoryKbn: '' },
              responsibleCategory: {index: '',categoryKbnName: '' ,categoryKbn: '' },
              userName: this.$route.query.userName !== undefined ?this.$route.query.userName:getUserDetailedInfo().firstName + ' ' +getUserDetailedInfo().lastName,
              userId: this.$route.query.userId !== undefined ?this.$route.query.userId:getUserDetailedInfo().userId,
              form: {
                resumeDetailList:[{
                    developPeriodFromTo:''
                }],
                resumeVerKbn:'',
                nearestStation:'',
                companyCode: getUserInfo().companyCode,
                userId: this.$route.query.userId !== undefined ?this.$route.query.userId:getUserDetailedInfo().userId,
              },
              ruleForm: {
                basicFlg: '1',
                codingFlg: '1',
                delFlg: '',
                developDb1: '',
                developDb2: '',
                developDb3: '',
                developLanguage1: '',
                developLanguage2: '',
                developLanguage3: '',
                developOs1: '',
                developOs2: '',
                developOs3: '',
                developPeriodFrom: '',
                developPeriodFromTo: '',
                developPeriodTo: '',
                developSummaryCts: '',
                developSummaryMbs: '',
                developSummarySry: '',
                developSummarySys: '',
                dialogDate: [],
                interfaceTestFlg: '1',
                requirementsFlg: '1',
                responsible: '',
                resumeSeqNo: '',
                resumeVerKbn: '',
                specificFlg: '1',
                systemTestFlg: '1',
                unitTestFlg: '1',
                userId: ''
              },
              rules1: {
                    resumeVerKbn: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    nearestStation: [{ required: true, message: '入力必須項目です。', trigger: ['blur','change'] }],
                    graduationYm: [ { required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    degree: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    experienceYears: [
                        { type: 'number', required: true, message: '数字で入力必須です。', trigger: 'blur' }
                    ],
                    lgJpBusinessCon: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgJpDailyCon: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgJpReading: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgJpListening: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgJpWritting: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgEnBusinessCon: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgEnDailyCon: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgEnReading: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgEnListening: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    lgEnWritting: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsWindows: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsUnix: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsSolaris: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsHpux: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsAix: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsLinux: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsDos: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcOsMac: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgCobol: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgJava: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgJsp: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgC: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgVb: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgVc: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgSql: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgNet: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgPerl: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcPgXml: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcDbOracle: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcDbHirdb: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcDbSqlserver: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcDbDb2: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcDbMysql: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcDbPostgresql: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcDbAccess: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcEtcWeblogic: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcEtcJ2ee: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcEtcWebsphere: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcEtcTomcat: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcEtcJp1: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcEtcCosminexus: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                    tcEtcXamarin: [{ required: true, message: '入力必須項目です。', trigger: 'blur' }],
                },
                rules2: {
                    dialogDate: [ { required: true, message: '入力必須項目です。', trigger: ['blur','change'] }],
                    developSummarySys: [{ required: true, message: '入力必須項目です。', trigger: ['blur','change'] }],
                    developSummarySry: [{ required: true, message: '入力必須項目です。', trigger: ['blur','change'] }],
                    developSummaryCts: [{ required: true, message: '入力必須項目です。', trigger: ['blur','change'] }],
                    developSummaryMbs: [{ type: 'number', required: true, message: '数字で入力必須です。', trigger: 'blur' }],
                }
            }
        },

        computed: {
        },

        created() {
            
            let LgKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '071'
            }
            getCategory(LgKbnData).then(res => {
                console.log(res)
                this.LgCategory = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
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
            let data = {
                    companyCode: getUserInfo().companyCode,
                    userId: this.$route.query.userId !== undefined ?this.$route.query.userId:getUserDetailedInfo().userId,
                }
                getResumeVerList(data).then(res => {

                    this.resumeVerList = res.data
                }).catch(error => {
                               
                })
            
            // let usrdata = {
            //     companyCode: getUserInfo().companyCode,
            //     userId: this.userId,
            // }
            // getUserDetail(usrdata).then( res => {
            //     this.userName = res.data[0].firstName + "　" + res.data[0].lastName
            // }).catch(error => {
            //     console.log(error)
            // })
                
            if(this.$route.query.mode != '1'){
              this.getResumeDetail()
            }else{
                this.form.resumeDetailList=[]
            } 
        },
        methods: {
            insertBtn(){
                this.$refs['ruleForm'].resetFields();
                this.ruleForm= {
                    basicFlg: '1',
                    codingFlg: '1',
                    delFlg: '',
                    developDb1: '',
                    developDb2: '',
                    developDb3: '',
                    developLanguage1: '',
                    developLanguage2: '',
                    developLanguage3: '',
                    developOs1: '',
                    developOs2: '',
                    developOs3: '',
                    developPeriodFrom: '',
                    developPeriodFromTo: '',
                    developPeriodTo: '',
                    developSummaryCts: '',
                    developSummaryMbs: '',
                    developSummarySry: '',
                    developSummarySys: '',
                    dialogDate: [],
                    interfaceTestFlg: '1',
                    requirementsFlg: '1',
                    responsible: '',
                    resumeSeqNo: '',
                    resumeVerKbn: '',
                    specificFlg: '1',
                    systemTestFlg: '1',
                    unitTestFlg: '1',
                    userId: ''
                }
                this.resumeCreateDialog = true
                this.resumeDetailMode = '1'
            },

            resumeVerBtn(){
                if (this.resumeVer ==''){
                    Message({
                                    message: '経歴書バージョンを選択してください。',
                                    type: 'error',
                                    duration: 4 * 1000
                                })
                }else {
                    let data = {
                        companyCode: getUserInfo().companyCode,
                        userId: this.$route.query.userId !== undefined ?this.$route.query.userId:getUserDetailedInfo().userId,
                        resumeVerKbn : this.resumeVer
                    }
                    getResume(data).then(res => {
                        this.form = res.data
                        for (var i = 0; i < this.form.resumeDetailList.length; i++){
                            var year = this.form.resumeDetailList[i].developPeriodFrom.substring(0, 4)
                            this.form.resumeDetailList[i].developPeriodFromTo = this.form.resumeDetailList[i].developPeriodFrom.substring(0, 4)
                            + '/' + this.form.resumeDetailList[i].developPeriodFrom.substring(4, 6) + '～' 
                            + this.form.resumeDetailList[i].developPeriodTo.substring(0, 4)
                            + '/' + this.form.resumeDetailList[i].developPeriodTo.substring(4, 6)
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
                
            },
            
            resumeDetailConfirm(){
                console.log(this.ruleForm)
                this.$refs['ruleForm'].validate((valid) => {
                    if(valid){
                        this.ruleForm.developPeriodFrom = this.ruleForm.dialogDate[0]
                        this.ruleForm.developPeriodTo = this.ruleForm.dialogDate[1]
                        this.ruleForm.developPeriodFromTo = this.ruleForm.dialogDate[0].substring(0, 4)
                        + '/' + this.ruleForm.dialogDate[0].substring(4, 6)+ '～' 
                        + this.ruleForm.dialogDate[1].substring(0, 4)
                        + '/' + this.ruleForm.dialogDate[1].substring(4, 6)
                        const data = {
                                basicFlg:this.ruleForm.basicFlg,
                                codingFlg:this.ruleForm.codingFlg,
                                delFlg:this.ruleForm.delFlg,
                                developDb1:this.ruleForm.developDb1,
                                developDb2:this.ruleForm.developDb2,
                                developDb3:this.ruleForm.developDb3,
                                developLanguage1:this.ruleForm.developLanguage1,
                                developLanguage2:this.ruleForm.developLanguage2,
                                developLanguage3:this.ruleForm.developLanguage3,
                                developOs1:this.ruleForm.developOs1,
                                developOs2:this.ruleForm.developOs2,
                                developOs3:this.ruleForm.developOs3,
                                developPeriodFrom:this.ruleForm.developPeriodFrom,
                                developPeriodFromTo:this.ruleForm.developPeriodFromTo,
                                developPeriodTo:this.ruleForm.developPeriodTo,
                                developSummaryCts:this.ruleForm.developSummaryCts,
                                developSummaryMbs:this.ruleForm.developSummaryMbs,
                                developSummarySry:this.ruleForm.developSummarySry,
                                developSummarySys:this.ruleForm.developSummarySys,
                                dialogDate:this.ruleForm.dialogDate,
                                interfaceTestFlg:this.ruleForm.interfaceTestFlg,
                                requirementsFlg:this.ruleForm.requirementsFlg,
                                responsible:this.ruleForm.responsible,
                                resumeSeqNo:this.ruleForm.resumeSeqNo,
                                resumeVerKbn:this.ruleForm.resumeVerKbn,
                                specificFlg:this.ruleForm.specificFlg,
                                systemTestFlg:this.ruleForm.systemTestFlg,
                                unitTestFlg:this.ruleForm.unitTestFlg,
                                userId:this.ruleForm.userId
                            }
                        if(this.resumeDetailMode == '1'){
                            this.form.resumeDetailList.push(data) 
                            for(var i = 0; i < this.form.resumeDetailList.length; i++){
                                if(i < 9){
                                    this.form.resumeDetailList[i].resumeSeqNo = '0' + (i + 1)
                                }else{
                                    this.form.resumeDetailList[i].resumeSeqNo = i + 1
                                }
                            }
                        }else{
                            for(var i = 0; i < this.form.resumeDetailList.length; i++){
                                if(this.form.resumeDetailList[i].resumeSeqNo == this.ruleForm.resumeSeqNo){
                                    this.form.resumeDetailList[i] = data
                                    break;
                                }
                            }
                        }
                        console.log(this.form)
                        this.resumeCreateDialog = false;
                    } else {
                        console.log('error submit!!');
                        return false;
                    }  
                 });
                
            },

            resumeDetailClose(){
               this.resumeCreateDialog = false;
            },

            deleteOne(row){
                let list = []
                for (var i = 0; i < this.form.resumeDetailList.length; i++){
                    if (this.form.resumeDetailList[i].resumeSeqNo != row.resumeSeqNo){
                        list.push(this.form.resumeDetailList[i])
                    }
                }
                this.form.resumeDetailList = list
            },
            // 
            addBtn(){
                this.$refs['form'].validate((valid) => {
                    if(valid){
                        if(this.$route.query.mode == '1'){
                            insertResume(this.form).then(res => {
                                console.log(res)
                                Message({
                                    message: 'データを追加しました。',
                                    type: 'success',
                                    duration: 4 * 1000
                                })
                                this.$router.push({
                                    path: '/resume' ,
                                    query: { userId: this.userId,userName: this.userName }                 
                                });
                                this.$refs['form'].resetFields();
                            }).catch(error => {
                                Message({
                                    message: '追加されませんでした。再度、ご入力ください。',
                                    type: 'error',
                                    duration: 4 * 1000
                                })
                                console.log(error)
                            })

                        }else{
                            updateResume(this.form).then(res => {
                                console.log(res)
                                Message({
                                    message: 'データを更新しました。',
                                    type: 'success',
                                    duration: 4 * 1000
                                })
                                this.$router.push({
                                    path: '/resume' ,
                                    query: { userId: this.userId,userName: this.userName }                          
                                });
                                this.$refs['form'].resetFields();
                            }).catch(error => {
                                Message({
                                    message: 'データが更新されませんでした。再度、ご確認ください。',
                                    type: 'error',
                                    duration: 4 * 1000
                                })
                                console.log(error)
                            })
                        }
                    }else{
                        console.log('error submit!!');
                        return false;
                    }
                });  
            },
            getResumeDetail(){
                let searchData = {
                    resumeVerKbn : this.$route.query.resumeVerKbn,
                    companyCode: getUserInfo().companyCode,
                    userId: this.userId
                }

                getResume(searchData).then(res => {
                    this.form = res.data
                    for (var i = 0; i < this.form.resumeDetailList.length; i++){
                        var year = this.form.resumeDetailList[i].developPeriodFrom.substring(0, 4)
                        this.form.resumeDetailList[i].developPeriodFromTo = this.form.resumeDetailList[i].developPeriodFrom.substring(0, 4)
                        + '/' + this.form.resumeDetailList[i].developPeriodFrom.substring(4, 6) + '～' 
                        + this.form.resumeDetailList[i].developPeriodTo.substring(0, 4)
                        + '/' + this.form.resumeDetailList[i].developPeriodTo.substring(4, 6)
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            editDetail(row){
                console.log(row)
                this.resumeDetailMode = '2'
                this.resumeCreateDialog = true
                this.ruleForm = {
                    basicFlg: row.basicFlg,
                    codingFlg: row.codingFlg,
                    delFlg: row.delFlg,
                    developDb1: row.developDb1,
                    developDb2: row.developDb2,
                    developDb3: row.developDb3,
                    developLanguage1: row.developLanguage1,
                    developLanguage2: row.developLanguage2,
                    developLanguage3: row.developLanguage3,
                    developOs1: row.developOs1,
                    developOs2: row.developOs2,
                    developOs3: row.developOs3,
                    developPeriodFrom: row.developPeriodFrom,
                    developPeriodFromTo: row.developPeriodFromTo,
                    developPeriodTo: row.developPeriodTo,
                    developSummaryCts: row.developSummaryCts,
                    developSummaryMbs: parseInt(row.developSummaryMbs),
                    developSummarySry: row.developSummarySry,
                    developSummarySys: row.developSummarySys,
                    dialogDate: [row.developPeriodFrom,row.developPeriodTo],
                    interfaceTestFlg: row.interfaceTestFlg,
                    requirementsFlg: row.requirementsFlg,
                    responsible: row.responsible,
                    resumeSeqNo: row.resumeSeqNo,
                    resumeVerKbn: row.resumeVerKbn,
                    specificFlg: row.specificFlg,
                    systemTestFlg: row.systemTestFlg,
                    unitTestFlg: row.unitTestFlg,
                    userId: row.userId
                }
                
                // this.ruleForm.basicFlg = this.ruleForm.basicFlg=='0'? true:false
                // this.ruleForm.specificFlg = this.ruleForm.specificFlg=='0'? true:false
                // this.ruleForm.codingFlg = this.ruleForm.codingFlg=='0'? true:false
                // this.ruleForm.unitTestFlg = this.ruleForm.unitTestFlg=='0'? true:false
                // this.ruleForm.interfaceTestFlg = this.ruleForm.interfaceTestFlg=='0'? true:false
                // this.ruleForm.systemTestFlg = this.ruleForm.systemTestFlg=='0'? true:false
                // this.ruleForm.requirementsFlg = this.ruleForm.requirementsFlg=='0'? true:false
            },

            //  戻るボタン押下
            closeA(){
                this.$router.push({
                    path: '/resume' ,
                    query: { userId: this.userId,userName: this.userName }                     
                });
            },
        }
        
    }
</script>

<style lang="scss" scope>
    @import '../style/index.scss';
    .resume-title{
        font-size: 20px;
        font-weight: bold;
        line-height: 60px;
    }
    .popup-card {
        display: flex;
        background-color: #f5f5f5;
        width: 100%;
        padding: 10px 0px;
        margin: 10px 0;
    }
    .checkbox-btn {
        margin: 10px;
    }
    .btn-fixed {
        // z-index: 2;
        width: 100%;
        height: auto;
        padding: 20px 0;
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
    .resumeMaintenance-card {
        // width: 100%;
        margin: 20px 0;
        background-color: #fff;
        box-shadow: 0 3px 23px 0 #95959530;
        padding: 20px 30px 30px 30px;
        border-radius: 6px;
        .resumeMaintenance-card-block {
            background-color: #f5f5f5;
            width: 100%;
            padding: 10px 0px;
            margin: 10px 0;
        }
    }
    .resumeMaintenance-area {
        height: 100%;
        width: 100%;
        // background-color: #fff;
        // box-shadow: 0 3px 23px 0 #95959530;
        // padding: 30px 30px;
        // border-radius: 6px;

    }
    .select-title {
        font-weight: bold;
        white-space: nowrap;
        font-size: 16px;
        margin-top: 8px;
    }
    // 追加ボタン＆更新ボタン
    .resumeAdd-btn {
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #fd5555;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(253, 85, 85, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .resumeAdd-btn:hover {
        opacity: 0.6;
    }
    // 作成ボタン
    .create-btn {
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
    .create-btn:hover {
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
    .mo-input-number input::-webkit-outer-spin-button,
    .mo-input-number input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
    .mo-input-number input[type="number"] {
        -moz-appearance: textfield;
    }
</style>