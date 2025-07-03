<template>
    <div class="home">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="holidayList-area">
                    <div class="holidayList-header">
                        <el-row>
                            <el-col :span="10">
                                <span class="card-title-big">有休一覧</span>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 40px">
                                <el-col :span="5" class="flex-start">
                                    <div class="select-title">所属:</div>
                                    <el-select v-model="departmentKbnStatus" placeholder="">
                                        <!-- 部門マスタを取得して表示 -->                                      
                                        <el-option
                                            v-for="item in departmentKbnList"
                                            :key="item.departmentCode"
                                            :label="item.departmentName"
                                            :value="item.departmentCode">
                                            {{item.departmentName}}                                              
                                        </el-option>                                   
                                    </el-select>
                                </el-col>
                                    <!-- <el-col :span="5" class="flex-start">
                                        <div class="select-title">年:</div>
                                        <el-date-picker
                                            v-model="targetYear"
                                            type="year"
                                            :editable="false"
                                            :clearable="false">
                                        </el-date-picker>
                                    </el-col> -->
                                <el-col :span="5" class="flex-start">
                                    <span class="select-title">年：</span>
                                        <el-date-picker
                                            v-model="targetYear"
                                            type="year"
                                            :editable="false"
                                            :clearable="false">
                                        </el-date-picker>
                                </el-col> 
                                <el-col :span="5" class="flex-start">
                                    <span class="select-title">月：</span>
                                        <el-select v-model="monthStatus" placeholder="">
                                            <el-option
                                            v-for="item in months"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                            </el-option>
                                        </el-select>
                                </el-col>
                            <el-col :span="9" class="flex-end" style="margin-top:5px">
                                <div class="green-btn"  @click="search()">検索</div>
                            </el-col>
                        </el-row>
                    </div>
                    
                    <el-col style="margin-top:20px"></el-col>
                               
                    <el-table
                        :data="tableData" 
                        :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                         
                        style="width: 100%"
                        height="640px"
                        :cell-class-name="tableCellClassName"
                        border>
                            
                        <el-table-column
                            prop="userIdName"
                            label="ID 社員名" 
                            width="140"                  
                            >
                        </el-table-column>
                        <el-table-column
                            prop="closeMonth"
                            label="最終締め年月"
                            width="110"
                            >
                        </el-table-column>
                        <el-table-column
                            prop="paidHolidayRemaining"
                            label="有休残日数"
                            width="100"
                            align="right">
                        </el-table-column>
                        <el-table-column                       
                            :prop="prp[monthStatusIndex+0].list"
                            :label="mon[monthStatusIndex+0].label"
                            width="60"
                            align="right">
                            
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+1].list"
                            :label="mon[monthStatusIndex+1].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+2].list"
                            :label="mon[monthStatusIndex+2].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+3].list"
                            :label="mon[monthStatusIndex+3].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+4].list"
                            :label="mon[monthStatusIndex+4].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+5].list"
                            :label="mon[monthStatusIndex+5].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+6].list"
                            :label="mon[monthStatusIndex+6].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+7].list"
                            :label="mon[monthStatusIndex+7].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                           :prop="prp[monthStatusIndex+8].list"
                            :label="mon[monthStatusIndex+8].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+9].list"
                            :label="mon[monthStatusIndex+9].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+10].list"
                            :label="mon[monthStatusIndex+10].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        <el-table-column
                            :prop="prp[monthStatusIndex+11].list"
                            :label="mon[monthStatusIndex+11].label"
                            width="60"
                            align="right">
                        </el-table-column>
                        
                        <el-table-column
                            prop="nextPaidFullDay"
                            label="次回有休付与日"
                            width="130"
                            >
                        </el-table-column>
                        <el-table-column
                            prop="categoryKbnOption2"
                            label="次回有休付与日数"
                            width="80"
                            align="right">
                        </el-table-column>
                    </el-table>
                     <div class="back-btn" style="margin: 20px" @click="backToToppage()">戻る</div>
                </div>
            </div>
        </div>
        <commonFootter></commonFootter>
    </div>
</template>

<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { getDepartment } from '@/api/department'

import { getUserInfo, setUserDetailedInfo } from '@/utils/auth'

import { RequestCompleteHoliday }from '@/api/requestHoliday'

    export default {
        
        name: "HolidayList",
        components: {
            commonHeader,
            commonFootter
        },
        created() {
            this.current=new Date
            this.currentDate=this.current.toDateString()
            this.currentYear=this.current.getFullYear()
            this.companyCode = getUserInfo().companyCode   
                  
            //所属の初期処理
            this.getDepartmentKbnlist()
            //現在年の初期代入処理
            this.targetYear=this.currentDate+' 00:00:00 GMT+0900 (日本標準時)'
            console.log(this.currentDate)      
        },
        
        data() {
            return {
                departmentKbnList: [],
                departmentKbnStatus: [],
                kariDepartmentKbnStatus: [],
                tableData:[],
                closeMonth:'',
                targetYear: '',
                categoryKbnOption2:[],
                kariYear:'',
                kariMonth:'',
                companyCode:'',
                userId:'',
                requestUserId:'',              
                nextassignmentday:'',
                nextassignmentquantity:'',
                nextAssignmentMonth:'',
                planYear:'',
                planMonth:'',
                paidHolidayRemaining:'',
                tableRimData:[],
                tableMonData:[],
                tableAllData:[],
                kariDataMon:[],
                kariDataRim:[],
                kariDataAll:[],
                dataMemo:'',
                current:'',
                currentYear:'',
                currentDate:'', 
                prp:[{list:'janCrt'},{list:'febCrt'},{list:'marCrt'},{list:'aplCrt'},{list:'mayCrt'},{list:'junCrt'},{list:'julCrt'},{list:'augCrt'},{list:'sepCrt'},{list:'octCrt'},{list:'novCrt'},{list:'decCrt'},
                        {list:'janNext'},{list:'febNext'},{list:'marNext'},{list:'aplNext'},{list:'mayNext'},{list:'junNext'},{list:'julNext'},{list:'augNext'},{list:'sepNext'},{list:'octNext'},{list:'novNext'},{list:'decNext'}],
                mon:[{label:'1月'},{label:'2月'},{label:'3月'},{label:'4月'},{label:'5月'},{label:'6月'},{label:'7月'},{label:'8月'},{label:'9月'},{label:'10月'},{label:'11月'},{label:'12月'},
                        {label:'1月'},{label:'2月'},{label:'3月'},{label:'4月'},{label:'5月'},{label:'6月'},{label:'7月'},{label:'8月'},{label:'9月'},{label:'10月'},{label:'11月'},{label:'12月'}],
                months: [{
                value: '0',
                label: '1月'
                },{
                value: '1',
                label: '2月'
                },{
                value: '2',
                label: '3月'
                },{
                value: '3',
                label: '4月'
                },{
                value: '4',
                label: '5月'
                },{
                value: '5',
                label: '6月'
                },{
                value: '6',
                label: '7月'
                },{
                value: '7',
                label: '8月'
                }, {
                value: '8',
                label: '9月'
                }, {
                value: '9',
                label: '10月'
                }, {
                value: '10',
                label: '11月'
                }, {
                value: '11',
                label: '12月'
                }],
                monthStatus:'0',
                monthStatusIndex:0
                }
            
        },

        computed: {

        },

        methods: {
              //  戻るボタン
            backToToppage () {
                this.$router.push({
                    path: '/toppage'                  
                })
            },
           
            
            //セルの色変え処理
            tableCellClassName({row,column,rowIndex,columnIndex}) {
                    
                    for(let i=0;i<this.tableData.length;i++){
                        //今年のものを検索したとき
                        
                        if(this.kariYear-this.tableData[i].closeMonth.substr(0,4)==0){
                            for(let k=3;k<Number(this.tableData[i].closeMonth.substr(5,2))-Number(this.kariMonth)+4;k++){
                                if (rowIndex == i&&columnIndex==k) {
                                    
                                    return 'already-cell';
                                  
                                }
                            }                           
                        }  
                        
                        //去年のものを検索したとき
                        else if(this.kariYear-this.tableData[i].closeMonth.substr(0,4)==-1){
                            for(let k=3;k<Number(this.tableData[i].closeMonth.substr(5,2))-Number(this.kariMonth)+16;k++){
                                if(k>=14){
                                    if (rowIndex == i&&columnIndex==14){
                                        return 'already-cell';
                                    }
                                }
                                else{
                                    if (rowIndex == i&&columnIndex==k) {
                                        return 'already-cell';
                                    }
                                }
                            }     
                        }
                        //おととしよりも前で検索したなら灰色にする
                        else if(this.kariYear-this.tableData[i].closeMonth.substr(0,4)<-1){
                            for(let k=3;k<15;k++){                         
                               if (rowIndex == i&&columnIndex==k) {
                                    return 'already-cell';                                  
                                }
                            }
                        }

                        else{/*来年以降のものならば灰色にしない*/}  
                    }              
                     
            },
            
           

            //所属リスト取得
            getDepartmentKbnlist(){
                let data ={companyCode:this.companyCode}
                getDepartment(data).then(res => {
                    console.log(res)
                    this.departmentKbnList = res.data
                     let blockArray = { departmentCode: '', departmentName: ''}
                    this.departmentKbnList.unshift(blockArray)
                    
                }).catch(error => {
                })
            },
            
            
            //有休申請完了済みリストを取得してリターンする
            async getRequestCompleteHoliday(){
                this.kariYear=this.targetYear
                this.kariYear=this.kariYear.toString()
                this.kariYear=this.kariYear.substr(11,4)
                this.kariMonth=100+this.monthStatusIndex+1
                this.kariMonth=this.kariMonth.toString()
                this.kariMonth=this.kariMonth.substr(1,2)
             let data = {  
                 companyCode:this.companyCode , 
                 planYear:this.kariYear     
            } 
                 await RequestCompleteHoliday(data).then(res => {
                    console.log(res)
                   data=res.data
                }).catch(error => {
                })
                this.tableMonData=data
               
                return this.tableMonData
            },
            //済入力処理
            alreadyCloseMonth(){
                //今年のものを検索したとき                       
                if(this.kariYear-this.kariDataAll.closeMonth.substr(0,4)==0){
                    //数字と済の両方記載
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=1)this.kariDataAll.janCrt=this.kariDataAll.janCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=2)this.kariDataAll.febCrt=this.kariDataAll.febCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=3)this.kariDataAll.marCrt=this.kariDataAll.marCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=4)this.kariDataAll.aplCrt=this.kariDataAll.aplCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=5)this.kariDataAll.mayCrt=this.kariDataAll.mayCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=6)this.kariDataAll.junCrt=this.kariDataAll.junCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=7)this.kariDataAll.julCrt=this.kariDataAll.julCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=8)this.kariDataAll.augCrt=this.kariDataAll.augCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=9)this.kariDataAll.sepCrt=this.kariDataAll.sepCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=10)this.kariDataAll.octCrt=this.kariDataAll.octCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=11)this.kariDataAll.novCrt=this.kariDataAll.novCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=12)this.kariDataAll.decCrt=this.kariDataAll.decCrt+'済'
                    //数字か済のどちらかを記載
                    if(this.kariDataAll.closeMonth.substr(4,2)>=1&&this.kariDataAll.janCrt=='')this.kariDataAll.janCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=2&&this.kariDataAll.febCrt=='')this.kariDataAll.febCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=3&&this.kariDataAll.marCrt=='')this.kariDataAll.marCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=4&&this.kariDataAll.aplCrt=='')this.kariDataAll.aplCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=5&&this.kariDataAll.mayCrt=='')this.kariDataAll.mayCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=6&&this.kariDataAll.junCrt=='')this.kariDataAll.junCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=7&&this.kariDataAll.julCrt=='')this.kariDataAll.julCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=8&&this.kariDataAll.augCrt=='')this.kariDataAll.augCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=9&&this.kariDataAll.sepCrt=='')this.kariDataAll.sepCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=10&&this.kariDataAll.octCrt=='')this.kariDataAll.octCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=11&&this.kariDataAll.novCrt=='')this.kariDataAll.novCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=12&&this.kariDataAll.decCrt=='')this.kariDataAll.decCrt='済'                                  
                }
                //去年のものを検索したとき
                else if(this.kariYear-this.kariDataAll.closeMonth.substr(0,4)==-1){
                    //数字と済の両方記載
                    // this.kariDataAll.janCrt=this.kariDataAll.janCrt+'済'
                    // this.kariDataAll.febCrt=this.kariDataAll.febCrt+'済'
                    // this.kariDataAll.marCrt=this.kariDataAll.marCrt+'済'
                    // this.kariDataAll.aplCrt=this.kariDataAll.aplCrt+'済'
                    // this.kariDataAll.mayCrt=this.kariDataAll.mayCrt+'済'
                    // this.kariDataAll.junCrt=this.kariDataAll.junCrt+'済'
                    // this.kariDataAll.julCrt=this.kariDataAll.julCrt+'済'
                    // this.kariDataAll.augCrt=this.kariDataAll.augCrt+'済'
                    // this.kariDataAll.sepCrt=this.kariDataAll.sepCrt+'済'
                    // this.kariDataAll.octCrt=this.kariDataAll.octCrt+'済'
                    // this.kariDataAll.novCrt=this.kariDataAll.novCrt+'済'
                    // this.kariDataAll.decCrt=this.kariDataAll.decCrt+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=1)this.kariDataAll.janNext=this.kariDataAll.janNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=2)this.kariDataAll.febNext=this.kariDataAll.febNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=3)this.kariDataAll.marNext=this.kariDataAll.marNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=4)this.kariDataAll.aplNext=this.kariDataAll.aplNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=5)this.kariDataAll.mayNext=this.kariDataAll.mayNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=6)this.kariDataAll.junNext=this.kariDataAll.junNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=7)this.kariDataAll.julNext=this.kariDataAll.julNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=8)this.kariDataAll.augNext=this.kariDataAll.augNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=9)this.kariDataAll.sepNext=this.kariDataAll.sepNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=10)this.kariDataAll.octNext=this.kariDataAll.octNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=11)this.kariDataAll.novNext=this.kariDataAll.novNext+'済'
                    // if(this.kariDataAll.closeMonth.substr(4,2)>=12)this.kariDataAll.decNext=this.kariDataAll.decNext+'済'
                    //数字か済のどちらかを記載
                    if(this.kariDataAll.janCrt==null)this.kariDataAll.janCrt='済'
                    if(this.kariDataAll.febCrt==null)this.kariDataAll.febCrt='済'
                    if(this.kariDataAll.marCrt==null)this.kariDataAll.marCrt='済'
                    if(this.kariDataAll.aplCrt==null)this.kariDataAll.aplCrt='済'
                    if(this.kariDataAll.mayCrt==null)this.kariDataAll.mayCrt='済'
                    if(this.kariDataAll.junCrt==null)this.kariDataAll.junCrt='済'
                    if(this.kariDataAll.julCrt==null)this.kariDataAll.julCrt='済'
                    if(this.kariDataAll.augCrt==null)this.kariDataAll.augCrt='済'
                    if(this.kariDataAll.sepCrt==null)this.kariDataAll.sepCrt='済'
                    if(this.kariDataAll.octCrt==null)this.kariDataAll.octCrt='済'
                    if(this.kariDataAll.novCrt==null)this.kariDataAll.novCrt='済'
                    if(this.kariDataAll.decCrt==null)this.kariDataAll.decCrt='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=1&&this.kariDataAll.janNext=='')this.kariDataAll.janNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=2&&this.kariDataAll.febNext=='')this.kariDataAll.febNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=3&&this.kariDataAll.marNext=='')this.kariDataAll.marNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=4&&this.kariDataAll.aplNext=='')this.kariDataAll.aplNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=5&&this.kariDataAll.mayNext=='')this.kariDataAll.mayNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=6&&this.kariDataAll.junNext=='')this.kariDataAll.junNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=7&&this.kariDataAll.julNext=='')this.kariDataAll.julNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=8&&this.kariDataAll.augNext=='')this.kariDataAll.augNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=9&&this.kariDataAll.sepNext=='')this.kariDataAll.sepNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=10&&this.kariDataAll.octNext=='')this.kariDataAll.octNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=11&&this.kariDataAll.novNext=='')this.kariDataAll.novNext='済'
                    if(this.kariDataAll.closeMonth.substr(4,2)>=12&&this.kariDataAll.decNext=='')this.kariDataAll.decNext='済'   
                }
                //去年よりも前ものを検索したとき
                else if(this.kariYear-this.kariDataAll.closeMonth.substr(0,4)<-1){
                    //数字と済の両方記載
                    // this.kariDataAll.janCrt=this.kariDataAll.janCrt+'済'
                    // this.kariDataAll.febCrt=this.kariDataAll.febCrt+'済'
                    // this.kariDataAll.marCrt=this.kariDataAll.marCrt+'済'
                    // this.kariDataAll.aplCrt=this.kariDataAll.aplCrt+'済'
                    // this.kariDataAll.mayCrt=this.kariDataAll.mayCrt+'済'
                    // this.kariDataAll.junCrt=this.kariDataAll.junCrt+'済'
                    // this.kariDataAll.julCrt=this.kariDataAll.julCrt+'済'
                    // this.kariDataAll.augCrt=this.kariDataAll.augCrt+'済'
                    // this.kariDataAll.sepCrt=this.kariDataAll.sepCrt+'済'
                    // this.kariDataAll.octCrt=this.kariDataAll.octCrt+'済'
                    // this.kariDataAll.novCrt=this.kariDataAll.novCrt+'済'
                    // this.kariDataAll.decCrt=this.kariDataAll.decCrt+'済'
                    // this.kariDataAll.janNext=this.kariDataAll.janNext+'済'
                    // this.kariDataAll.febNext=this.kariDataAll.febNext+'済'
                    // this.kariDataAll.marNext=this.kariDataAll.marNext+'済'
                    // this.kariDataAll.aplNext=this.kariDataAll.aplNext+'済'
                    // this.kariDataAll.mayNext=this.kariDataAll.mayNext+'済'
                    // this.kariDataAll.junNext=this.kariDataAll.junNext+'済'
                    // this.kariDataAll.julNext=this.kariDataAll.julNext+'済'
                    // this.kariDataAll.augNext=this.kariDataAll.augNext+'済'
                    // this.kariDataAll.sepNext=this.kariDataAll.sepNext+'済'
                    // this.kariDataAll.octNext=this.kariDataAll.octNext+'済'
                    // this.kariDataAll.novNext=this.kariDataAll.novNext+'済'
                    // this.kariDataAll.decNext=this.kariDataAll.decNext+'済'
                    //数字か済のどちらかを記載
                    if(this.kariDataAll.janCrt=='')this.kariDataAll.janCrt='済'
                    if(this.kariDataAll.febCrt=='')this.kariDataAll.febCrt='済'
                    if(this.kariDataAll.marCrt=='')this.kariDataAll.marCrt='済'
                    if(this.kariDataAll.aplCrt=='')this.kariDataAll.aplCrt='済'
                    if(this.kariDataAll.mayCrt=='')this.kariDataAll.mayCrt='済'
                    if(this.kariDataAll.junCrt=='')this.kariDataAll.junCrt='済'
                    if(this.kariDataAll.julCrt=='')this.kariDataAll.julCrt='済'
                    if(this.kariDataAll.augCrt=='')this.kariDataAll.augCrt='済'
                    if(this.kariDataAll.sepCrt=='')this.kariDataAll.sepCrt='済'
                    if(this.kariDataAll.octCrt=='')this.kariDataAll.octCrt='済'
                    if(this.kariDataAll.novCrt=='')this.kariDataAll.novCrt='済'
                    if(this.kariDataAll.decCrt=='')this.kariDataAll.decCrt='済'
                    if(this.kariDataAll.janNext=='')this.kariDataAll.janNext='済'
                    if(this.kariDataAll.febNext=='')this.kariDataAll.febNext='済'
                    if(this.kariDataAll.marNext=='')this.kariDataAll.marNext='済'
                    if(this.kariDataAll.aplNext=='')this.kariDataAll.aplNext='済'
                    if(this.kariDataAll.mayNext=='')this.kariDataAll.mayNext='済'
                    if(this.kariDataAll.junNext=='')this.kariDataAll.junNext='済'
                    if(this.kariDataAll.julNext=='')this.kariDataAll.julNext='済'
                    if(this.kariDataAll.augNext=='')this.kariDataAll.augNext='済'
                    if(this.kariDataAll.sepNext=='')this.kariDataAll.sepNext='済'
                    if(this.kariDataAll.octNext=='')this.kariDataAll.octNext='済'
                    if(this.kariDataAll.novNext=='')this.kariDataAll.novNext='済'
                    if(this.kariDataAll.decNext=='')this.kariDataAll.decNext='済'   
                }
                //来年のものを検索したとき
                else{                   
                }
            },
            //データをまとめてテーブルに入れる処理
             async getHolidayAllList(){ 
                
                //検索データ履歴を削除
                this.tableAllData.splice(0) 
                //取得データ格納
                this.tableMonData=await this.getRequestCompleteHoliday()
                //要素操作
                 for(let i=0;i<this.tableMonData.length;i++){

                     this.kariDataMon=this.tableMonData[i]
                     this.kariDataAll=this.tableMonData[i]
                                         
                   

                    //値が０かNULLなら０にする
                    if(this.kariDataAll.paidHolidayRemaining==null||this.kariDataAll.paidHolidayRemaining==0)this.kariDataAll.paidHolidayRemaining='0.0'
　　　　　　　　　　　//値が０かNULLなら空白にする
                    if(this.kariDataAll.janCrt==null||this.kariDataAll.janCrt==0)this.kariDataAll.janCrt=''
                    if(this.kariDataAll.febCrt==null||this.kariDataAll.febCrt==0)this.kariDataAll.febCrt=''
                    if(this.kariDataAll.marCrt==null||this.kariDataAll.marCrt==0)this.kariDataAll.marCrt=''
                    if(this.kariDataAll.aplCrt==null||this.kariDataAll.aplCrt==0)this.kariDataAll.aplCrt=''
                    if(this.kariDataAll.mayCrt==null||this.kariDataAll.mayCrt==0)this.kariDataAll.mayCrt=''
                    if(this.kariDataAll.junCrt==null||this.kariDataAll.junCrt==0)this.kariDataAll.junCrt=''
                    if(this.kariDataAll.julCrt==null||this.kariDataAll.julCrt==0)this.kariDataAll.julCrt=''
                    if(this.kariDataAll.augCrt==null||this.kariDataAll.augCrt==0)this.kariDataAll.augCrt=''
                    if(this.kariDataAll.sepCrt==null||this.kariDataAll.sepCrt==0)this.kariDataAll.sepCrt=''
                    if(this.kariDataAll.octCrt==null||this.kariDataAll.octCrt==0)this.kariDataAll.octCrt=''
                    if(this.kariDataAll.novCrt==null||this.kariDataAll.novCrt==0)this.kariDataAll.novCrt=''
                    if(this.kariDataAll.decCrt==null||this.kariDataAll.decCrt==0)this.kariDataAll.decCrt=''
    ''
                    if(this.kariDataAll.janNext==null||this.kariDataAll.janNext==0)this.kariDataAll.janNext=''
                    if(this.kariDataAll.febNext==null||this.kariDataAll.febNext==0)this.kariDataAll.febNext=''
                    if(this.kariDataAll.marNext==null||this.kariDataAll.marNext==0)this.kariDataAll.marNext=''
                    if(this.kariDataAll.aplNext==null||this.kariDataAll.aplNext==0)this.kariDataAll.aplNext=''
                    if(this.kariDataAll.mayNext==null||this.kariDataAll.mayNext==0)this.kariDataAll.mayNext=''
                    if(this.kariDataAll.junNext==null||this.kariDataAll.junNext==0)this.kariDataAll.junNext=''
                    if(this.kariDataAll.julNext==null||this.kariDataAll.julNext==0)this.kariDataAll.julNext=''
                    if(this.kariDataAll.augNext==null||this.kariDataAll.augNext==0)this.kariDataAll.augNext=''
                    if(this.kariDataAll.sepNext==null||this.kariDataAll.sepNext==0)this.kariDataAll.sepNext=''
                    if(this.kariDataAll.octNext==null||this.kariDataAll.octNext==0)this.kariDataAll.octNext=''
                    if(this.kariDataAll.novNext==null||this.kariDataAll.novNext==0)this.kariDataAll.novNext=''
                    if(this.kariDataAll.decNext==null||this.kariDataAll.decNext==0)this.kariDataAll.decNext=''
                    
                    //済入力
                    this.alreadyCloseMonth()
                     //文字入れ
                    this.kariDataAll.closeMonth=this.kariDataAll.closeMonth.slice(0,4)+'年'+this.kariDataAll.closeMonth.slice(4)+'月'
                    this.kariDataAll.nextPaidFullDay=this.kariDataAll.nextPaidFullDay.slice(0,4)+'年'+this.kariDataAll.nextPaidFullDay.slice(4,6)+'月'+this.kariDataAll.nextPaidFullDay.slice(6)+'日'
                    this.kariDataAll.categoryKbnOption2=this.kariDataAll.categoryKbnOption2+'.0'   
                        
                        //去年のものを検索したとき
                        
                //     //名前が複数表示される場合一人のみを表示
                  if(i>=0&&i<this.tableMonData.length-1){                  
                   if(this.tableMonData[i].userId==this.tableMonData[i+1].userId&&this.tableMonData[i].departmentCode!=this.tableMonData[i+1].departmentCode&&this.departmentKbnStatus==''){
                             this.kariDataAll=null 
                           // console.log(1)
                    } 
                  }
                    
                    //全表示（所属が空白）か所属ごとの表示か
                    if(this.kariDataAll!=null){
                             if(this.kariDataAll.departmentCode==this.departmentKbnStatus&&this.departmentKbnStatus!='')this.tableAllData.push(this.kariDataAll)
                        else if(this.kariDataAll.departmentCode!=this.departmentKbnStatus&&this.departmentKbnStatus=='')this.tableAllData.push(this.kariDataAll)
                        else {}
                     }

                   }
                  
                //テーブルに値代入              
                this.tableData=this.tableAllData
                console.log(this.tableData)
            },
            //検索ボタン押下時の処理
            search(){
                //console.log(this.targetYear)   
                this.monthStatusIndex=Number(this.monthStatus)        
                this.getHolidayAllList() 
            },         
        }   
    }
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.holidayList-area {
    height: 880px;
    width: 100%;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 30px 30px;
    border-radius: 6px;

    .holidayList-header {
        padding: 0px 30px;
    }
}
.select-title {
    font-weight: bold;
    white-space: nowrap;
    font-size: 16px;
    margin-top: 8px;
}
.el-table .already-cell {
    background: rgb(189, 189, 189);
    
  }

</style>