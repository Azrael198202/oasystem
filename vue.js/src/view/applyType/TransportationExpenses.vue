<template>
    <div class="apply-area">
        <apply-header title="月末精算申請" v-bind:reqNumber="reqNumber"></apply-header>
        <div class="header">
            <div class='apply-month'>
            <el-date-picker
                v-model="applyMonth"
                value-format="yyyy-MM"
                type="month"
                placeholder=""
                format="yyyy年MM月"
                style="width: 140px"
                :disabled="selectedMonth">
            </el-date-picker>
            <span class="month">分申請</span>
            <el-button type="primary" size="small" class='input-btn' round @click="openDetailBox()" v-if="enterFlg">入力</el-button>
            <el-button class="reference-button" size="small" round @click="onReference()" v-if="refFlg">参照</el-button>
        </div>
            <div>
                <div class="pdf-button" v-loading='exportLoading' @click="exportPdf()" v-if="pdfFlg">PDF</div>
            </div>
        </div>   
        <div>
            <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick" class='main-tab'>
                <!-- 定期券リスト -->
                <el-tab-pane label="定期券" name="teiki">
                    <el-table
                        class='main-table'
                        size="mini"
                        :data="teikiData"
                        :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600}"
                        height="400px">
                        <el-table-column prop="index" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.index = scope.$index + 1 }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="requestNumber" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{scope.row.requestNumber }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="term" label="適用期間" width="180">
                        </el-table-column>
                        <el-table-column prop="startDate" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.startDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="endDate" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.endDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="days" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.days }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="institution" label="経費区分" width="99">
                        </el-table-column>
                        <el-table-column prop="month" label="月区分" width="80">
                        </el-table-column>
                        <el-table-column prop="monthKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.monthKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="applyKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.applyKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.travelKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="station" label="乗車区間" width="199">
                        </el-table-column>
                        <el-table-column prop="departure" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.departure }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="arrival" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.arrival }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelWays" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.travelWays }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="wayKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.wayKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="destination" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.destination }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="amount" label="金額" width="80">
                            <template slot-scope="scope">
                                <span>{{ scope.row.amount.toLocaleString() }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="teikiAmount" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.teikiAmount }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="company" label="請求区分" width="90">
                        </el-table-column>
                        <el-table-column prop="recipientKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.recipientKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="remark" label="備考" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" @click="getRemark(scope.row)" :disabled="listBtnC"><i class="el-icon-more"></i></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="image" label="添付" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" @click="getImage(scope.row)" :disabled="listBtnC"><i class="el-icon-more"></i></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="edit" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" icon="el-icon-edit" @click="editDetail(scope.row)" :disabled="listBtn"></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="delete" width="60">
                            <template slot-scope="scope">
                                <el-button circle size="mini" icon="el-icon-delete-solid" @click="deleteOne(scope.row)" :disabled="listBtn"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <span class="total-amount">
                        <span>合計&nbsp;&#058;&nbsp;</span>{{ this.totalTeikiAmount.toLocaleString() }}
                    </span>
                    <div class="add-btn teiki-add-btn" @click="addBtn()" v-if="teikiAddFlg">追加</div>
                </el-tab-pane>
                <el-tab-pane label="旅費" name="travel">
                    <!-- 旅費リスト -->
                    <el-table
                        size="mini"
                        :data="travelData"
                        :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600}"
                        class='main-table'
                        height="400px">
                        <el-table-column prop="index" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.index = scope.$index + 1 }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="requestNumber" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{scope.row.requestNumber }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="term" label="日付/期間" width="170">
                        </el-table-column>
                        <el-table-column prop="startDate" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.startDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="endDate" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.endDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="days" label="日数" width="50">
                        </el-table-column>
                        <el-table-column prop="institution" label="経費区分" width="80">
                        </el-table-column>
                        <el-table-column prop="month" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.month }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="monthKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.monthKbn }}</span>
                            </template>
                            <!-- style="display: none" -->
                        </el-table-column>
                        <el-table-column prop="applyKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.applyKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.travelKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="station" label="乗車区間/宿泊・訪問先" width="191">
                        </el-table-column>
                        <el-table-column prop="departure" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.departure }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="arrival" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.arrival }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelWays" label="往復区分" width="80">
                        </el-table-column>
                        <el-table-column prop="wayKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.wayKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="destination" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.destination }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="amount" label="金額" width="80">
                            <template slot-scope="scope">
                                <span>{{ scope.row.amount.toLocaleString() }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelAmount" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.travelAmount }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="company" label="請求区分" width="80">
                        </el-table-column>
                        <el-table-column prop="recipientKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.recipientKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="remark" label="備考" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" @click="getRemark(scope.row)"><i class="el-icon-more" :disabled="listBtnC"></i></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="image" label="添付" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" @click="getImage(scope.row)" :disabled="listBtnC"><i class="el-icon-more"></i></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="TravelEdit" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" icon="el-icon-edit" @click="editDetail(scope.row)" :disabled="listBtn"></el-button>
                            </template >
                        </el-table-column>
                        <el-table-column prop="TravelDelete" width="58">
                            <template slot-scope="scope">
                                <el-button circle size="mini" icon="el-icon-delete-solid" @click="deleteOne(scope.row)" :disabled="listBtn"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <span class="total-amount">
                        <span>合計&nbsp;&#058;&nbsp;</span>{{ this.totaltravelAmount.toLocaleString() }}
                    </span>
                    <div class="add-btn travel-add-btn" @click="TravelAddBtn()" v-if="travelAddFlg">追加</div>
                </el-tab-pane>
                <el-tab-pane label="その他経費" name="other">
                    <!-- その他経費リスト -->
                    <el-table
                        size="mini"
                        :data="otherData"
                        :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600}"
                        class='main-table'
                        height="400px">
                        <el-table-column prop="index" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.index = scope.$index + 1 }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="requestNumber" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{scope.row.requestNumber }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="term" label="日付/期間" width="170">
                        </el-table-column>
                        <el-table-column prop="startDate" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.startDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="endDate" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.endDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="days" label="日数" width="50">
                        </el-table-column>
                        <el-table-column prop="institution" label="経費区分" width="80">
                        </el-table-column>
                        <el-table-column prop="month" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.month }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="monthKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.monthKbn }}</span>
                            </template>
                            style="display: none"
                        </el-table-column>
                        <el-table-column prop="applyKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.applyKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.travelKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="station" label="内容" width="270">
                        </el-table-column>
                        <el-table-column prop="departure" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.departure }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="arrival" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.arrival }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelWays" width="1">
                        </el-table-column>
                        <el-table-column prop="wayKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.wayKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="destination" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.destination }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="amount" label="金額" width="80">
                            <template slot-scope="scope">
                                <span>{{ scope.row.amount.toLocaleString() }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="travelAmount" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.travelAmount }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="company" label="請求区分" width="80">
                        </el-table-column>
                        <el-table-column prop="recipientKbn" width="1">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.recipientKbn }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="remark" label="備考" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" @click="getRemark(scope.row)"><i class="el-icon-more" :disabled="listBtnC"></i></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="image" label="添付" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" @click="getImage(scope.row)" :disabled="listBtnC"><i class="el-icon-more"></i></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="TravelEdit" width="55">
                            <template slot-scope="scope">
                                <el-button circle size="mini" icon="el-icon-edit" @click="editDetail(scope.row)" :disabled="listBtn"></el-button>
                            </template >
                        </el-table-column>
                        <el-table-column prop="TravelDelete" width="58">
                            <template slot-scope="scope">
                                <el-button circle size="mini" icon="el-icon-delete-solid" @click="deleteOne(scope.row)" :disabled="listBtn"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <span class="total-amount">
                        <span>合計&nbsp;&#058;&nbsp;</span>{{ this.totalOtherAmount.toLocaleString() }}
                    </span>
                    <div class="add-btn travel-add-btn" @click="OtherAddBtn()" v-if="otherAddFlg">追加</div>
                </el-tab-pane>
            </el-tabs>
            <div class="all-btn">
                <el-row>
                <div class="btn-line">
                    <div>
                        <el-col class="flex-start">
                        <div class="back-btn common-btn" @click="setNew()" v-if="upFlg">上へ</div>
                        <div class="gray-btn common-btn" @click="onDeleteConfirmClick()" v-if="delFlg">削除</div>
                        <div class="itirann-btn" @click="closeA()" v-if="changebyFlg == '2'">申請一覧に戻る</div>
                        <div class="itirann-btn" @click="closeB()" v-if="changebyFlg == '3'">承認一覧に戻る</div>
                    </el-col>
                    </div>
                    <div>
                        <el-col class="flex-end">
                        <div class="green-btn"  @click="saveBtn()" v-if="modifyFlgsave" style='margin-right: 30px'>保存</div>
                        <div class="orange-btn" @click="applyDialog(0)" v-if="reqFlg" style='margin-right: 15px'>申請</div>
                        <div class="blue-btn" @click="OpenGetBackDialog()" v-if="getBack" style='margin-right: 15px'>取戻</div>
                        <div class="red-btn" @click="remandDialog()" v-if="changebyFlgwf" style='margin-right: 30px'>差戻</div>
                        <div class="green-btn" @click="applyDialog(1)" v-if="changebyFlgapp" style='margin-right: 15px'>承認</div>
                    </el-col>
                    </div>
                </div>                     
                </el-row>
            </div>
        </div>
        <!-- リスト内備考ダ イアログ -->
        <el-dialog
            title="備考"
            :visible.sync="dialogRemark"
            width="30%">
            <span>{{this.allRemarks}}</span>
        </el-dialog>
         <!-- 定期リスト内画像ダイアログ -->
         <el-dialog :visible.sync='listPictureDialog'>
         <div v-if="allPics==''">
            <span>画像が存在しません。</span>
        </div>
        <div v-if="allPics!==''">
            <img width='100%'  :src='allPics'  alt="画像が存在しません。">
        </div>    
        </el-dialog>
        <!-- 交通費リスト内画像ダイアログ -->
         <el-dialog :visible.sync='listPictureDialogT'>
         <div v-if="allPicsT==''">
            <span>画像が存在しません。</span>
        </div >
        <div v-if="allPicsT!==''">
            <img width='100%'  :src='allPicsT'  alt="画像が存在しません。">
        </div>   
        </el-dialog>
        <!-- その他経費リスト内画像ダイアログ -->
         <el-dialog :visible.sync='listPictureDialogO'>
         <div v-if="allPicsO==''">
            <span>画像が存在しません。</span>
        </div >
        <div v-if="allPicsO!==''">
            <img width='100%'  :src='allPicsO'  alt="画像が存在しません。">
        </div>   
        </el-dialog>
        <!-- 定期券入力ボックス -->
        <el-dialog title="定期券入力"
            class="pc"
            :visible.sync="teikiInputDialog"
            width="800px"
            @close="teikiClose()"
            @open="teikiOpen()"
            center>
            <el-form  :inline="true" ref="formTeiki" :rules="rules" :model="formTeiki" class='input-form' size="small">
                <el-form-item label="経費区分" prop="teikiKbn" label-width="90px">
                    <div class='travel-kbn'>
                        <el-select v-model="formTeiki.teikiKbn" placeholder="" :disabled="teikiKbnOff">
                        <el-option
                            v-for="item in teikiKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                        </el-select>
                    </div>
                </el-form-item>
                <div>
                    <el-form-item label-width="90px" prop="chosenDays" label="適用期間">
                        <el-date-picker
                            @blur="countDays()"
                            @change="checkDays()"
                            v-model="formTeiki.chosenDays"
                            type="daterange"
                            range-separator="～"
                            start-placeholder="YYYY/MM/DD"
                            end-placeholder="YYYY/MM/DD"
                            value-format="yyyy-MM-dd"
                            format="yyyy/MM/dd"
                            :default-value= "applyMonth"
                            key='1'
                            style="width: 250px"
                            :disabled="inputOff">
                        </el-date-picker>
                    <!-- {{formTeiki.chosenDays}}
                    {{this.dayCount}} -->
                    </el-form-item>                  
                    <el-form-item label-width="80px" prop="month" label="月区分">
                        <el-select v-model="formTeiki.month" placeholder="" class='month-kbn' :disabled="inputOff" @change="checkDays()">
                        <el-option label="1ヶ月" value="1"></el-option>
                        <el-option label="2ヶ月" value="2"></el-option>
                        <el-option label="3ヶ月" value="3"></el-option>
                        <el-option label="4ヶ月" value="4"></el-option>
                        <el-option label="5ヶ月" value="5"></el-option>
                        <el-option label="6ヶ月" value="6"></el-option>
                        </el-select>
                    </el-form-item>
                    <!-- <span>{{formInline.month}}</span> -->
                    <div>
                        <el-form-item  prop="input1" label-width="90px" label="出発地">
                            <div class='travel-kbn'>
                                <el-input  placeholder="" v-model="formTeiki.input1" maxlength="50" :disabled="inputOff"></el-input>
                            </div>
                        </el-form-item>
                        <el-form-item prop="input2" label-width="130px" label="到着地">
                            <div class='travel-kbn'>
                                <el-input placeholder="" v-model="formTeiki.input2" maxlength="50" :disabled="inputOff"></el-input>
                            </div>
                        </el-form-item>
                    </div>
                </div>
                <div>
                    <el-form-item prop="amount" label-width="90px" label="金額">
                        <div class='travel-kbn'>
                            <el-input placeholder="" v-model.number="formTeiki.amount" maxlength="8" :disabled="inputOff"></el-input>
                        </div>
                    </el-form-item>
                    <el-form-item label-width="130px" prop="company" label="請求区分">
                        <el-select v-model="formTeiki.company" placeholder="" class='company-kbn' :disabled="inputOff">
                        <el-option
                            v-for="item in reimbursementKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                        </el-select>
                    </el-form-item>
                </div>
                <div>
                    <el-form-item label-width="90px" label="備考">
                        <div class="remark-box">
                            <el-input
                            type="textarea"
                            :rows="7"
                            placeholder=""
                            v-model="formTeiki.textarea"
                            maxlength="255"
                            :disabled="inputOff">
                        </el-input>
                        </div>
                    </el-form-item>
                    <el-form-item label-width="80px" label="添付">
                        <div class='image-upload'>
                            <el-upload
                                :class="{disabled:uploadDisabled}"
                                class='appended-uploader'
                                action='#'
                                list-type='picture-card'
                                :file-list="fileList"
                                :auto-upload='false'
                                :on-change='imageLoading'
                                :limit='1'
                                :disabled="inputOff">
                                <i slot='default' class='el-icon-plus appended-uploader-icon'></i>
                                <div slot="file" slot-scope="{file}">
                                    <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                                    <span class="el-upload-list__item-actions image-upload">
                                        <span
                                            class="el-upload-list__item-preview"
                                            @click="handlePictureCardPreview(file)">
                                            <i class="el-icon-zoom-in"></i>
                                        </span>
                                        <span
                                            v-if="!disabled"
                                            class="el-upload-list__item-delete"
                                            @click="removeFunction(file)">
                                            <i class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </el-upload>
                        </div>
                    </el-form-item>
                </div>
                <!-- 定期券（追加・編集・取消） -->
                <div>
                    <el-form-item label="">
                        <div class="finalize-btn" @click="addList()" v-if="finalizeFlg">確定</div>
                        <div class="finalize-btn" @click="editBtn()" v-if="EditFinalizeFlg">確定</div>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>
        <!-- 旅費入力ボックス -->
        <el-dialog title="旅費入力"
            :visible.sync="travelInputDialog"
            width="800px"
            @close="travelClose()"
            @open="travelOpen()"
            center>
            <!-- 交通費、宿泊、日当（経費区分） -->
            <el-form :inline="true" ref="formTravel" :rules="rules2" :model="formTravel" class='input-form' size="small">
                <el-form-item label="経費区分" prop="travelKbn" label-width="90px">
                    <div class='travel-kbn'>
                        <el-select v-model="formTravel.travelKbn" placeholder="" :disabled="travelKbnOff">
                        <el-option
                            v-for="item in travelKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                        </el-select>
                    </div>
                </el-form-item>
                <!-- 交通費（日付/期間） -->
                <el-form-item label-width="90px" prop="term" label="日付/期間">
                    <el-date-picker
                        @blur="countTravelDays()"
                        @change="onClear()"
                        v-model="formTravel.term"
                        type="daterange"
                        range-separator="～"
                        start-placeholder="YYYY/MM/DD"
                        end-placeholder="YYYY/MM/DD"
                        value-format="yyyy-MM-dd"
                        format="yyyy/MM/dd"
                        :default-value= "applyMonth"
                        key='1'
                        style='width: 250px'
                        :disabled="inputOff">
                    </el-date-picker>
                    <!-- {{formTravel.term}} -->
                    <span class="cal-days">
                        <span>日数&nbsp;&#058;&nbsp;</span>{{this.travelCountDays}}<span>&nbsp;日</span>
                    </span>
                </el-form-item>
                <div>
                    <!-- 宿泊/日当地） -->
                    <el-form-item label-width="90px" label="宿泊/訪問" prop="destination" error="" v-if="formTravel.travelKbn == '030' || formTravel.travelKbn == '040'">
                        <div class='travel-kbn'>
                            <el-input placeholder="" v-model="formTravel.destination" maxlength="50" :disabled="inputOff"></el-input>
                        </div>
                    </el-form-item>
                    <!-- 交通費（出発・到着地） -->
                    <el-form-item label-width="90px" label="出発地" prop="start" v-if="formTravel.travelKbn != '030' && formTravel.travelKbn != '040'">
                        <div class='travel-kbn'>
                            <el-input placeholder="" v-model="formTravel.start" maxlength="50" class='travel-kbn' :disabled="inputOff"></el-input>
                        </div>
                    </el-form-item>
                    <el-form-item label-width="100px" label="到着地" prop="end" v-if="formTravel.travelKbn != '030' && formTravel.travelKbn != '040'">
                        <div class='travel-kbn'>
                            <el-input placeholder="" :disabled="inputOff" v-model="formTravel.end" maxlength="50" class='travel-kbn' v-if="formTravel.travelKbn != '030' && formTravel.travelKbn != '040'"></el-input>
                        </div>
                    </el-form-item>
                    <el-form-item label="">
                    <!-- 交通費（往復・片道） -->
                    <el-radio-group v-model="formTravel.ways" :disabled="inputOff" v-if="formTravel.travelKbn != '030' && formTravel.travelKbn != '040'">
                        <el-radio-button
                            v-for="(item , index) in wayList"
                            :key="index"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-radio-button>
                    </el-radio-group>
                    </el-form-item>
                </div>
                <div>
                    <!-- 交通費・宿泊・日当（金額） -->
                    <el-form-item label="金額" label-width="90px" prop="amount">
                        <div class='travel-kbn'>
                            <el-input placeholder="" v-model.number="formTravel.amount" maxlength="8" :disabled="inputOff"></el-input>
                        </div>
                    </el-form-item>
                    <!-- 交通費・宿泊・日当（請求区分） -->
                    <el-form-item label-width="100px" label="請求区分" prop="company">
                        <el-select v-model="formTravel.company" placeholder="" class='company-kbn' :disabled="inputOff">
                        <el-option
                            v-for="item in reimbursementKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                        </el-select>
                    </el-form-item>
                </div>
                <div>
                    <!-- 交通費・宿泊・日当（備考） -->
                    <el-form-item  label-width="90px" label="備考">
                        <div class="remark-box">
                            <el-input
                                type="textarea"
                                :rows="7"
                                placeholder=""
                                v-model="formTravel.textarea"
                                maxlength="255"
                                :disabled="inputOff">
                            </el-input>
                        </div>
                    </el-form-item>
                    <!-- 交通費・宿泊・日当（画像） -->
                    <el-form-item label="添付" class="image-label travel-img">
                        <div class='image-upload'>
                            <el-upload
                                :class="{disabled:uploadDisabled}"
                                class='appended-uploader'
                                action='#'
                                list-type='picture-card'
                                :file-list="fileList"
                                :auto-upload='false'
                                :on-change='imageLoading'
                                :limit='1'
                                :disabled="inputOff">
                                <i slot='default' class='el-icon-plus appended-uploader-icon'></i>
                                <div slot="file" slot-scope="{file}">
                                    <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                                    <span class="el-upload-list__item-actions image-upload">
                                        <span
                                            class="el-upload-list__item-preview"
                                            @click="handlePictureCardPreview(file)">
                                            <i class="el-icon-zoom-in"></i>
                                        </span>
                                        <span
                                            v-if="!disabled"
                                            class="el-upload-list__item-delete"
                                            @click="removeFunction(file)">
                                            <i class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </el-upload>
                        </div>
                    </el-form-item>
                    <!-- 交通費・宿泊・日当（追加・編集・取消） -->
                    <el-form-item label="">
                        <div class="finalize-btn" @click="addList()" v-if="finalizeFlg">確定</div>
                        <div class="finalize-btn" @click="editBtn()" v-if="EditFinalizeFlg">確定</div>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>
        <!-- その他経費入力ボックス -->
        <el-dialog title="その他経費入力"
            :visible.sync="otherInputDialog"
            width="800px"
            @close="otherClose()"
            @open="otherOpen()"
            center>
            <!-- その他経費（経費区分） -->
            <!-- 追加押下時に区分がとれない -->
            <el-form :inline="true" ref="formOther" :rules="rules3" :model="formOther" class='input-form' size="small">
                <el-form-item label="経費区分" prop="travelKbn" label-width="90px">
                    <div class='travel-kbn'>
                        <el-select v-model="formOther.travelKbn" placeholder="" :disabled="true" >
                        <el-option
                            v-for="item in otherKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                        </el-select>
                    </div>
                </el-form-item>
                <!-- 購入日（日付/期間） -->
                <el-form-item label-width="90px" prop="term" label="日付/期間">
                    <el-date-picker
                        @blur="countOtherDays()"
                        @change="onOtherClear()"
                        v-model="formOther.term"
                        type="daterange"
                        range-separator="～"
                        start-placeholder="YYYY/MM/DD"
                        end-placeholder="YYYY/MM/DD"
                        value-format="yyyy-MM-dd"
                        format="yyyy/MM/dd"
                        :default-value= "applyMonth"
                        key='1'
                        style='width: 250px'
                        :disabled="inputOff">
                    </el-date-picker>
                    <!-- {{formTravel.term}} -->
                    <span class="cal-days">
                        <span>日数&nbsp;&#058;&nbsp;</span>{{this.otherCountDays}}<span>&nbsp;日</span>
                    </span>
                </el-form-item>
                <div>
                    <!-- 購入内容 -->
                    <el-form-item label-width="90px" label="内容" prop="destination" error="">
                        <div class='travel-kbn'>
                            <el-input placeholder="" v-model="formOther.destination" maxlength="50" :disabled="inputOff"></el-input>
                        </div>
                    </el-form-item>
                </div>
                <div>
                    <!-- 購入金額 -->
                    <el-form-item label="金額" label-width="90px" prop="amount">
                        <div class='travel-kbn'>
                            <el-input placeholder="" v-model.number="formOther.amount" maxlength="8" :disabled="inputOff"></el-input>
                        </div>
                    </el-form-item>
                    <!-- その他経費（請求区分） -->
                    <el-form-item label-width="100px" label="請求区分" prop="company">
                        <el-select v-model="formOther.company" placeholder="" class='company-kbn' :disabled="inputOff">
                        <el-option
                            v-for="item in reimbursementKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                        </el-select>
                    </el-form-item>
                </div>
                <div>
                    <!-- その他経費（備考） -->
                    <el-form-item  label-width="90px" label="備考">
                        <div class="remark-box">
                            <el-input
                                type="textarea"
                                :rows="7"
                                placeholder=""
                                v-model="formOther.textarea"
                                maxlength="255"
                                :disabled="inputOff">
                            </el-input>
                        </div>
                    </el-form-item>
                    <!-- その他経費（画像） -->
                    <el-form-item label="添付" class="image-label travel-img">
                        <div class='image-upload'>
                            <el-upload
                                :class="{disabled:uploadDisabled}"
                                class='appended-uploader'
                                action='#'
                                list-type='picture-card'
                                :file-list="fileList"
                                :auto-upload='false'
                                :on-change='imageLoading'
                                :limit='1'
                                :disabled="inputOff">
                                <i slot='default' class='el-icon-plus appended-uploader-icon'></i>
                                <div slot="file" slot-scope="{file}">
                                    <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                                    <span class="el-upload-list__item-actions image-upload">
                                        <span
                                            class="el-upload-list__item-preview"
                                            @click="handlePictureCardPreview(file)">
                                            <i class="el-icon-zoom-in"></i>
                                        </span>
                                        <span
                                            v-if="!disabled"
                                            class="el-upload-list__item-delete"
                                            @click="removeFunction(file)">
                                            <i class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </el-upload>
                        </div>
                    </el-form-item>
                    <!-- その他経費（追加・編集・取消） -->
                    <el-form-item label="">
                        <div class="finalize-btn" @click="addList()" v-if="finalizeFlg">確定</div>
                        <div class="finalize-btn" @click="editBtn()" v-if="EditFinalizeFlg">確定</div>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>
        <!-- 削除のポップアップ -->
        <el-dialog title="申請を削除しますか。"
            :visible.sync="deleteConfirmDialogVisible"
            width="300px"
            center>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="deleteApplication()" style="width: 80px">はい</el-button>
                <el-button @click="deleteConfirmDialogVisible = false" style="width: 80px">いいえ</el-button>
            </div>
        </el-dialog>
        <!-- 参照ダイアログ -->
        <el-dialog title=""
            :visible.sync="referenceDialogVisible"
            width="300px"
            center>
            <div>
                参照する年月を選択してください。
            </div>
            <div slot="footer" class="dialog-footer">
                <el-date-picker
                    v-model="referenceMonth"
                    value-format="yyyy-MM"
                    type="month"
                    placeholder=""
                    format="yyyy年MM月"
                    style="width: 140px; margin-right: 25px">
                </el-date-picker>
                <el-button type="primary" @click="searchRefMonth()" style="width: 80px">確定</el-button>
            </div>
        </el-dialog>
        <approval v-bind:userData="userData" :requestData="requestData" ref="approvalDialog" @dataRegistration='approvalSave'></approval>
        <remand v-bind:dateList="detailList" ref="onRemandDialog"></remand>
        <getBackDoalog ref="ongetBackDoalog"></getBackDoalog>
        <!-- 定期入力項目内画像プレビューダイアログ -->
        <el-dialog :visible.sync="picDialogVisible">
            <img width="100%" :src="pic64Data" alt="Bear">
        </el-dialog>
        <!-- 交通費入力項目内画像プレビューダイアログ -->
        <el-dialog :visible.sync="picDialogVisibleT">
            <img width="100%" :src="pic64DataT" alt="Bear">
        </el-dialog>
        <!-- その他経費入力項目内画像プレビューダイアログ -->
        <el-dialog :visible.sync="picDialogVisibleO">
            <img width="100%" :src="pic64DataO" alt="Bear">
        </el-dialog>
    </div>
</template>

<script>
    import applyHeader from '../component/applyHeader.vue';
    import { getCategory } from '@/api/category';
    import { Message } from 'element-ui';
    import approval from "../Approval.vue";
    import remand from "../Remand.vue";
    import getBackDoalog from "../GetBackDialog.vue";
    import { insertTransportationExpenses, getTransportationExpenses, deleteTransportationExpenses, updateTransportationExpenses, searchRefMonthData, selectMonth, exportToPDF, saveImgTemporary } from '@/api/transportationExpenses';
    import { insertLeaveRequest, deleteRequest} from '@/api/requestList'
    import { deleteAllWf } from '@/api/wfList'

    export default {
        props: ['userData'],
        name: "TransportationExpenses",
        components: {
            applyHeader,
            approval,
            remand,
            getBackDoalog
        },
        created() {
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            this.getCurrentTime()
            this.setReqNum()
            this.allDelete()

            let userDataInfo = { companyCode: this.userData.companyCode
                                ,requestUserId: this.userData.userId
                            }
            selectMonth(userDataInfo).then(res => {
                this.getData = res.data
                console.log('this.getData', this.getData)
                if(this.getData) {
                    // 過去申請の最大年月+1ヶ月
                    let maxYmString = this.getData.applyYm
                    let maxYm =maxYmString.substring(0,4) + '/' + maxYmString.substring(4,6) + '/01'
                    let maxYmd = new Date(maxYm)
                    let plusOneMonth = maxYmd.setMonth(maxYmd.getMonth() + 1)
                    console.log('plusOneMonth', plusOneMonth)
                    let setYm = new Date(plusOneMonth)
                    let year = setYm.getFullYear()
                    let month = setYm.getMonth()+1
                     console.log('month', month)
                    let getMonth = String(year) + String(month)
                    console.log('applyMonth.length',  getMonth.length)
                    if(getMonth.length == 5) {
                       this.applyMonth = getMonth.substring(0,4) + '-0' + getMonth.substring(4,5)
                    }else{
                       this.applyMonth = getMonth.substring(0,4) + '-' + getMonth.substring(4,6) 
                    }
                    console.log('this.applyMonth',  this.applyMonth)
                }else{
                    // 過去申請が無ければログイン日の年月
                    this.applyMonth = this.yM
                }
            }).catch(error => {
                console.log(error)
            })

            // 旅費申請区分
            let travelApplyData = { companyCode: this.userData.companyCode
                                  , categoryClassNumber: '050'
                                  }
            getCategory(travelApplyData).then(res => {
                console.log(res)
                this.travelApplyList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 経費区分（定期）
            let teikiKbnData = { companyCode: this.userData.companyCode
                               , categoryClassNumber: '051'
                               }
            getCategory(teikiKbnData).then(res => {
                console.log(res)
                this.teikiKbnList = res.data.filter((e=>{return e.categoryKbn!='020' && e.categoryKbn!='021' && e.categoryKbn!='022' && e.categoryKbn!='030' && e.categoryKbn!='040' && e.categoryKbn!='050'}))
            console.log('teikiKbnList', this.teikiKbnList)
                if(this.changebyFlg=="2"||this.changebyFlg=="3"){
                    // 詳細ボタン押下時
                    this.setDetailData()
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 経費区分（旅費）
            let travelKbnData = { companyCode: this.userData.companyCode
                                , categoryClassNumber: '051'
                                }
            getCategory(travelKbnData).then(res => {
                console.log(res)
                this.travelKbnList = res.data.filter((e=>{return e.categoryKbn!='050'}))
            console.log('travelKbnList', this.travelKbnList)
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })

            this.getOthers()

            // 精算区分
            let reimbursementKbnData = { companyCode: this.userData.companyCode
                                       , categoryClassNumber: '052'
                                       }
            getCategory(reimbursementKbnData).then(res => {
                console.log(res)
                this.reimbursementKbnList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 往復区分（旅費）
            let wayData = { companyCode: this.userData.companyCode
                          , categoryClassNumber: '053'
                          }
            getCategory(wayData).then(res => {
                console.log(res)
                this.wayList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            this.statusSetBtn()
        },

        data() {
            return {
                startDate: '',
                endDate: '',
                startD: '',
                endD:'',
                pdfFlg: false,
                exportLoading: '',
                // 過去申請データ
                getData: '',
                // 過去申請の最大年月
                maxYm: '',
                // ログイン日の年月
                yM: '',
                teikiCount: '',
                travelCount: '',
                requestOverview: '',
                // 編集ボタン押下後入力項目内
                editTerm: '',
                // addList用変数
                addStation: '',
                addDeparture: '',
                addArrival: '',
                addWayKbn: '',
                addTravelWays: '',
                addDestination: '',
                // リスト内編集用変数
                editStation: '',
                editStations: '',
                editDestination: '',
                // 定期リスト
                teikiData: [],
                // 交通費リスト
                travelData: [],
                // その他経費リスト
                otherData: [],
                // 宿泊リスト
                stayData: [],
                // 宿泊リスト
                workData: [],
                // リスト内定期券金額
                teikiAmount: '',
                // リスト内定交通費金額
                travelAmount: '',
                // リスト内定宿泊費金額
                stayAmount: '',
                // リスト内定日当費金額
                workAmount:'',
                // 定期券合計金額
                totalTeikiAmount: '',
                // 交通費合計金額
                totaltravelAmount: '',
                // その他経費合計金額
                totalOtherAmount: '',
                // リスト内編集用定期券適用期間
                editTeikiTerm: '',
                // リスト内編集用定期券適用開始日
                editTeikiStartDate: '',
                // リスト内編集用定期券適用終了日
                editTeikiEndDate: '',
                // 入力ボタン
                enterFlg: true,
                // 参照ボタン
                refFlg: false,
                // 参照ダイアログ
                referenceDialogVisible: false,
                // 参照年月
                referenceMonth: '',
                // 参照データリスト
                refDataList: '',
                // 画面フラグ（1:申請、2:詳細、3：承認）
                changebyFlg: '',
                // 再保存時の枝番
                subNum: 0,
                // 上へボタン
                upFlg: false,
                // 削除ボタン
                delFlg: true,
                // 削除ダイアログ
                deleteConfirmDialogVisible: false,
                // 保存ボタン
                modifyFlgsave: false,
                // 申請テーブルに格納するデータ
                requestData: [],
                // 申請ボタン
                reqFlg: false,
                // 取戻
                getBack: false,
                // opende: false,
                // 承認
                changebyFlgapp: true,
                // 差戻
                changebyFlgwf: true,
                // 現在の年月日と時間
                aTime: '',
                // 申請番号
                reqNum: '',
                // 定期セッションストレージ編集用
                teikiEditData: '',
                // 宿泊セッションストレージ編集用
                stayEditData: '',
                // 日当セッションストレージ編集用
                workEditData: '',
                // 交通費セッションストレージ編集用
                transportationEditData: '',
                // その他経費セッションストレージ編集用
                otherEditData: '',
                // 編集後の定期データ
                teikiRewriteData: '',
                // 編集後の宿泊データ
                stayRewriteData: '',
                // 編集後の日当データ
                workRewriteData: '',
                // 定期券入力ダイアログ
                teikiInputDialog: false,
                // 旅費入力ダイアログ
                travelInputDialog: false,
                // その他経費入力ダイアログ
                otherInputDialog: false,
                // 旅費入力追加ボタン
                travelAddFlg: false,
                // その他経費入力追加ボタン
                otherAddFlg: false,
                // 定期券追加ボタン
                teikiAddFlg: false,
                activeName: 'teiki',
                // 申請年月
                applyMonth: '',
                plusApplyMonth:'',
                // 定期日数
                dayCount: '',
                // 旅費日数
                travelCountDays: '',
                // その他経費日数
                otherCountDays: '',
                // 宿泊日数
                // stayCountDays: '',
                // 日当日数
                // workCountDays: '',
                // 追加確定ボタン
                finalizeFlg: false,
                // 編集確定ボタン
                EditFinalizeFlg: false,
                // 申請月入力欄
                selectedMonth: false,
                // 入力項目活性・非活性
                inputOff: true,
                // リスト内ボタン活性・非活性
                listBtn: true,
                // リスト内備考添付ボタン活性・非活性
                listBtnC: true,
                // 経費区分（定期）活性・非活性
                teikiKbnOff: false,
                // 経費区分（旅費）活性・非活性
                travelKbnOff: true,
                // 経費区分（定期）
                teikiKbnList: {index: '',categoryKbnName: '' },
                // 経費区分（旅費）
                travelKbnList: {index: '',categoryKbnName: '' },
                // 経費区分（その他経費）
                otherKbnList: {index: '',categoryKbnName: '' },
                // 請求区分
                reimbursementKbnList: {index: '',categoryKbnName: '' },
                // 往復区分
                wayList: {index: '',categoryKbnName: '' },
                // 申請状態区分
                reqStatus: '',
                // pictureCardDialog: false,
                // リスト内定期券添付画像プレビューダイアログ
                listPictureDialog: false,
                // リスト内交通費添付画像プレビューダイアログ
                listPictureDialogT: false,
                // リスト内その他経費添付画像プレビューダイアログ
                listPictureDialogO: false,
                // 定期券入力項目内画像プレビューダイアログ
                picDialogVisible: false,
                // 交通費入力項目内画像プレビューダイアログ
                picDialogVisibleT: false,
                // その他経費入力項目内画像プレビューダイアログ
                picDialogVisibleO: false,
                picData: '',
                pic64Data: '',
                pic64DataT: '',
                pic64DataO: '',
                picSize: '',
                allPics: '',
                allPicsT: '',
                allPicsO: '',
                fileList: [],
                base64String: '',
                content64Type: '',
                base64StringT: '',
                content64TypeT: '',
                base64StringO: '',
                content64TypeO: '',
                // 定期画像
                base64pictures: [],
                // 旅費画像
                base64picturesT: [],
                // その他経費画像
                base64picturesO: [],
                // 定期券入力項目
                formTeiki: {
                    teikiKbn: '',
                    chosenDays:'',
                    input1: '',
                    input2: '',
                    amount: '',
                    company: '',
                    month: '',
                    textarea: '',
                    image: ''
                },
                // 定期券入力項目バリデーション
                rules: {
                        teikiKbn: [
                                    { required: true, message: '入力必須です。', trigger: 'change' }
                                ],
                        chosenDays: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        input1: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        input2: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        amount: [
                                    { type: 'number', required: true, message: '数字で入力必須です。', trigger: 'blur' }
                                ],
                        company: [
                                    { required: true, message: '入力必須です。', trigger: 'change' }
                                ],
                        month: [
                                    { required: true, message: '入力必須です。', trigger: 'change' }
                                ],
                        },
                // 交通費・宿泊・日当入力項目
                formTravel: {
                            travelKbn: '',
                            term: '',
                            days: '',
                            destination: '',
                            start: '',
                            end: '',
                            ways: '片道',
                            amount: '',
                            company:'',
                            // 備考
                            textarea: '',
                            image: ''
                },
                // 交通費・宿泊・日当入力項目バリデーション
                rules2: {
                        travelKbn: [
                                    { required: true, message: '入力必須です。', trigger: 'change' }
                                ],
                        term: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        days: [
                                { required: true, message: '入力必須です。', trigger: 'blur' }
                            ],
                        destination: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        start: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        end: [
                            { required: true, message: '入力必須です。', trigger: 'blur' }
                        ],
                        amount: [
                                    { type: 'number', required: true, message: '数字で入力必須です。', trigger: 'blur' }
                                ],
                        company: [
                                    { required: true, message: '入力必須です。', trigger: 'change' }
                                ],
                        },
                // その他経費入力項目
                formOther: {
                            travelKbn: '050',
                            term: '',
                            days: '',
                            destination: '',
                            amount: '',
                            company:'',
                            // 備考
                            textarea: '',
                            image: ''
                },
                // その他経費入力項目バリデーション
                rules3: {
                        term: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        destination: [
                                    { required: true, message: '入力必須です。', trigger: 'blur' }
                                ],
                        amount: [
                                    { type: 'number', required: true, message: '数字で入力必須です。', trigger: 'blur' }
                                ],
                        company: [
                                    { required: true, message: '入力必須です。', trigger: 'change' }
                                ],
                        },
                // 定期券・交通費・宿泊・日当の備考
                allRemarks: '',
                //  定期券・交通費・宿泊・日当備考ダイアログ
                dialogRemark: false,
                // 詳細リスト
                detailList: '',
                // 詳細:申請番号
                reqNumber: '',
                // 空画像判定
                countEmpty: '',
                // DB保存用データ
                saveData: '',
                disabled: false,
                // pdf用のタブ名
                tabName: ''
            }
        },

        computed: {
            uploadDisabled:function() {
                return this.fileList.length > 0
            },
        },

        methods: {
            async getOthers(){
                let otherKbnData = { companyCode: this.userData.companyCode
                                , categoryClassNumber: '051'
                                }
                await getCategory(otherKbnData).then(res => {
                    console.log(res)
                    this.otherKbnList = res.data.filter((e=>{return e.categoryKbn=='050'}))
                console.log('otherKbnList', this.otherKbnList)
                }).catch(error => {
                    this.tableLoading = false
                    console.log(error)
                })
            },
            // 定期ダイアログ開閉時のバリデーションの初期化
            teikiClose() {
                this.setEnptyTeiki()
            },
            teikiOpen() {
                this.$nextTick(()=>{
                    this.$refs['formTeiki'].clearValidate();
                })
            },
            // 旅費ダイアログ開閉時のバリデーションの初期化
            travelClose() {
                this.setEnptyTravel()
            },
            travelOpen() {
                 this.$nextTick(()=>{
                    this.$refs["formTravel"].clearValidate();
                })
            },
            // その他経費ダイアログ開閉時のバリデーションの初期化
            otherClose() {
                this.setEnptyOther()
            },
            otherOpen() {
                 this.$nextTick(()=>{
                    this.$refs["formOther"].clearValidate();
                })
            },
            // 定期券合計金額
            sumTeikiAmount() {
                this.teikiData = JSON.parse(sessionStorage.getItem("010_teiki"))
                console.log('this.teikiData.length',this.teikiData.length)
                // if(this.teikiData !== null) {
                if(this.teikiData.length >= 1 ) {
                    for(let i = 0; i < this.teikiData.length;i++) {
                        this.totalTeikiAmount = 0
                        for(let i = 0; i < this.teikiData.length;i++) {
                            this.totalTeikiAmount += this.teikiData[i].teikiAmount
                        }
                    }
                }else if(this.teikiData.length == 0){
                    this.totalTeikiAmount = ''
                }
            },
            // 旅費合計金額
            sumTravelAmount() {
                this.travelData = JSON.parse(sessionStorage.getItem("020_travel_expenses"))
                // if(this.travelData !== null) {
                if(this.travelData.length >= 1) {
                    for(let i = 0; i < this.travelData.length;i++){
                        this.totaltravelAmount = 0
                        for(let i = 0; i < this.travelData.length;i++) {
                            this.totaltravelAmount += this.travelData[i].travelAmount
                        }
                    }
                }else if(this.travelData.length == 0)
                    this.totaltravelAmount = ''
            },
            // その他経費合計金額
            sumOtherAmount() {
                this.otherData = JSON.parse(sessionStorage.getItem("050_other_expenses"))
                console.log('this.otherData', this.otherData)
                if(this.otherData.length >= 1) {
                    for(let i = 0; i < this.otherData.length;i++){
                        this.totalOtherAmount = 0
                        for(let i = 0; i < this.otherData.length;i++) {
                            this.totalOtherAmount += this.otherData[i].otherAmount
                        }
                    }
                }else if(this.otherData.length == 0)
                    this.totalOtherAmount = ''
            },
            // 各ボタンの表示/非表示
            statusSetBtn(){
                // 申請画面
                if(this.changebyFlg == "1"){
                    this.delFlg = false
                    this.getBack = false
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                    this.listBtn = false
                    this.listBtnC = false
                // 詳細画面
                }else if(this.changebyFlg == "2"){
                    // 状態＝保存
                    if(this.rowdata.requestStatusKbn == "001") {
                        this.inputOff = false
                        this.teikiKbnOff = false
                        this.travelKbnOff = false
                        this.listBtn = false
                        this.listBtnC = false
                        this.modifyFlgsave = true
                        this.reqFlg = true
                        this.teikiAddFlg = true
                        this.travelAddFlg = true
                        this.otherAddFlg = true
                        this.enterFlg = false
                        this.refFlg = false
                        this.pdfFlg = false
                    }
                    // 状態＝回覧中
                    if(this.rowdata.requestStatusKbn == "003"){
                        // 定期券
                        if(this.activeName == 'teiki') {
                        this.teikiKbnOff = true
                        // 交通費・宿泊・日当
                        }else if (this.activeName == 'travel') {
                            this.travelKbnOff = false
                        }
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.upFlg = false
                        this.enterFlg = false
                        this.teikiAddFlg = false
                        this.travelAddFlg = false
                        this.otherAddFlg = false
                        this.listBtn = true
                        this.listBtnC = false
                        this.inputOff = true
                        this.pdfFlg = false
                    }
                    // 状態＝回覧中（ログイン者：取戻ボタンの表示）
                    if(this.rowdata.requestStatusKbn == "003"&&this.userData.userId==this.rowdata.requestUserId){
                        this.getBack = true
                        this.pdfFlg = true
                    }
                    // 状態＝差戻
                    if(this.rowdata.requestStatusKbn == "010") {
                        this.modifyFlgsave = true
                        this.reqFlg = true
                        this.listBtn = false
                        this.listBtnC = false
                        this.inputOff = false
                        this.teikiKbnOff = false
                        this.travelKbnOff = false
                        this.enterFlg = false
                        this.refFlg = true
                        this.teikiAddFlg = true
                        this.travelAddFlg = true
                        this.otherAddFlg = true
                         this.pdfFlg = false
                    }
                    // 状態＝完了
                    if(this.rowdata.requestStatusKbn == "999"){
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.inputOff = true
                        this.enterFlg = false
                        this.listBtnC = false
                        this.pdfFlg = true
                    }
                        this.changebyFlgwf = false
                        this.changebyFlgapp = false
                        this.selectedMonth = true
                // 承認画面
                }else if(this.changebyFlg == "3"){
                    // 定期券
                    if(this.activeName == 'teiki') {
                        this.teikiKbnOff = true
                        this.travelKbnOff = true
                    // 交通費・宿泊・日当
                    }else if (this.activeName == 'travel') {
                        this.teikiKbnOff = false
                        this.travelKbnOff = false
                    }
                    console.log('this.rowdata.wfStatusKbn',this.rowdata.wfStatusKbn)
                    // 未承認
                    if(this.rowdata.wfStatusKbn =="020"){
                        this.changebyFlgwf = true
                        this.changebyFlgapp = true
                        this.pdfFlg = true
                    // 差戻
                    }else if (this.rowdata.wfStatusKbn == "021") {
                        this.changebyFlgwf = false
                        this.changebyFlgapp = false
                        this.pdfFlg = true
                        console.log('this.inputOff',this.inputOff)
                    // 承認済、承認完了
                    }else if (this.rowdata.wfStatusKbn =="025"|| this.rowdata.wfStatusKbn =="900") {
                        this.changebyFlgapp = false
                        this.pdfFlg = true
                    }
                    this.delFlg = false
                    this.modifyFlgsave = false
                    this.reqFlg = false
                    this.getBack = false
                    this.teikiAddFlg = false
                    this.travelAddFlg = false
                    this.otherAddFlg = false
                    this.upFlg = false
                    this.inputOff = true
                    this.listBtn = true
                    this.listBtnC = false
                    this.enterFlg = false
                    this.selectedMonth = true
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
                this.yM = yy + '-' + mm
                this.yMd = yy+mm+dd
            },
            // 定期券数計算
            countDays(){
                this.formTeiki.month = ''
                this.startDate = ''
                this.endDate = ''
                if(this.formTeiki.chosenDays[0] || this.formTeiki.chosenDays[1]){
                    this.startDate = this.formTeiki.chosenDays[0]
                    this.endDate = this.formTeiki.chosenDays[1]
                }else{
                    console.log('this.editList', this.editList)
                    this.startDate = this.editList.startDate.substring(0,4) + '-' + this.editList.startDate.substring(4,6) + '-' + this.editList.startDate.substring(6,8)
                    this.endDate = this.editList.endDate.substring(0,4) + '-' + this.editList.endDate.substring(4,6) + '-' + this.editList.endDate.substring(6,8)
                } 
                console.log('this.startDate',this.startDate)
                console.log('this.endDate',this.endDate)
                this.startD = new Date(this.startDate.replace(/-/g , "/"))
                this.endD = new Date(this.endDate.replace(/-/g , "/"))
                console.log('this.startD',this.startD)
                console.log('this.endD',this.endD)
                let dayCount = 0
                while (this.endD >= this.startD) {
                    dayCount++
                    this.startD.setDate(this.startD.getDate() + 1)
                }
                this.dayCount = dayCount
                console.log('AAAthis.dayCount', this.dayCount)
            },
             //nヶ月前後の年月日を取得する
            getAddMonthDate(fstrDate,add) {
                let fDate = new Date(fstrDate.replace(/-/g , "/"));
                fDate.setMonth(fDate.getMonth() + add);
                //add分を加えた月の最終日を取得
                let endDate = this.getEndOfMonth(fDate.getFullYear(),fDate.getMonth());

                //引数で渡された日付がnヶ月後の最終日より大きければ日付を次月最終日に合わせる
                //5/31→6/30のように応当日が無い場合に必要
                let day = Number(fDate.getDate());
                if(day > endDate){
                    day = endDate;
                }else{
                    day = day - 1;
                }

                var addMonthDate = new Date(fDate.getFullYear(),fDate.getMonth(),day);
                return addMonthDate;
            },

            //今月の月末日を取得
            //※次月の0日目＝今月の末日になる
            getEndOfMonth(year,month){
                var endDate = new Date(year,month,0);
                return Number(endDate.getDate());
            },
            // 適用期間と月区分が変わるたびに処理するイベント
            checkDays(){
                if(this.formTeiki.month && this.formTeiki.chosenDays && this.formTeiki.chosenDays.length == 2){
                    // 適用期間を取得
                    let fstrDate = this.formTeiki.chosenDays[0];    //適用開始日（yyyy-mm-dd）
                    let tstrDate = this.formTeiki.chosenDays[1];    //適用終了日（yyyy-mm-dd）
                    let addMonth = this.formTeiki.month;            //月区分（ｎヶ月）
                    let tDate = new Date(tstrDate.replace(/-/g , "/"));                 //適用終了日（日付型）
                    
                    // 適用開始日からｎヶ月後の日付を取得
                    let limitDate = this.getAddMonthDate(fstrDate,parseInt(addMonth,10));

                    //　ｎヶ月後の日付と適用終了日を比較し、年月日が何れか違ったらエラーを出力する
                    if(limitDate.getFullYear() != tDate.getFullYear() 
                        || limitDate.getMonth() != tDate.getMonth()
                        || limitDate.getDate() != tDate.getDate() ){
                        this.formTeiki.month = ''
                        Message({
                            message: '選択日数と月数が合っていません。',
                            type: 'error',
                            duration: 4000
                        })
                    }
                }
            },
            // 旅費日数計算
            countTravelDays() {
                console.log('term', this.formTravel.term)
                if (this.formTravel.term == '' || this.formTravel.term == null) {
                    return
                }else {
                    let startDate = this.formTravel.term[0]
                    let endDate = this.formTravel.term[1]
                    let startD = new Date(startDate.replace(/-/g , "/"))
                    let endD = new Date(endDate.replace(/-/g , "/"))
                    console.log('startD',startD)
                    console.log('endD',endD)
                    let dayCount = 0
                    while (endD >= startD) {
                        dayCount++
                        startD.setDate(startD.getDate() + 1)
                    }
                    this.travelCountDays = dayCount
                    console.log('this.travelCountDays', this.travelCountDays)
                }
            },
            // その他経費日数計算
            countOtherDays() {
                console.log('term', this.formOther.term)
                if (this.formOther.term == '' || this.formOther.term == null) {
                    return
                }else {
                    let startDate = this.formOther.term[0]
                    let endDate = this.formOther.term[1]
                    let startD = new Date(startDate.replace(/-/g , "/"))
                    let endD = new Date(endDate.replace(/-/g , "/"))
                    console.log('startD',startD)
                    console.log('endD',endD)
                    let dayCount = 0
                    while (endD >= startD) {
                        dayCount++
                        startD.setDate(startD.getDate() + 1)
                    }
                    this.otherCountDays = dayCount
                    console.log('this.otherCountDays', this.otherCountDays)
                }
            },
            // 日数初期化
            onClear() {
                if(this.formTravel.term == null) {
                    this.travelCountDays = ''
                }
            },
            // 日数初期化
            onOtherClear() {
                if(this.formOther.term == null) {
                    this.otherCountDays = ''
                }
            },
            // 月区分初期化
            onMonthClear(){
                if(this.formTeiki.chosenDays == null){
                    this.formTeiki.month = ''
                }
            },
            // リストに追加
            addList() {
                if (this.changebyFlg == '1') {

                } else if (this.changebyFlg == '2') {
                    this.reqNum = this.rowdata.requestNumber
                }
                // 定期
                if(this.activeName == 'teiki'){
                    this.$refs["formTeiki"].validate((valid) => {
                    if (valid) {
                        console.log('this.applyMonth', this.applyMonth)
                        let arr = { companyCode: this.userData.companyCode
                                  , applyYm: this.applyMonth.replaceAll('-', '')
                                  , requestNumber: this.reqNum
                                  , requestSubNumber: ''
                                  , requestUserId: this.userData.userId
                                  , index: this.teikiData == null ? 1 : this.teikiData.length + 1
                                  , term: this.formTeiki.chosenDays[0].replaceAll('-', '/') + ' - ' + this.formTeiki.chosenDays[1].replaceAll('-', '/')
                                  , startDate: this.formTeiki.chosenDays[0].replaceAll('-', '')
                                  , endDate: this.formTeiki.chosenDays[1].replaceAll('-', '')
                                  , monthKbn: this.formTeiki.month
                                  , month: this.formTeiki.month + 'ヶ月'
                                  , days: ''
                                  , applyKbn: '010'
                                  , travelKbn: this.formTeiki.teikiKbn
                                  , station: this.formTeiki.input1 + ' - ' + this.formTeiki.input2
                                  , departure: this.formTeiki.input1
                                  , arrival: this.formTeiki.input2
                                  , wayKbn: ''
                                  , travelWays: ''
                                  , destination: ''
                                  , recipientKbn: this.formTeiki.company
                                  , institution: this.numSearchK(this.formTeiki.teikiKbn)
                                  , amount: this.formTeiki.amount
                                  , teikiAmount: Number(this.formTeiki.amount)
                                  , company: this.numSearchR(this.formTeiki.company)
                                  , remark: this.formTeiki.textarea
                                  , image: ''
                                  , img64Str: this.base64String
                                  , img64contentType: this.content64Type
                                }
                        saveImgTemporary(arr).then(res => {
                            console.log(res.data)
                            this.base64String = '';
                            this.content64Type = '';
                            arr.image = res.data
                            if(this.teikiData) {
                                this.teikiData.push(arr);
                            } else {
                                this.teikiData = [];
                                this.teikiData.push(arr);
                            }
                            sessionStorage.setItem("010_teiki",JSON.stringify(this.teikiData))
                        // 定期入力項目空にする
                        this.setEnptyTeiki()
                        // 定期合計金額
                        this.sumTeikiAmount()
                        // 入力ダイアログ非表示
                        this.teikiInputDialog = false
                        // 申請ボタン表示
                        this.reqFlg = true
                        // 保存ボタン表示
                        this.modifyFlgsave = true
                        }).catch(error => {
                            console.log('getInfo fail')
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    });
                }else if(this.activeName == 'travel') {
                    console.log('タブ名', this.activeName)
                    // 宿泊/日当
                    if(this.formTravel.travelKbn == '030' || this.formTravel.travelKbn == '040'){
                        console.log('宿泊', this.formTravel.travelKbn)
                        this.$refs["formTravel"].validate((valid) => {
                        if (valid) {
                            let arr = { companyCode: this.userData.companyCode
                                      , applyYm: this.applyMonth.replaceAll('-', '')
                                      , requestNumber: this.reqNum
                                      , requestSubNumber: ''
                                      , requestUserId: this.userData.userId
                                      , index: this.travelData == null ? 1 : this.travelData.length + 1
                                      , term: this.formTravel.term[0].replaceAll('-', '/') + ' - ' + this.formTravel.term[1].replaceAll('-', '/')
                                      , startDate: this.formTravel.term[0].replaceAll('-', '')
                                      , endDate: this.formTravel.term[1].replaceAll('-', '')
                                      , monthKbn: ''
                                      , month: ''
                                      , days: this.travelCountDays
                                      , applyKbn: '020'
                                      , travelKbn: this.formTravel.travelKbn
                                      , station: this.formTravel.destination
                                      , departure: ''
                                      , arrival: ''
                                      , wayKbn: ''
                                      , travelWays: ''
                                      , destination: this.formTravel.destination
                                      , recipientKbn: this.formTravel.company
                                      , institution: this.numSearchT(this.formTravel.travelKbn)
                                      , amount: this.formTravel.amount
                                      , travelAmount: Number(this.formTravel.amount)
                                      , company: this.numSearchR(this.formTravel.company)
                                      , remark: this.formTravel.textarea
                                      , image: ''
                                      , img64Str: this.base64StringT
                                      , img64contentType: this.content64TypeT
                                    }
                            saveImgTemporary(arr).then(res => {
                                console.log('宿泊内容/日当内容', this.travelData)
                                this.base64StringT = '';
                                this.content64TypeT = '';
                                arr.image = res.data;
                                if(this.travelData) {
                                    this.travelData.push(arr);
                                } else {
                                    this.travelData = [];
                                    this.travelData.push(arr);
                                }
                                sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                                // 旅費入力項目空にする
                                this.setEnptyTravel()
                                // 旅費合計計算
                                this.sumTravelAmount()
                                // 旅費入力ポップアップ非表示
                                this.travelInputDialog = false
                                // 申請ボタン表示
                                this.reqFlg = true
                                // 保存ボタン表示
                                this.modifyFlgsave = true
                            }).catch(error => {
                            console.log('getInfo fail')
                            });
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                        });
                    // 交通費
                    }else {
                        console.log('交通費', this.formTravel.travelKbn)
                        console.log('this.formTravel.term', this.formTravel.term)
                        this.$refs["formTravel"].validate((valid) => {
                        if (valid) {
                            console.log('this.travelData', this.travelData)
                            let arr = { companyCode: this.userData.companyCode
                                      , applyYm: this.applyMonth.replaceAll('-', '')
                                      , requestNumber: this.reqNum
                                      , requestSubNumber: ''
                                      , requestUserId: this.userData.userId
                                      , index: this.travelData == null ? 1 : this.travelData.length + 1
                                      , term: this.formTravel.term[0].replaceAll('-', '/') + ' - ' + this.formTravel.term[1].replaceAll('-', '/')
                                      , startDate: this.formTravel.term[0].replaceAll('-', '')
                                      , endDate: this.formTravel.term[1].replaceAll('-', '')
                                      , monthKbn: ''
                                      , month: ''
                                      , days: this.travelCountDays
                                      , applyKbn: '020'
                                      , travelKbn: this.formTravel.travelKbn
                                      , station: this.formTravel.start + ' - ' + this.formTravel.end
                                      , departure: this.formTravel.start
                                      , arrival: this.formTravel.end
                                      , wayKbn: this.nameSearchW(this.formTravel.ways)
                                      , travelWays: this.formTravel.ways
                                      , destination: ''
                                      , recipientKbn: this.formTravel.company
                                      , institution: this.numSearchT(this.formTravel.travelKbn)
                                      , amount: this.formTravel.amount
                                      , travelAmount: Number(this.formTravel.amount)
                                      , company: this.numSearchR(this.formTravel.company)
                                      , remark: this.formTravel.textarea
                                      , image: ''
                                      , img64Str: this.base64StringT
                                      , img64contentType: this.content64TypeT
                                    }
                            saveImgTemporary(arr).then(res => {
                                console.log('交通費内容',this.travelData)
                                this.base64StringT = '';
                                this.content64TypeT = '';
                                arr.image = res.data;
                                if(this.travelData) {
                                    this.travelData.push(arr);
                                } else {
                                    this.travelData = [];
                                    this.travelData.push(arr);
                                }
                                sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                                // 旅費入力項目空にする
                                this.setEnptyTravel()
                                // 旅費合計計算
                                this.sumTravelAmount()
                                // 旅費入力ポップアップ非表示
                                this.travelInputDialog = false　
                                // 申請ボタン表示
                                this.reqFlg = true
                                // 保存ボタン表示
                                this.modifyFlgsave = true
                            }).catch(error => {
                                console.log('getInfo fail')
                            });
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                        });
                    }
                }else if(this.activeName == 'other') {
                    // その他経費
                    console.log('タブ名', this.activeName)
                    console.log('その他経費', this.formOther.travelKbn)
                    this.$refs["formOther"].validate((valid) => {
                    if (valid) {
                        let arr = { companyCode: this.userData.companyCode
                                    , applyYm: this.applyMonth.replaceAll('-', '')
                                    , requestNumber: this.reqNum
                                    , requestSubNumber: ''
                                    , requestUserId: this.userData.userId
                                    , index: this.otherData == null ? 1 : this.otherData.length + 1
                                    , term: this.formOther.term[0].replaceAll('-', '/') + ' - ' + this.formOther.term[1].replaceAll('-', '/')
                                    , startDate: this.formOther.term[0].replaceAll('-', '')
                                    , endDate: this.formOther.term[1].replaceAll('-', '')
                                    , monthKbn: ''
                                    , month: ''
                                    , days: this.otherCountDays
                                    , applyKbn: '030'
                                    , travelKbn: '050'
                                    , station: this.formOther.destination
                                    , departure: ''
                                    , arrival: ''
                                    , wayKbn: ''
                                    , travelWays: ''
                                    , destination: this.formOther.destination
                                    , recipientKbn: this.formOther.company
                                    , institution: this.numSearchO(this.formOther.travelKbn)
                                    , amount: this.formOther.amount
                                    , otherAmount: Number(this.formOther.amount)
                                    , company: this.numSearchR(this.formOther.company)
                                    , remark: this.formOther.textarea
                                    , image: ''
                                    , img64Str: this.base64StringO
                                    , img64contentType: this.content64TypeO
                                }
                        saveImgTemporary(arr).then(res => {
                            console.log('その他経費', this.otherData)
                            this.base64StringO = '';
                            this.content64TypeO = '';
                            arr.image = res.data;
                            if(this.otherData) {
                                this.otherData.push(arr);
                            } else {
                                this.otherData = [];
                                this.otherData.push(arr);
                            }
                            sessionStorage.setItem("050_other_expenses",JSON.stringify(this.otherData))
                        // その他経費入力項目空にする
                        this.setEnptyOther()
                        // その他経費合計計算
                        this.sumOtherAmount()
                        // その他経費入力ポップアップ非表示
                        this.otherInputDialog = false
                        // 申請ボタン表示
                        this.reqFlg = true
                        // 保存ボタン表示
                        this.modifyFlgsave = true
                    }).catch(error => {
                        console.log('getInfo fail')
                    });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    });
                }
            },
            // 経費区分番号→旅費名称(定期)
            numSearchK(number) {
                let num = Number(number)
                console.log('teikiKbnList', this.teikiKbnList)
                for(let i = 0; i < this.teikiKbnList.length;i++) {
                    if(this.teikiKbnList[i].categoryKbn == num) {
                        console.log('categoryKbnName', this.teikiKbnList[i].categoryKbnName)
                        return this.teikiKbnList[i].categoryKbnName
                    }
                }
            },
            // 旅費名称→経費区分番号(定期)
            nameSearchI(name) {
                console.log('teikiKbnList', this.teikiKbnList)
                for(let i = 0; i < this.teikiKbnList.length;i++) {
                    if(this.teikiKbnList[i].categoryKbnName == name) {
                        console.log('categoryKbn', this.teikiKbnList[i].categoryKbn)
                        return this.teikiKbnList[i].categoryKbn
                    }
                }
            },
            // 経費区分番号→旅費名称(交通費)
            numSearchT(number) {
                let num = Number(number)
                console.log('travelKbnList', this.travelKbnList)
                for(let i = 0; i < this.travelKbnList.length;i++) {
                    if(this.travelKbnList[i].categoryKbn == num) {
                        console.log('categoryKbnName', this.travelKbnList[i].categoryKbnName)
                        return this.travelKbnList[i].categoryKbnName
                    }
                }
            },
            // 旅費名称→経費区分番号(交通費)
            nameSearchT(name) {
                console.log('travelKbnList', this.travelKbnList)
                for(let i = 0; i < this.travelKbnList.length;i++) {
                    if(this.travelKbnList[i].categoryKbnName == name) {
                        console.log('categoryKbn', this.travelKbnList[i].categoryKbn)
                        return this.travelKbnList[i].categoryKbn
                    }
                }
            },
            // 経費区分番号→旅費名称(その他経費)
            numSearchO(number) {
                let num = Number(number)
                console.log('otherKbnList', this.otherKbnList)
                for(let i = 0; i < this.otherKbnList.length;i++) {
                    if(this.otherKbnList[i].categoryKbn == num) {
                        console.log('categoryKbnName', this.otherKbnList[i].categoryKbnName)
                        return this.otherKbnList[i].categoryKbnName
                    }
                }
            },
            // 旅費名称→経費区分番号(その他経費)
            nameSearchO(name) {
                console.log('otherKbnList', this.otherKbnList)
                for(let i = 0; i < this.otherKbnList.length;i++) {
                    if(this.otherKbnList[i].categoryKbnName == name) {
                        console.log('categoryKbn', this.otherKbnList[i].categoryKbn)
                        return this.otherKbnList[i].categoryKbn
                    }
                }
            },
            // 請求区分名称→請求区分番号
            nameSearchC(name) {
                console.log('reimbursementKbnList', this.reimbursementKbnList)
                for(let i = 0; i < this.reimbursementKbnList.length;i++) {
                    if(this.reimbursementKbnList[i].categoryKbnName == name) {
                        console.log('categoryKbn', this.reimbursementKbnList[i].categoryKbn)
                        return this.reimbursementKbnList[i].categoryKbn
                    }
                }
            },
            // 請求区分番号→請求区分名称
            numSearchR(number) {
                let num = Number(number)
                console.log('reimbursementKbnList', this.reimbursementKbnList)
                for(let i = 0; i < this.reimbursementKbnList.length;i++) {
                    if(this.reimbursementKbnList[i].categoryKbn == num) {
                        console.log('categoryKbnName', this.reimbursementKbnList[i].categoryKbnName)
                        return this.reimbursementKbnList[i].categoryKbnName
                    }
                }
            },
            // 往復区分名称→往復区分番号
            nameSearchW(name) {
                for(let i = 0; i < this.wayList.length;i++) {
                    if(this.wayList[i].categoryKbnName == name) {
                        return this.wayList[i].categoryKbn
                    }
                }
            },
            // 往復区分番号→往復区分名称
            numSearchW(number) {
                let num = Number(number)
                for(let i = 0; i < this.wayList.length;i++) {
                    if(this.wayList[i].categoryKbn == num) {
                        return this.wayList[i].categoryKbnName
                    }
                }
            },
            // 備考
            getRemark(row) {
                this.getInfo = row
                this.allRemarks = this.getInfo.remark
                this.dialogRemark = true
            },
            // 添付画像
            getImage(row) {
                console.log('row', row)
                if(this.activeName == 'teiki') {
                    this.allPics = row.image;
                    this.listPictureDialog = true
                }else if (this.activeName == 'travel'){
                    this.allPicsT = row.image;
                    console.log('this.allPicsT', this.allPicsT)
                    this.listPictureDialogT = true
                }else if (this.activeName == 'other'){
                    this.allPicsO = row.image;
                    console.log('this.allPicsO', this.allPicsO)
                    this.listPictureDialogO = true
                }
            },
            // リスト内編集ボタン
            editDetail(row) {
                this.editList = row
                console.log('editList', this.editList)
                console.log('this.activeName', this.activeName)
                // 追加確定ボタン
                this.finalizeFlg = false
                // 編集確定ボタン
                this.EditFinalizeFlg = true
                if (this.editList.image !== '' && this.editList.image !== null){
                    this.changeImage(this.editList.image)
                }
                if(this.activeName == 'teiki') {
                    this.countDays()
                    // 入力項目表示
                    this.teikiInputDialog = true
                    this.index =  this.editList.index
                    // this.editIndex = this.index
                    console.log('editIndex', this.index)
                    this.formTeiki.amount = this.editList.amount
                    this.formTeiki.teikiKbn = this.nameSearchI(this.editList.institution)
                    this.formTeiki.month = this.editList.monthKbn
                    this.formTeiki.company = this.nameSearchC(this.editList.company)
                    this.formTeiki.textarea = this.editList.remark
                    this.editTerm =  this.editList.term.split('-')
                    this.formTeiki.chosenDays = [this.editTerm[0].replaceAll('/', '-'), this.editTerm[1].replaceAll('/', '-')]
                    this.editChosenDays = this.editTerm[0].replaceAll('/', '-') + ' - ' + this.editTerm[1].replaceAll('/', '-')
                    this.formTeiki.input1 = this.editList.departure
                    this.formTeiki.input2 = this.editList.arrival
                    this.teikiEditData = []
                    let arr = { companyCode: this.editList.companyCode
                              , applyYm: this.applyMonth.replaceAll('-', '')
                              , requestNumber: this.editList.requestNumber
                              , requestSubNumber: this.editList.requestSubNumber
                              , requestUserId: this.editList.requestUserId
                              , index: this.index
                              , term: this.editChosenDays
                              , startDate: this.editList.startDate
                              , endDate: this.editList.endDate
                              , monthKbn: this.editList.monthKbn
                              , month: this.editList.monthKbn + 'ヶ月'
                              , days: ''
                              , applyKbn: this.editList.applyKbn
                              , travelKbn: this.formTeiki.teikiKbn
                              , station: this.formTeiki.input1 +'-'+ this.formTeiki.input2
                              , departure: this.formTeiki.input1
                              , arrival: this.formTeiki.input2
                              , wayKbn: ''
                              , travelWays: ''
                              , destination: ''
                              , recipientKbn: this.formTeiki.company
                              , institution: this.formTeiki.teikiKbn
                              , amount: this.formTeiki.amount
                              , teikiAmount: this.formTeiki.amount
                              , company: this.formTeiki.company
                              , remark: this.formTeiki.textarea
                              , image: this.editList.image
                            }
                    this.teikiEditData.push(arr);
                    // console.log('TeikiEditSessionData', this.teikiEditData)
                    sessionStorage.setItem("010_edit",JSON.stringify(this.teikiEditData))
                    this.fileList = []
                    if (this.editList.image !== ''){
                      this.fileList.push({ name:'picture', url: this.editList.image })
                    }
                   
                    if (this.teikiKbnOff === false) {
                        this.teikiKbnOff = true
                    }
                }else if (this.activeName == 'travel') {
                    // 入力項目表示
                    this.travelInputDialog = true
                    // 入力欄初期化
                    this.setEnptyTravel()
                    // 入力共通部
                    this.index =  this.editList.index
                    this.editTerm =  this.editList.term.split('-')
                    this.formTravel.term = [this.editTerm[0].replaceAll('/', '-'), this.editTerm[1].replaceAll('/', '-')]
                    this.travelCountDays = this.editList.days
                    this.formTravel.travelKbn = this.nameSearchT(this.editList.institution)
                    this.formTravel.company = this.nameSearchC(this.editList.company)
                    this.formTravel.amount = this.editList.amount
                    this.formTravel.textarea = this.editList.remark
                    this.formTravel.image = this.editList.image
                    console.log('経費区分:宿泊', this.editList.travelKbn)
                    if(this.editList.travelKbn == '030') {
                        this.editStation = this.editList.destination
                        this.editDestination = this.editList.destination
                        this.formTravel.ways = ''
                        this.formTravel.destination = this.editList.destination
                    }else if (this.editList.travelKbn == '040') {
                        console.log('日当', this.editList.travelKbn)
                        this.editStation = this.editList.destination
                        this.editDestination = this.editList.destination
                        this.formTravel.ways = ''
                        this.formTravel.destination = this.editList.destination
                    }else if (this.editList.travelKbn == '010' || this.editList.travelKbn == '011' || this.editList.travelKbn == '012' || this.editList.travelKbn == '020' || this.editList.travelKbn == '021' || this.editList.travelKbn == '022') {
                        console.log('交通費', this.editList.travelKbn)
                        this.formTravel.start = this.editList.departure
                        this.formTravel.end = this.editList.arrival
                        this.editStation = this.formTravel.start +' - '+ this.formTravel.end
                        this.editDestination = ''
                        this.formTravel.ways = this.editList.travelWays
                    }
                    this.transportationEditData = []
                    let arr = { companyCode: this.editList.companyCode
                              , applyYm: this.applyMonth.replaceAll('-', '')
                              , requestNumber: this.editList.requestNumber
                              , requestSubNumber: this.editList.requestSubNumber
                              , requestUserId: this.editList.requestUserId
                              , index: this.index
                              , term: this.formTravel.term
                              , startDate: this.editList.startDate
                              , endDate: this.editList.endDate
                              , monthKbn: ''
                              , month: ''
                              , days: this.editList.days
                              , applyKbn: this.editList.applyKbn
                              , travelKbn: this.editList.travelKbn
                              , station: this.editStation
                              , departure: this.editList.departure
                              , arrival: this.editList.arrival
                              , wayKbn: this.editList.wayKbn
                              , travelWays: this.formTravel.ways
                              , destination: this.editDestination
                              , recipientKbn: this.editList.recipientKbn 
                              , institution: this.formTravel.travelKbn
                              , amount: this.formTravel.amount
                              , travelAmount: this.editList.travelAmount
                              , company: this.formTravel.company
                              , remark: this.editList.remark
                              , image: this.editList.image
                            }
                    
                    console.log('arr', arr)
                    this.transportationEditData.push(arr);
                    console.log('this.transportationEditData', this.transportationEditData)
                    sessionStorage.setItem("020_travel_expenses_edit",JSON.stringify(this.transportationEditData))
                    
                    this.fileList = []
                    if (this.editList.image !== ''){
                        this.fileList.push({ name:'picture', url: this.editList.image })
                    }
                    
                    if (this.travelKbnOff === false) {
                        this.travelKbnOff = true
                    }
                }else if (this.activeName == 'other') {
                    // 入力項目表示
                    this.otherInputDialog = true
                    // 入力欄初期化
                    this.setEnptyOther()
                    // 入力共通部
                    this.index =  this.editList.index
                    this.editTerm =  this.editList.term.split('-')
                    this.formOther.term = [this.editTerm[0].replaceAll('/', '-'), this.editTerm[1].replaceAll('/', '-')]
                    this.otherCountDays = this.editList.days

                    this.formOther.travelKbn = this.nameSearchO(this.editList.institution)
                    this.formOther.company = this.nameSearchC(this.editList.company)
                    this.formOther.amount = this.editList.amount
                    this.formOther.textarea = this.editList.remark

                    console.log('経費区分:その他経費', this.editList.travelKbn)
                    this.editStation = this.editList.destination
                    this.editDestination = this.editList.destination
                    this.formOther.destination = this.editList.destination

                    this.otherEditData = []
                    let arr = { companyCode: this.editList.companyCode
                              , applyYm: this.applyMonth.replaceAll('-', '')
                              , requestNumber: this.editList.requestNumber
                              , requestSubNumber: this.editList.requestSubNumber
                              , requestUserId: this.editList.requestUserId
                              , index: this.index
                              , term: this.formTravel.term
                              , startDate: this.editList.startDate
                              , endDate: this.editList.endDate
                              , monthKbn: ''
                              , month: ''
                              , days: this.editList.days
                              , applyKbn: this.editList.applyKbn
                              , travelKbn: this.editList.travelKbn
                              , station: this.editStation
                              , departure: ''
                              , arrival: ''
                              , wayKbn: ''
                              , travelWays: ''
                              , destination: this.editDestination
                              , recipientKbn: this.editList.recipientKbn 
                              , institution: this.formOther.travelKbn
                              , amount: this.formOther.amount
                              , otherAmount: this.editList.otherAmount
                              , company: this.formOther.company
                              , remark: this.editList.remark
                              , image: this.editList.image
                            }
                    
                    console.log('arr', arr)
                    this.otherEditData.push(arr);
                    console.log('this.otherEditData', this.otherEditData)
                    sessionStorage.setItem("050_other_expenses_edit",JSON.stringify(this.otherEditData))
                    this.fileList = []
                    if (this.editList.image !== ''){
                        this.fileList.push({ name:'picture', url: this.editList.image })
                    }
                }
            },
            // 編集（確定）ボタン
            editBtn() {
                // 定期券編集内容
                if(this.activeName == 'teiki') {
                    this.$refs["formTeiki"].validate((valid) => {
                    if (valid) {
                        // リスト用データ
                        this.teikiRewriteData = JSON.parse(sessionStorage.getItem("010_edit"))
                        this.editIndex = this.teikiRewriteData[0].index
                        // 編集時に期間を編集する時としない時で処理が変わる
                        console.log('this.formTeiki.chosenDays[0]', this.formTeiki.chosenDays[0])
                        if(this.formTeiki.chosenDays[0] || this.formTeiki.chosenDays[1]){
                            this.startDate = this.formTeiki.chosenDays[0]
                            this.endDate = this.formTeiki.chosenDays[1]
                        }else{
                            console.log('this.editList', this.editList)
                            this.startDate = this.teikiData[this.editIndex - 1].startDate.substring(0,4) + '-' + this.teikiData[this.editIndex - 1].startDate.substring(4,6) + '-' + this.teikiData[this.editIndex - 1].startDate.substring(6,8)
                            this.endDate = this.teikiData[this.editIndex - 1].endDate.substring(0,4) + '-' + this.teikiData[this.editIndex - 1].endDate.substring(4,6) + '-' + this.teikiData[this.editIndex - 1].endDate.substring(6,8)
                        }
                        console.log('this.startDate', this.startDate)
                        console.log('this.endDate', this.endDate)
                        let startD = new Date(this.startDate.replace(/-/g , "/"))
                        console.log('startD', startD)
                        let endD = new Date(this.endDate.replace(/-/g , "/"))
                        let dayCount = 0
                        while (endD >= startD) {
                            dayCount++
                            startD.setDate(startD.getDate() + 1)
                        }
                        this.dayCount = dayCount
                        console.log('BBBthis.dayCount', this.dayCount)
                        this.checkDays()

                        this.teikiData[this.editIndex - 1].amount = this.formTeiki.amount
                        this.teikiData[this.editIndex - 1].teikiAmount = Number(this.formTeiki.amount)
                        this.teikiData[this.editIndex - 1].institution = this.numSearchK(this.formTeiki.teikiKbn)
                        this.teikiData[this.editIndex - 1].month = this.formTeiki.month + 'ヶ月'
                        console.log('this.formTeiki.month',this.formTeiki.month)
                        this.teikiData[this.editIndex - 1].company = this.numSearchR(this.formTeiki.company)
                        this.teikiData[this.editIndex - 1].remark = this.formTeiki.textarea
                        // this.teikiData[this.editIndex - 1].term =  this.formTeiki.chosenDays[0] + ' ～ ' + this.formTeiki.chosenDays[1]
                        this.teikiData[this.editIndex - 1].term =  this.startDate.replaceAll('-', '/').trim() + ' - ' + this.endDate.replaceAll('-', '/').trim()
                        this.formTeiki.chosenDays = this.editTerm[0].replaceAll('/', '-')   + ' - ' + this.editTerm[1].replaceAll('/', '-') 
                        // this.editTeikiTerm = this.teikiData[this.editIndex - 1].term.split('～')
                        // this.editTeikiStartDate = this.editTeikiTerm[0].replaceAll('/', '')
                        // this.editTeikiEndDate = this.editTeikiTerm[1].replaceAll('/', '')
                        this.teikiData[this.editIndex - 1].station = this.formTeiki.input1 + ' - ' + this.formTeiki.input2
                        console.log('this.editIndex',this.editIndex)
                        console.log('this.base64pictures[this.editIndex - 1]',this.base64pictures[this.editIndex - 1])
                        console.log('this.base64String',this.base64String)
                        console.log('this.content64Type',this.content64Type)

                        this.base64pictures[this.editIndex - 1] = { picture:this.base64String
                                                                  , contentType:this.content64Type
                                                                }
                        console.log('this.startDate', this.startDate)
                        // DB用データ
                        this.teikiData[this.editIndex - 1].startDate =  this.startDate.replaceAll('-', '').trim()
                        this.teikiData[this.editIndex - 1].endDate =  this.endDate.replaceAll('-', '').trim()
                        // this.teikiData[this.editIndex - 1].startDate =  this.editTeikiStartDate
                        // this.teikiData[this.editIndex - 1].endDate =  this.editTeikiEndDate
                        this.teikiData[this.editIndex - 1].monthKbn = this.formTeiki.month
                        this.teikiData[this.editIndex - 1].days = ''
                        this.teikiData[this.editIndex - 1].applyKbn = '010'
                        this.teikiData[this.editIndex - 1].travelKbn = this.teikiData[this.editIndex - 1].travelKbn
                        this.teikiData[this.editIndex - 1].departure = this.formTeiki.input1
                        this.teikiData[this.editIndex - 1].arrival = this.formTeiki.input2
                        this.teikiData[this.editIndex - 1].wayKbn = ''
                        this.teikiData[this.editIndex - 1].destination = ''
                        this.teikiData[this.editIndex - 1].recipientKbn = this.formTeiki.company
                        if (this.base64String != ''){
                            this.teikiData[this.editIndex - 1].img64Str = this.base64String
                            this.teikiData[this.editIndex - 1].img64contentType = this.content64Type
                            saveImgTemporary(this.teikiData[this.editIndex - 1]).then(res => {
                                console.log('定期券', this.teikiData)
                                this.base64String = '';
                                this.content64Type = '';
                                this.teikiData[this.editIndex - 1].image = res.data;
                                this.editList.image = res.data;
                                this.formTeiki.image = res.data;
                                sessionStorage.setItem("010_teiki",JSON.stringify(this.teikiData))
                                sessionStorage.removeItem("010_edit",JSON.stringify(this.teikiEditData))
                                // 定期入力項目空
                                this.setEnptyTeiki()
                                // 定期合計金額
                                this.sumTeikiAmount()
                            }).catch(error => {
                                console.log('getInfo fail')
                            });
                        } else {
                            this.teikiData[this.editIndex - 1].image = ''
                            console.log('image', this.teikiData)
                            sessionStorage.setItem("010_teiki",JSON.stringify(this.teikiData))
                            sessionStorage.removeItem("010_edit",JSON.stringify(this.teikiEditData))
                            // 定期入力項目空
                            this.setEnptyTeiki()
                            // 定期合計金額
                            this.sumTeikiAmount()
                        }
                        // 定期入力ダイアログ
                        this.teikiInputDialog = false
                        // 追加ボタン表示
                        this.teikiAddFlg = true
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    });
                }else if (this.activeName == 'travel') {
                    this.$refs["formTravel"].validate((valid) => {
                        if (valid) {
                        console.log('this.formTravel.travelKbn', this.formTravel.travelKbn)
                        this.travelRewriteData = JSON.parse(sessionStorage.getItem("020_travel_expenses_edit"))
                        this.editIndex = this.travelRewriteData[0].index
                        // リスト用データ
                        this.travelData[this.editIndex - 1].amount = this.formTravel.amount
                        this.travelData[this.editIndex - 1].travelAmount = Number(this.formTravel.amount)
                        this.travelData[this.editIndex - 1].institution = this.numSearchT(this.formTravel.travelKbn)
                        this.travelData[this.editIndex - 1].company = this.numSearchR(this.formTravel.company)
                        this.travelData[this.editIndex - 1].remark = this.formTravel.textarea
                        this.travelData[this.editIndex - 1].term =  this.formTravel.term[0].replaceAll('-', '/').trim() + ' - ' + this.formTravel.term[1].replaceAll('-', '/').trim()
                        // this.travelData[this.editIndex - 1].term =  (this.formTravel.term[0].replaceAll('-', '/')).trim() + ' - ' + (this.formTravel.term[1].replaceAll('-', '/')).trim()
                        this.travelData[this.editIndex - 1].days =  this.travelCountDays

                        // 画像データ
                        // this.base64picturesT[this.editIndex - 1] = { picture:this.base64StringT
                        //                                             , contentType:this.content64TypeT
                        //                                         }
                        // DB用データ
                        this.travelData[this.editIndex - 1].startDate =  this.formTravel.term[0].replaceAll('-', '').trim()
                        this.travelData[this.editIndex - 1].endDate =  this.formTravel.term[1].replaceAll('-', '').trim()
                        // this.travelData[this.editIndex - 1].startDate =  (this.formTravel.term[0].replaceAll('-', '')).trim()
                        // this.travelData[this.editIndex - 1].endDate =  (this.formTravel.term[1].replaceAll('-', '')).trim()
                        this.travelData[this.editIndex - 1].monthKbn = ''
                        this.travelData[this.editIndex - 1].applyKbn = this.editList.applyKbn
                        this.travelData[this.editIndex - 1].travelKbn = this.editList.travelKbn
                        this.travelData[this.editIndex - 1].recipientKbn = this.nameSearchC(this.travelData[this.editIndex - 1].company)


                        // 宿泊編集内容
                        if(this.formTravel.travelKbn == '030') {
                            // DB用データ
                            this.travelData[this.editIndex - 1].travelWays = ''
                            this.travelData[this.editIndex - 1].station = this.formTravel.destination
                            this.travelData[this.editIndex - 1].destination = this.formTravel.destination
                            this.travelData[this.editIndex - 1].departure = ''
                            this.travelData[this.editIndex - 1].arrival = ''
                            this.travelData[this.editIndex - 1].wayKbn = ''

                        // 日当編集内容
                        }else if(this.formTravel.travelKbn == '040') {
                            // DB用データ
                            this.travelData[this.editIndex - 1].travelWays = ''
                            this.travelData[this.editIndex - 1].station = this.formTravel.destination
                            this.travelData[this.editIndex - 1].destination = this.formTravel.destination
                            this.travelData[this.editIndex - 1].departure = ''
                            this.travelData[this.editIndex - 1].arrival = ''
                            this.travelData[this.editIndex - 1].wayKbn = ''
                        // 交通費編集内容
                        }else {
                            // DB用データ
                            this.travelData[this.editIndex - 1].travelWays = this.formTravel.ways
                            this.travelData[this.editIndex - 1].station = this.formTravel.start + ' - ' + this.formTravel.end
                            this.travelData[this.editIndex - 1].destination = ''
                            this.travelData[this.editIndex - 1].departure = this.formTravel.start
                            this.travelData[this.editIndex - 1].arrival = this.formTravel.end
                            this.travelData[this.editIndex - 1].wayKbn = this.nameSearchW(this.travelData[this.editIndex - 1].travelWays)
                        }
                        if (this.base64StringT != ''){
                            this.teikiData[this.editIndex - 1].img64Str = this.base64StringT
                            this.teikiData[this.editIndex - 1].img64contentType = this.content64TypeT
                            saveImgTemporary(this.teikiData[this.editIndex - 1]).then(res => {
                                console.log('宿泊内容/日当内容', this.travelData)
                                this.base64StringT = '';
                                this.content64TypeT = '';
                                this.travelData[this.editIndex - 1].image = res.data;
                                this.editList.image = res.data;
                                this.formTravel.image = res.data;
                                sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                                sessionStorage.removeItem("020_travel_expenses_edit",JSON.stringify(this.transportationEditData))
                                // 交通費入力項目空
                                this.setEnptyTravel()
                                this.sumTravelAmount()
                            }).catch(error => {
                                    console.log('getInfo fail')
                            });
                        } else {
                            this.travelData[this.editIndex - 1].image = ''
                            sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                            sessionStorage.removeItem("020_travel_expenses_edit",JSON.stringify(this.transportationEditData))
                            // 交通費入力項目空
                            this.setEnptyTravel()
                            this.sumTravelAmount() 
                        }
                        // 交通費入力ポップアップ非表示
                        this.travelInputDialog = false
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    });
                }else if (this.activeName == 'other') {
                    this.$refs["formOther"].validate((valid) => {
                        if (valid) {
                        console.log('this.formOther.travelKbn', this.formOther.travelKbn)
                        this.otherRewriteData = JSON.parse(sessionStorage.getItem("050_other_expenses_edit"))
                        this.editIndex = this.otherRewriteData[0].index
                        // リスト用データ
                        this.otherData[this.editIndex - 1].amount = this.formOther.amount
                        this.otherData[this.editIndex - 1].otherAmount = Number(this.formOther.amount)
                        this.otherData[this.editIndex - 1].institution = this.numSearchO(this.formOther.travelKbn)
                        this.otherData[this.editIndex - 1].company = this.numSearchR(this.formOther.company)
                        this.otherData[this.editIndex - 1].remark = this.formOther.textarea
                        this.otherData[this.editIndex - 1].term =  this.formOther.term[0].replaceAll('-', '/').trim() + ' - ' + this.formOther.term[1].replaceAll('-', '/').trim()
                        // this.otherData[this.editIndex - 1].term =  (this.formOther.term[0].replaceAll('-', '/')).trim() + ' - ' + (this.formOther.term[1].replaceAll('-', '/')).trim()
                        this.otherData[this.editIndex - 1].days =  this.otherCountDays

                        // 画像データ
                        this.base64picturesO[this.editIndex - 1] = { picture:this.base64StringO
                                                                    , contentType:this.content64TypeO
                                                                }
                        // DB用データ
                        this.otherData[this.editIndex - 1].startDate =  this.formOther.term[0].replaceAll('-', '').trim()
                        this.otherData[this.editIndex - 1].endDate =  this.formOther.term[1].replaceAll('-', '').trim()
                        // this.otherData[this.editIndex - 1].startDate =  (this.formOther.term[0].replaceAll('-', '')).trim()
                        // this.otherData[this.editIndex - 1].endDate =  (this.formOther.term[1].replaceAll('-', '')).trim()
                        this.otherData[this.editIndex - 1].monthKbn = ''
                        this.otherData[this.editIndex - 1].applyKbn = this.editList.applyKbn
                        this.otherData[this.editIndex - 1].travelKbn = this.editList.travelKbn
                        this.otherData[this.editIndex - 1].recipientKbn = this.nameSearchC(this.otherData[this.editIndex - 1].company)
                        this.otherData[this.editIndex - 1].travelWays = ''
                        this.otherData[this.editIndex - 1].station = this.formOther.destination
                        this.otherData[this.editIndex - 1].destination = this.formOther.destination
                        this.otherData[this.editIndex - 1].departure = ''
                        this.otherData[this.editIndex - 1].arrival = ''
                        this.otherData[this.editIndex - 1].wayKbn = ''
                        if (this.base64StringO != ''){
                            this.otherData[this.editIndex - 1].img64Str = this.base64StringO
                            this.otherData[this.editIndex - 1].img64contentType = this.content64TypeO
                            saveImgTemporary(this.otherData[this.editIndex - 1]).then(res => {
                                console.log('その他経費', this.otherData)
                                this.base64StringO = '';
                                this.content64TypeO = '';
                                this.otherData[this.editIndex - 1].image = res.data;
                                this.editList.image = res.data;
                                this.formOther.image = res.data;
                                sessionStorage.setItem("050_other_expenses",JSON.stringify(this.otherData))
                                sessionStorage.removeItem("050_other_expenses_edit",JSON.stringify(this.otherEditData))
                                // その他経費入力項目空
                                this.setEnptyOther()
                                this.sumOtherAmount()
                            }).catch(error => {
                                console.log('getInfo fail')
                            });
                        } else {
                            this.otherData[this.editIndex - 1].image = ''
                            sessionStorage.setItem("050_other_expenses",JSON.stringify(this.otherData))
                            sessionStorage.removeItem("050_other_expenses_edit",JSON.stringify(this.otherEditData))
                            // その他経費入力項目空
                            this.setEnptyOther()
                            this.sumOtherAmount()
                        }
                        // その他経費入力ポップアップ非表示
                        this.otherInputDialog = false
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    });
                }
            },
            // リスト内データ単体削除
            deleteOne(row) {
                console.log('row', row)
                // 定期
                if(this.activeName == 'teiki') {
                    this.deleteTeikiData = row
                    this.teikiData.splice(this.deleteTeikiData.index - 1, 1);
                    this.deletefiles(this.base64pictures, this.deleteTeikiData.index - 1)
                    sessionStorage.setItem("010_teiki",JSON.stringify(this.teikiData))
                    console.log('this.travelData',this.travelData)
                    this.sumTeikiAmount()
                }else if (this.activeName == 'travel') {
                    this.deleteTravelData = row
                    this.travelData.splice(this.deleteTravelData.index - 1, 1);
                    this.deletefiles(this.base64picturesT, this.deleteTravelData.index - 1)
                    sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                    console.log('this.travelData',this.travelData)
                    this.sumTravelAmount()
                }else if (this.activeName == 'other') {
                    this.deleteOtherData = row
                    this.otherData.splice(this.deleteOtherData.index - 1, 1);
                    this.deletefiles(this.base64picturesO, this.deleteOtherData.index - 1)
                    sessionStorage.setItem("050_other_expenses",JSON.stringify(this.otherData))
                    console.log('this.otherData',this.otherData)
                    this.sumOtherAmount()
                }
            },
            // フロント操作の画像単体削除
            deletefiles(arr,index){
                for(let i=0;i<arr.length;i++){
                    if(i==index){
                    arr.splice(i,1);
                    }
                }
            },

            // 削除ボタン（フロント側全削除）
            allDelete() {
                // 定期全削除
                this.teikiData = []
                this.teikiEditData = JSON.parse(sessionStorage.getItem("010_teiki"))
                sessionStorage.removeItem("010_teiki",JSON.stringify(this.teikiData))
                sessionStorage.removeItem("010_edit",JSON.stringify(this.teikiEditData))
                // 旅費全削除
                this.travelData = []
                this.transportationEditData = JSON.parse(sessionStorage.getItem("020_travel_expenses"))
                sessionStorage.removeItem("020_travel_expenses",JSON.stringify(this.travelData))
                sessionStorage.removeItem("020_travel_expenses_edit",JSON.stringify(this.transportationEditData))
                // その他経費全削除
                this.otherData = []
                this.otherEditData = JSON.parse(sessionStorage.getItem("050_other_expenses"))
                sessionStorage.removeItem("050_other_expenses",JSON.stringify(this.otherData))
                sessionStorage.removeItem("050_other_expenses_edit",JSON.stringify(this.otherEditData))
            },
            // 参照ダイアログ
            onReference() {
                this.referenceDialogVisible = true
                this.referenceMonth = ''
            },
            // 参照年月検索
            searchRefMonth() {
                let refData = { companyCode: this.userData.companyCode
                              , requestUserId: this.userData.userId
                              , applyYm: this.referenceMonth.replaceAll('-', '')
                            }
                console.log('refData',refData )
                searchRefMonthData(refData).then(res =>{
                    console.log(res.data)
                    this.refDataList = res.data
                    console.log('this.refDataList', this.refDataList)
                    console.log('this.refDataList.length', this.refDataList.length)
                    if (this.changebyFlg == "1"){
                        this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                    }else if (this.changebyFlg == "2") {
                        this.reqNum = this.rowdata.requestNumber
                    }
                    
                    if(this.refDataList.length >= 1) {
                        this.allDelete()
                        for(let i = 0; i < this.refDataList.length;i++) {
                            console.log('this.refDataList.applyKbn', this.refDataList[i].applyKbn)
                            // 定期
                            if(this.refDataList[i].applyKbn == '010') {
                                let arr = { companyCode: this.refDataList[i].companyCode
                                          , applyYm: this.applyMonth.replaceAll('-', '')
                                          , requestNumber: this.reqNum
                                          , requestSubNumber: this.refDataList[i].requestSubNumber
                                          , requestUserId: this.refDataList[i].requestUserId
                                          , index: this.teikiData == null ? 1 : this.teikiData.length + 1
                                          , term: this.refDataList[i].startDate.substring(0,4) + '/' + this.refDataList[i].startDate.substring(4,6) + '/' + this.refDataList[i].startDate.substring(6,8) + ' - ' + this.refDataList[i].endDate.substring(0,4) + '/' + this.refDataList[i].endDate.substring(4,6) + '/' + this.refDataList[i].endDate.substring(6,8)
                                          , startDate: this.refDataList[i].startDate
                                          , endDate: this.refDataList[i].endDate
                                          , monthKbn: this.refDataList[i].monthKbn
                                          , month: this.refDataList[i].monthKbn + 'ヶ月'
                                          , days: ''
                                          , applyKbn: this.refDataList[i].applyKbn
                                          , travelKbn: this.refDataList[i].travelKbn
                                          , station: this.refDataList[i].departure + ' - ' + this.refDataList[i].arrival
                                          , departure: this.refDataList[i].departure
                                          , arrival: this.refDataList[i].arrival
                                          , wayKbn: ''
                                          , travelWays: ''
                                          , destination: ''
                                          , recipientKbn: this.refDataList[i].recipientKbn
                                          , institution: this.numSearchK(this.refDataList[i].travelKbn)
                                          , amount: this.refDataList[i].amount
                                          , teikiAmount: this.refDataList[i].amount
                                          , company: this.numSearchR(this.refDataList[i].recipientKbn)
                                          , remark: this.refDataList[i].remark
                                        //   , image: (this.refDataList[i].image == null) ? '' : this.refDataList[i].image
                                          , image: ''
                                          
                                        }
                                this.teikiData.push(arr);
                                console.log('参照_teiki',this.teikiData)
                                sessionStorage.setItem("010_teiki",JSON.stringify(this.teikiData))
                                this.totaltravelAmount = ''
                            }else if (this.refDataList[i].applyKbn == '020') {
                                // 宿泊/日当
                                if(this.refDataList[i].travelKbn == '030' || this.refDataList[i].travelKbn == '040') {
                                    let arr = { companyCode: this.refDataList[i].companyCode
                                              , applyYm: this.applyMonth.replaceAll('-', '')
                                              , requestNumber: this.reqNum
                                              , requestSubNumber: this.refDataList[i].requestSubNumber
                                              , requestUserId: this.refDataList[i].requestUserId
                                              , index: this.travelData == null ? 1 : this.travelData.length + 1
                                              , term: this.refDataList[i].startDate.substring(0,4) + '/' + this.refDataList[i].startDate.substring(4,6) + '/' + this.refDataList[i].startDate.substring(6,8) +' - ' + this.refDataList[i].endDate.substring(0,4) + '/' + this.refDataList[i].endDate.substring(4,6) + '/' + this.refDataList[i].endDate.substring(6,8)
                                              , startDate: this.refDataList[i].startDate
                                              , endDate: this.refDataList[i].endDate
                                              , monthKbn: ''
                                              , month: ''
                                              , days: this.refDataList[i].days
                                              , applyKbn: '020'
                                              , travelKbn: this.refDataList[i].travelKbn
                                              , station: this.refDataList[i].destination
                                              , departure: ''
                                              , arrival: ''
                                              , wayKbn: ''
                                              , travelWays: ''
                                              , destination: this.refDataList[i].destination
                                              , recipientKbn: this.refDataList[i].recipientKbn
                                              , days: this.refDataList[i].days
                                              , institution: this.numSearchT(this.refDataList[i].travelKbn)
                                              , amount: this.refDataList[i].amount
                                              , travelAmount: this.refDataList[i].amount
                                              , company: this.numSearchR(this.refDataList[i].recipientKbn)
                                              , remark: this.refDataList[i].remark
                                            //   , image: (this.refDataList[i].image == null) ? '' : this.refDataList[i].image
                                              , image: ''
                                            }
                                    this.travelData.push(arr);
                                    console.log('参照_travel',this.travelData)
                                    sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                                    this.totalTeikiAmount = ''
                                // 旅費
                                }else {
                                    console.log('this.refDataList[i].wayKbn',this.refDataList[i].wayKbn)
                                    let arr = { companyCode: this.refDataList[i].companyCode
                                              , applyYm: this.applyMonth.replaceAll('-', '')
                                              , requestNumber: this.reqNum
                                              , requestSubNumber: this.refDataList[i].requestSubNumber
                                              , requestUserId: this.refDataList[i].requestUserId
                                              , index: this.travelData == null ? 1 : this.travelData.length + 1
                                              , term: this.refDataList[i].startDate.substring(0,4) + '/' + this.refDataList[i].startDate.substring(4,6) + '/' + this.refDataList[i].startDate.substring(6,8) +' - ' + this.refDataList[i].endDate.substring(0,4) + '/' + this.refDataList[i].endDate.substring(4,6) + '/' + this.refDataList[i].endDate.substring(6,8)
                                              , startDate: this.refDataList[i].startDate
                                              , endDate: this.refDataList[i].endDate
                                              , monthKbn: ''
                                              , month: ''
                                              , days: this.refDataList[i].days
                                              , applyKbn: '020'
                                              , travelKbn: this.refDataList[i].travelKbn
                                              , station: this.refDataList[i].departure + ' - ' + this.refDataList[i].arrival
                                              , departure: this.refDataList[i].departure
                                              , arrival: this.refDataList[i].arrival
                                              , wayKbn: this.refDataList[i].wayKbn
                                              , travelWays: this.numSearchW(this.refDataList[i].wayKbn)
                                              , destination: ''
                                              , recipientKbn: this.refDataList[i].recipientKbn
                                              , institution: this.numSearchT(this.refDataList[i].travelKbn)
                                              , amount: this.refDataList[i].amount
                                              , travelAmount: this.refDataList[i].amount
                                              , company: this.numSearchR(this.refDataList[i].recipientKbn)
                                              , remark: this.refDataList[i].remark
                                            //   , image: (this.refDataList[i].image == null) ? '' : this.refDataList[i].image
                                              , image: ''
                                            }
                                    this.travelData.push(arr);
                                    console.log('参照_travel',this.travelData)
                                    sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                                    this.totalTeikiAmount = ''
                                }
                            }else if (this.refDataList[i].applyKbn == '030') {
                                // その他経費
                                let arr = { companyCode: this.refDataList[i].companyCode
                                            , applyYm: this.applyMonth.replaceAll('-', '')
                                            , requestNumber: this.reqNum
                                            , requestSubNumber: this.refDataList[i].requestSubNumber
                                            , requestUserId: this.refDataList[i].requestUserId
                                            , index: this.otherData == null ? 1 : this.otherData.length + 1
                                            , term: this.refDataList[i].startDate.substring(0,4) + '/' + this.refDataList[i].startDate.substring(4,6) + '/' + this.refDataList[i].startDate.substring(6,8) +' - ' + this.refDataList[i].endDate.substring(0,4) + '/' + this.refDataList[i].endDate.substring(4,6) + '/' + this.refDataList[i].endDate.substring(6,8)
                                            , startDate: this.refDataList[i].startDate
                                            , endDate: this.refDataList[i].endDate
                                            , monthKbn: ''
                                            , month: ''
                                            , days: this.refDataList[i].days
                                            , applyKbn: '030'
                                            , travelKbn: this.refDataList[i].travelKbn
                                            , station: this.refDataList[i].destination
                                            , departure: ''
                                            , arrival: ''
                                            , wayKbn: ''
                                            , travelWays: ''
                                            , destination: this.refDataList[i].destination
                                            , recipientKbn: this.refDataList[i].recipientKbn
                                            , days: this.refDataList[i].days
                                            , institution: this.numSearchO(this.refDataList[i].travelKbn)
                                            , amount: this.refDataList[i].amount
                                            , otherAmount: this.refDataList[i].amount
                                            , company: this.numSearchR(this.refDataList[i].recipientKbn)
                                            , remark: this.refDataList[i].remark
                                            // , image: (this.refDataList[i].image == null) ? '' : this.refDataList[i].image
                                            , image: ''
                                        }
                                this.otherData.push(arr);
                                console.log('参照_other',this.otherData)
                                sessionStorage.setItem("050_other_expenses",JSON.stringify(this.otherData))
                                this.totalOtherAmount = ''
                            }
                        }
                        this.referenceDialogVisible = false
                        this.modifyFlgsave = true
                        this.reqFlg = true
                        // 定期・旅費・その他の合計
                        if(this.teikiData.length >= 1  && this.travelData.length == 0 && this.otherData.length == 0) {
                            this.sumTeikiAmount()
                        }else if(this.teikiData.length == 0 && this.travelData.length >= 1 && this.otherData.length == 0) {
                            this.sumTravelAmount()
                        }else if(this.teikiData.length == 0 && this.travelData.length == 0 && this.otherData.length >= 1) {
                            this.sumOtherAmount()
                        }else if(this.teikiData.length >= 1 && this.travelData.length >= 1 && this.otherData.length == 0) {
                            this.sumTeikiAmount()
                            this.sumTravelAmount()
                        }else if(this.teikiData.length >= 1 && this.otherData.length >= 1 && this.travelData.length == 0) {
                            this.sumTeikiAmount()
                            this.sumOtherAmount()
                        }else if(this.travelData.length >= 1 && this.otherData.length >= 1 && this.teikiData.length == 0) {
                            this.sumTravelAmount()
                            this.sumOtherAmount()
                        }else if(this.teikiData.length >= 1  && this.travelData.length >= 1 && this.otherData.length >= 1) {
                            this.sumTeikiAmount()
                            this.sumTravelAmount()
                            this.sumOtherAmount()
                        }
                    }else{
                        Message({
                            message: '選択年月のデータは存在しません。',
                            type: 'error',
                            duration: 4000
                        })
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            //差戻画面のポップアップ
            remandDialog(){
                this.$refs.onRemandDialog.remandDialogOpen()
            },
            //取戻画面のポップアップ
            OpenGetBackDialog(){
                this.$refs.ongetBackDoalog.applyopende()
            },
            // 定期入力のポップアップ
            addBtn() {
                this.teikiInputDialog = true
                this.teikiKbnOff = false
                this.finalizeFlg = true
                this.EditFinalizeFlg = false
            },
            // 旅費入力のポップアップ
            TravelAddBtn() {
                this.travelInputDialog = true
                this.travelKbnOff = false
                this.finalizeFlg = true
                this.EditFinalizeFlg = false
            },
            // その他経費入力のポップアップ
            async OtherAddBtn() {
                await this.getOthers()
                console.log('this.otherKbnList', this.otherKbnList)
                this.formOther.travelKbn = this.otherKbnList[0].categoryKbn
                console.log('this.otherKbnList.categoryKbn', this.otherKbnList[0].categoryKbn)
                console.log('this.otherKbnList.categoryKbnName', this.otherKbnList[0].categoryKbnName)
                this.otherInputDialog = true
                this.travelKbnOff = false
                this.finalizeFlg = true
                this.EditFinalizeFlg = false
            },
            // 削除のポップアップ
            onDeleteConfirmClick() {
                this.deleteConfirmDialogVisible = true
            },
            // 削除処理
            deleteApplication(){
                    this.deleteReq()
                    this.allDelete()
                    this.deleteAllW()
                    this.deleteTransportationExpenses()
                Message({
                    message: '申請を削除しました。',
                    type: 'success',
                    duration: 4000
                })
                this.deleteConfirmDialogVisible = false
                setTimeout(()=>{
                    this.$router.go({path: this.$router.currentRoute.path, force: true})
                },200)
            },
            //交通費申請内容テーブルのレコード削除
            deleteTransportationExpenses(){
                let deleteData = { companyCode: this.rowdata.companyCode
                                 , requestNumber: this.rowdata.requestNumber
                                 , requestUserId: this.rowdata.requestUserId
                                 }
                deleteTransportationExpenses(deleteData).then(res =>{
                    console.log(res.data)

                }).catch(error => {
                    console.log(error)
                })
            },
            //申請テーブルのレコード削除
            deleteReq(){
                let datadeletereq = { companyCode: this.rowdata.companyCode
                                    , requestNumber: this.rowdata.requestNumber
                                };  
                deleteRequest(datadeletereq)
                    .then((res) => {
                    console.log(res);
                })
                .catch((error) => {});
            },
            //ワークフローテーブルのレコード削除
            deleteAllW(){
                let data = { companyCode: this.rowdata.companyCode
                           , requestNumber: this.rowdata.requestNumber
                        }
                deleteAllWf(data).then(res =>{
                    console.log(res.data)
                }).catch(error => {
                    console.log(error)
                })
            },
            // 定期・旅費・その他経費タブ切り替え
            handleClick(tab, event) {
                console.log(tab.name, event);
                this.statusSetBtn()
            },
            // 入力ボタン
            openDetailBox() {
                console.log('applyMonth', this.applyMonth)
                if(this.applyMonth !== '') {
                    this.inputOff = false
                    this.travelKbnOff = false
                    this.selectedMonth = true
                    this.refFlg = true
                    this.teikiAddFlg = true
                    this.travelAddFlg = true
                    this.otherAddFlg = true
                    this.upFlg = true
                    this.enterFlg = false
                }else{
                Message({
                        message: '申請する年月を選択してください。',
                        type: 'error',
                        duration: 4 * 1000
                        })
                }
            },
            // 定期入力項目空
            setEnptyTeiki() {
                this.formTeiki.teikiKbn = ''
                this.formTeiki.chosenDays = ''
                this.formTeiki.month = ''
                this.formTeiki.input1 = ''
                this.formTeiki.input2 = ''
                this.formTeiki.amount = ''
                this.formTeiki.company = ''
                this.formTeiki.textarea = ''
                this.fileList = []
                this.base64String = ''
                this.pic64Data = ''
                this.content64Type = ''
            },
            // 旅費入力項目空
            setEnptyTravel() {
                this.formTravel.travelKbn = ''
                this.formTravel.term = ''
                this.travelCountDays = ''
                this.formTravel.destination = ''
                this.formTravel.start = ''
                this.formTravel.end = ''
                this.formTravel.ways = '片道'
                this.formTravel.amount = ''
                this.formTravel.company = ''
                this.formTravel.textarea = ''
                this.fileList = []
                this.base64StringT = ''
                this.pic64DataT = ''
                this.content64TypeT = ''
            },
            // その他経費入力項目空
            setEnptyOther() {
                this.formOther.travelKbn = ''
                this.formOther.term = ''
                this.otherCountDays = ''
                this.formOther.destination = ''
                this.formOther.amount = ''
                this.formOther.company = ''
                this.formOther.textarea = ''
                this.fileList = []
                this.base64StringO = ''
                this.pic64DataO = ''
                this.content64TypeO = ''
            },
            // 上へボタン
            setNew() {
                this.selectedMonth = false
                this.inputOff = true
                this.travelKbnOff = true
                this.teikiAddFlg = false
                this.travelAddFlg = false
                this.otherAddFlg = false
                this.enterFlg = true
                this.refFlg = false
                this.upFlg = false
                this.modifyFlgsave = false
                this.reqFlg = false
                // 申請月
                this.applyMonth = ''
                // 定期入力項目空
                if(this.teikiData.length >= 1){
                    this.totalTeikiAmount = ''
                }
                // 旅費入力項目空
                if(this.travelData.length >= 1){
                    this.totaltravelAmount = ''
                }
                // その他経費入力項目空
                if(this.otherData.length >= 1){
                    this.totalOtherAmount = ''
                }
                // 削除ボタン（フロント側全削除）
                this.allDelete()
            },
            // 画像アップロード
            async imageLoading(file,fileList){
                // alert(file.size)
                // alert(file.size / 1024 / 1024)
                console.log('file', file)
                console.log('fileList', fileList)
                const fileSizeLimit = await file.size / 1024 / 1024 < 3;
                if(!fileSizeLimit){
                     Message({
                        message: '3MB以内のイメージをアップロードしてください。',
                        type: 'error',
                        duration: 3000
                        
                    })
                    this.fileList = []
                } else {
                    const imageJudge = file.raw.type.substring(0,5) == 'image'
                    if(imageJudge){
                        let pictureRaw = await window.URL.createObjectURL(file.raw)
                        this.picData = await this.changeImage(pictureRaw)
                        console.log('this.picData', this.picData)
                        this.fileList = fileList;
                    }else{
                        this.fileList = []
                    }
                }
            },
            imageGet(data){
                for(let i = 0; i < this.data.length;i++){
                    data[i].image
                }
            },
            async changeImage(data){
                if(data){
                    const response = await fetch(data)
                // 確定ボタン複数回押下対応
                    let contentTypeArr = response.headers.get('content-type').split(";")
                    let contentType = contentTypeArr[0]
                    const arrayBuffer = await response.arrayBuffer()
                    const APPLY_MAX = 1024
                    let encodedStr = ''
                    for (var i = 0; i < arrayBuffer.byteLength; i += APPLY_MAX) {
                        encodedStr += String.fromCharCode.apply(
                            null,
                            new Uint8Array(arrayBuffer.slice(i, i + APPLY_MAX))
                        )
                    }
                    if(this.activeName == 'teiki') {
                        this.base64String = btoa(encodedStr)
                        this.pic64Data = 'data:'+ contentType + ';base64,' + this.base64String
                        this.content64Type = contentType.slice(6)
                        console.log('this.content64Type', this.content64Type)
                        console.log('this.content64Type', this.content64Type)
                    }else if(this.activeName == 'travel') {
                        this.base64StringT = btoa(encodedStr)
                        this.pic64DataT = 'data:'+ contentType + ';base64,' + this.base64StringT
                        this.content64TypeT = contentType.slice(6)
                    }else if(this.activeName == 'other') {
                        this.base64StringO = btoa(encodedStr)
                        this.pic64DataO = 'data:'+ contentType + ';base64,' + this.base64StringO
                        this.content64TypeO = contentType.slice(6)
                    }
                }
            },
            // DB保存データ作成
            prepareAllData(){
                this.saveData = []
                console.log('this.teikiData', this.teikiData)
                    if(this.teikiData !== null) {
                        for(let i = 0; i < this.teikiData.length; i++) {
                            this.saveData.push(this.teikiData[i])
                        }
                    }
                    console.log('this.travelData', this.travelData)
                    if(this.travelData !== null) {
                        for(let i = 0; i < this.travelData.length; i++) {
                            this.saveData.push(this.travelData[i])
                        }
                    }
                    if(this.otherData !== null) {
                        for(let i = 0; i < this.otherData.length; i++) {
                            this.saveData.push(this.otherData[i])
                        }
                    }
                    console.log('total_data', this.saveData)
                    let emptyPics = []
                    for(let i = 0; i < this.saveData.length; i++) {
                        this.saveData[i].requestSubNumber = i + 1
                        console.log('total_data', this.saveData[i].applyKbn)
                        if(this.saveData[i].applyKbn == '010'){
                            let bool = true
                            if(this.saveData[i].image === ''){
                                bool = false
                            }
                            emptyPics.push(bool)
                        }
                    }
                    console.log('emptyPics', emptyPics)
                    let count = emptyPics.filter(emptyPics => emptyPics == false)
                    this.countEmpty = count.length
                    console.log('this.countEmpty', this.countEmpty)
            },
            // 保存
            async saveBtn() {
                // 新規保存
                console.log('this.teikiData', this.teikiData)
                if(this.teikiData.length == 0  && this.travelData.length == 0 && this.otherData.length == 0) {
                    Message({
                            message: 'リスト内にデータが無いため保存できません。',
                            type: 'error',
                            duration: 4000
                        })
                }else {
                    if (this.changebyFlg == "1") {
                        this.prepareAllData()
                        // if(this.countEmpty >= 1){
                        //     Message({
                        //         message: '定期券申請には画像添付は必須です。',
                        //         type: 'error',
                        //         duration: 4000
                        //     })
                        // }else {
                            console.log('this.saveData', this.saveData)
                            insertTransportationExpenses(this.saveData).then(res =>{
                            console.log(res)
                            this.reqStatus = '001'
                            //申請内容テーブルの保存処理
                            this.requestSaveData()
                            this.requestSave(this.requestData)
                            Message({
                                message: '交通費申請を保存しました。',
                                type: 'success',
                                duration: 4000
                            })
                            this.allDelete()
                            setTimeout(()=>{
                                this.$router.go({path: this.$router.currentRoute.path, force: true})
                            },200)
                            }).catch(error => {
                                console.log(error)
                            })
                        // }
                    // 保存→再保存
                    } else if (this.changebyFlg == "2") {
                        let list = []
                        let data = { companyCode: this.rowdata.companyCode
                                , applyYm: ''
                                , requestNumber: this.rowdata.requestNumber
                                , requestSubNumber: ''
                                , requestUserId: this.rowdata.requestUserId
                                , startDate: ''
                                , endDate: ''
                                , monthKbn: ''
                                , days: ''
                                , applyKbn: ''
                                , travelKbn: ''
                                , departure: ''
                                , arrival: ''
                                , wayKbn: ''
                                , destination: ''
                                , amount: ''
                                , teikiAmount: ''
                                , travelAmount: ''
                                , stayAmount: ''
                                , workAmount: ''
                                , recipientKbn: ''
                                , remark: ''
                                , image: ''
                                }
                        list.push(data)
                        if(this.teikiData !== null) {
                            console.log('this.teikiData', this.teikiData)
                            for(let i = 0; i < this.teikiData.length; i++) {
                                list.push(this.teikiData[i])
                            }
                        }
                        if(this.travelData !== null) {
                            for(let i = 0; i < this.travelData.length; i++) {
                                list.push(this.travelData[i])
                            }
                        }
                        if(this.otherData !== null) {
                            for(let i = 0; i < this.otherData.length; i++) {
                                list.push(this.otherData[i])
                            }
                        }

                        console.log('list', list)
                        let emptyPics = []
                        for(let i = 0; i < list.length; i++) {
                            console.log(list[i])
                            console.log('i', i)
                            if (i == 0) {
                                list[i].requestSubNumber = ''
                            }else{
                                console.log('', list[i].requestSubNumber)
                                this.subNum += 1
                                list[i].requestSubNumber = this.subNum
                            }
                            // 添付画像の有無
                            let bool = true
                            if(list[i].image === ''){
                                bool = false
                            }
                            emptyPics.push(bool)
                            // console.log('list[i].applyKbn', list[i].applyKbn)
                            // if(list[i].applyKbn == "010"){
                            //     // 添付画像の有無
                            //     let bool = true
                            //     if(list[i].img64Str === ''){
                            //         bool = false
                            //     }
                            //     emptyPics.push(bool)
                            // }
                            
                        }
                        // console.log('emptyPics', emptyPics)
                        // let count = emptyPics.filter(emptyPics => emptyPics == false)
                        // this.countEmpty = count.length
                        // console.log('this.countEmpty', this.countEmpty)
                        // console.log('total_data', list)
                        // if(this.countEmpty >= 1){
                        //     Message({
                        //         message: '定期券申請には画像添付は必須です。',
                        //         type: 'error',
                        //         duration: 4000
                        //     })
                        // }else {
                            console.log('list', list)
                            updateTransportationExpenses(list).then(res =>{
                            console.log(res)
                            //ステータス区分：保存
                            this.reqStatus = '001'
                            //
                            //申請内容テーブルの保存処理
                            this.requestSaveData()
                            this.requestSave(this.requestData)
                            Message({
                                message: '交通費申請の内容を更新しました。',
                                type: 'success',
                                duration: 4000
                            })
                            this.allDelete()
                            setTimeout(()=>{
                                this.$router.go({path: this.$router.currentRoute.path, force: true})
                            },200)
                            }).catch(error => {
                            })
                        // }
                    }
                }
            },
            // 申請
            async submitBtn() {
                if (this.changebyFlg == "1") {
                    console.log('申請this.saveData',this.saveData)
                    insertTransportationExpenses(this.saveData).then(res =>{
                    console.log(res)
                    Message({
                        message: '交通費申請を申請しました。',
                        type: 'success',
                        duration: 4000
                    })
                    this.allDelete()
                    setTimeout(()=>{
                        this.$router.go({path: this.$router.currentRoute.path, force: true})
                    },200)
                    }).catch(error => {
                        console.log(error)
                    })
                // 保存→申請
                } else if (this.changebyFlg == "2") {
                    let list = []
                    let data = { companyCode: this.rowdata.companyCode
                               , applyYm: ''
                               , requestNumber: this.rowdata.requestNumber
                               , requestSubNumber: ''
                               , requestUserId: this.rowdata.requestUserId
                               , startDate: ''
                               , endDate: ''
                               , monthKbn: ''
                               , days: ''
                               , applyKbn: ''
                               , travelKbn: ''
                               , departure: ''
                               , arrival: ''
                               , wayKbn: ''
                               , destination: ''
                               , amount: ''
                               , teikiAmount: ''
                               , travelAmount: ''
                               , stayAmount: ''
                               , workAmount: ''
                               , recipientKbn: ''
                               , remark: ''
                               , image: ''
                            }
                    list.push(data)
                    if(this.teikiData !== null) {
                        console.log('this.teikiData', this.teikiData)
                         for(let i = 0; i < this.teikiData.length; i++) {
                            list.push(this.teikiData[i])
                        }
                    }
                    if(this.travelData !== null) {
                        for(let i = 0; i < this.travelData.length; i++) {
                            list.push(this.travelData[i])
                        }
                    }
                    if(this.otherData !== null) {
                        for(let i = 0; i < this.otherData.length; i++) {
                            list.push(this.otherData[i])
                        }
                    }

                    console.log('total_data', list)
                    for(let i = 0; i < list.length; i++) {
                        console.log(list[i])
                        console.log('i', i)
                        if (i == 0) {
                            list[i].requestSubNumber = ''
                        }else{
                            console.log('', list[i].requestSubNumber)
                            this.subNum += 1
                            list[i].requestSubNumber = this.subNum
                        }
                    }
                    console.log('total_redata', list)
                    updateTransportationExpenses(list).then(res =>{
                        console.log(res)
                        Message({
                            message: '交通費申請を申請しました。',
                            type: 'success',
                            duration: 10000
                        })
                    this.allDelete()
                    setTimeout(()=>{
                        this.$router.go({path: this.$router.currentRoute.path, force: true})
                    },200)
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            //申請テーブルに格納するデータ（保存、申請）
            requestSaveData(){
                if(this.changebyFlg == '1') {

                }else if(this.changebyFlg == '2') {
                    this.reqNum = this.rowdata.requestNumber
                    this.requestOverview = this.rowdata.requestOverview
                }
                if(this.applyMonth.length === 7) {
                        this.plusApplyMonth = this.applyMonth.replaceAll('-', '')
                }else{
                    this.plusApplyMonth = this.applyMonth
                }           
                if(this.saveData) {
                    console.log('this.saveData', this.saveData)
                    console.log('this.saveData.length', this.saveData.length)
                    console.log('this.applyMonth', this.applyMonth)

                    let teikiNum = 0
                    let travelNum = 0
                    let otherNum = 0
                    for(let i = 0; i < this.saveData.length;i++){
                        if(this.saveData[i].applyKbn == '010') {
                            teikiNum++
                        }else if(this.saveData[i].applyKbn == '020'){
                            travelNum++
                        }else if(this.saveData[i].applyKbn == '030'){
                            otherNum++
                        }
                    }
                    if(teikiNum >= 1 && travelNum == 0 && otherNum == 0){
                        this.requestOverview = '月末精算申請（定期）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(teikiNum == 0 && travelNum >= 1 && otherNum == 0){
                        this.requestOverview = '月末精算申請（旅費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(teikiNum == 0 && travelNum == 0 && otherNum >= 1){
                        this.requestOverview = '月末精算申請（その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(teikiNum >= 1 && travelNum >= 1 && otherNum == 0){
                        this.requestOverview = '月末精算申請（定期・旅費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(teikiNum >= 1 && travelNum == 0 && otherNum >= 1){
                        this.requestOverview = '月末精算申請（定期・その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(teikiNum == 0 && travelNum >= 1 && otherNum >= 1){
                        this.requestOverview = '月末精算申請（旅費・その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(teikiNum >= 1 && travelNum >= 1 && otherNum >= 1){
                        this.requestOverview = '月末精算申請（定期・旅費・その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }
                }else{
                    if(this.teikiData.length >= 1 && this.travelData.length == 0  && this.otherData.length == 0){
                        this.requestOverview = '月末精算申請（定期）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(this.teikiData.length == 0 && this.travelData.length >= 1  && this.otherData.length == 0){
                        this.requestOverview = '月末精算申請（旅費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(this.teikiData.length == 0 && this.travelData.length == 0  && this.otherData.length >= 1){
                        this.requestOverview = '月末精算申請（その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(this.teikiData.length >= 1 && this.travelData.length >= 1 && this.otherData.length == 0){
                        this.requestOverview = '月末精算申請（定期・旅費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(this.teikiData.length >= 1 && this.travelData.length == 0 && this.otherData.length >= 1){
                        this.requestOverview = '月末精算申請（定期・その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(this.teikiData.length == 0 && this.travelData.length >= 1 && this.otherData.length >= 1){
                        this.requestOverview = '月末精算申請（旅費・その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }else if(this.teikiData.length >= 1 && this.travelData.length >= 1 && this.otherData.length >= 1){
                        this.requestOverview = '月末精算申請（定期・旅費・その他経費）' + this.plusApplyMonth.substring(0,4) + '年' + this.plusApplyMonth.substring(4,6) + '月分'
                    }
                }
                
                this.requestData = { companyCode: this.userData.companyCode
                                   , requestNumber: this.reqNum
                                   , requestUserId :this.userData.userId
                                   , requestDate: this.yMd
                                   , requestTypeKbn: '031'
                                   , requestOverview: this.requestOverview
                                   , requestStatusKbn: this.reqStatus
                                }
                                console.log('this.requestData', this.requestData)
            },
            requestSave(data){
                console.log(data)
                insertLeaveRequest(data).then(res => {
                console.log(res)
                }).catch(error => {
                })
            },
            handlePictureCardPreview(file) {
                if(this.activeName == 'teiki') {
                    this.picDialogVisible = true;
                }else if (this.activeName == 'travel') {
                    console.log('this.formTravel.travelKbn', this.formTravel.travelKbn)
                    this.picDialogVisibleT = true;
                }else if (this.activeName == 'other') {
                    this.picDialogVisibleO = true;
                }
                
            },
            // 選択画像削除ボタン
            async removeFunction(file) {
                console.log(file)
                this.picSize = ''
                this.picData = ''
                this.fileList = await []
                this.base64String = ''
                this.pic64Data = ''
                this.content64Type = ''
                this.pic64DataT = ''
                this.content64TypeT = ''
                this.base64StringT = ''
                this.pic64DataO = ''
                this.content64TypeO = ''
                this.base64StringO = ''
            },
            // 詳細ボタン押下後の表示
            setDetailData() {
                let KeyData = { companyCode: this.rowdata.companyCode
                              , requestUserId: this.rowdata.requestUserId
                              , requestNumber: this.rowdata.requestNumber
                            }
                console.log(KeyData)
                getTransportationExpenses(KeyData).then(res => {
                    console.log(res.data)
                    this.detailList = res.data
                    console.log('this.detailList', this.detailList)
                    this.reqNumber = this.detailList[0].requestNumber
                    this.applyMonth = this.detailList[0].applyYm.substring(0,4) + '-' + this.detailList[0].applyYm.substring(4,6)
                    console.log('this.applyMonth', this.applyMonth)
                    for(let i = 0; i < this.detailList.length;i++) {
                        console.log('detailList', this.detailList)
                        console.log('applyKbn', this.detailList[i].applyKbn)  
                        console.log('travelKbn', this.detailList[i].travelKbn)            
                        // 定期
                        if(this.detailList[i].applyKbn == '010') {
                            let arr = { companyCode: this.detailList[i].companyCode
                                    , applyYm: this.detailList[i].applyYm
                                    , requestNumber: this.detailList[i].requestNumber
                                    , requestSubNumber: this.detailList[i].requestSubNumber
                                    , requestUserId: this.detailList[i].requestUserId
                                    , index: this.teikiData.length + 1
                                    , term: this.detailList[i].startDate.substring(0,4) + '/' + this.detailList[i].startDate.substring(4,6) + '/' + this.detailList[i].startDate.substring(6,8) + ' - ' + this.detailList[i].endDate.substring(0,4) + '/' + this.detailList[i].endDate.substring(4,6) + '/' + this.detailList[i].endDate.substring(6,8)
                                    , startDate: this.detailList[i].startDate
                                    , endDate: this.detailList[i].endDate
                                    , monthKbn: this.detailList[i].monthKbn
                                    , month: this.detailList[i].monthKbn + 'ヶ月'
                                    , days: ''
                                    , applyKbn: this.detailList[i].applyKbn
                                    , travelKbn: this.detailList[i].travelKbn
                                    , station: this.detailList[i].departure + ' - ' + this.detailList[i].arrival
                                    , departure: this.detailList[i].departure
                                    , arrival: this.detailList[i].arrival
                                    , wayKbn: ''
                                    , travelWays: ''
                                    , destination: ''
                                    , recipientKbn: this.detailList[i].recipientKbn
                                    , institution: this.numSearchK(this.detailList[i].travelKbn)
                                    , amount: this.detailList[i].amount
                                    , teikiAmount: this.detailList[i].amount
                                    , company: this.numSearchR(this.detailList[i].recipientKbn)
                                    , remark: this.detailList[i].remark
                                    , image: (this.detailList[i].image == null) ? '' : this.detailList[i].image
                                }
                            this.teikiData.push(arr);
                            sessionStorage.setItem("010_teiki",JSON.stringify(this.teikiData))
                            this.sumTeikiAmount()
                        }else if(this.detailList[i].applyKbn == '020'){
                            console.log('travelKbn', this.detailList[i].travelKbn)
                            // 宿泊/日当
                            if(this.detailList[i].travelKbn == '030' || this.detailList[i].travelKbn == '040') {
                            console.log('宿泊/日当', this.detailList[i])
                            let arr = { companyCode: this.detailList[i].companyCode
                                      , applyYm: this.detailList[i].applyYm
                                      , requestNumber: this.detailList[i].requestNumber
                                      , requestSubNumber: this.detailList[i].requestSubNumber
                                      , requestUserId: this.detailList[i].requestUserId
                                      , index: this.travelData.length + 1
                                      , term: this.detailList[i].startDate.substring(0,4) + '/' + this.detailList[i].startDate.substring(4,6) + '/' + this.detailList[i].startDate.substring(6,8) +' - ' + this.detailList[i].endDate.substring(0,4) + '/' + this.detailList[i].endDate.substring(4,6) + '/' + this.detailList[i].endDate.substring(6,8)
                                      , startDate: this.detailList[i].startDate
                                      , endDate: this.detailList[i].endDate
                                      , monthKbn: ''
                                      , month: ''
                                      , days: this.detailList[i].days
                                      , applyKbn: '020'
                                      , travelKbn: this.detailList[i].travelKbn
                                      , station: this.detailList[i].destination
                                      , departure: ''
                                      , arrival: ''
                                      , wayKbn: ''
                                      , travelWays: ''
                                      , destination: this.detailList[i].destination
                                      , recipientKbn: this.detailList[i].recipientKbn
                                      , remark: this.detailList[i].remark
                                      , institution: this.numSearchT(this.detailList[i].travelKbn)
                                      , amount: this.detailList[i].amount
                                      , travelAmount: this.detailList[i].amount
                                      , company: this.numSearchR(this.detailList[i].recipientKbn)
                                      , remark: this.detailList[i].remark
                                      , image: (this.detailList[i].image == null) ? '' : this.detailList[i].image 
                                    }
                            this.travelData.push(arr);
                            sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                            this.sumTravelAmount()
                            // // 交通費
                            }else if(this.detailList[i].travelKbn == '010' || this.detailList[i].travelKbn == '011' || this.detailList[i].travelKbn == '012' || this.detailList[i].travelKbn == '020' || this.detailList[i].travelKbn == '021' || this.detailList[i].travelKbn == '022') {
                                console.log('交通費', this.detailList[i])
                                let arr = { companyCode: this.detailList[i].companyCode
                                          , applyYm: this.detailList[i].applyYm
                                          , requestNumber: this.detailList[i].requestNumber
                                          , requestSubNumber: this.detailList[i].requestSubNumber
                                          , requestUserId: this.detailList[i].requestUserId
                                          , index: this.travelData.length + 1
                                          , term: this.detailList[i].startDate.substring(0,4) + '/' + this.detailList[i].startDate.substring(4,6) + '/' + this.detailList[i].startDate.substring(6,8) + ' - ' + this.detailList[i].endDate.substring(0,4) + '/' + this.detailList[i].endDate.substring(4,6) + '/' + this.detailList[i].endDate.substring(6,8)
                                          , startDate: this.detailList[i].startDate
                                          , endDate: this.detailList[i].endDate
                                          , monthKbn: ''
                                          , month: ''
                                          , days: this.detailList[i].days
                                          , applyKbn: '020'
                                          , travelKbn: this.detailList[i].travelKbn
                                          , station: this.detailList[i].departure + ' - ' + this.detailList[i].arrival
                                          , departure: this.detailList[i].departure
                                          , arrival: this.detailList[i].arrival
                                          , wayKbn: this.detailList[i].wayKbn
                                          , travelWays: this.numSearchW(this.detailList[i].wayKbn)
                                          , destination: ''
                                          , recipientKbn: this.detailList[i].recipientKbn
                                          , institution: this.numSearchT(this.detailList[i].travelKbn)
                                          , amount: this.detailList[i].amount
                                          , travelAmount: this.detailList[i].amount
                                          , company: this.numSearchR(this.detailList[i].recipientKbn)
                                          , remark: this.detailList[i].remark
                                          , image: (this.detailList[i].image == null) ? '' : this.detailList[i].image 
                                        }
                                this.travelData.push(arr);
                                sessionStorage.setItem("020_travel_expenses",JSON.stringify(this.travelData))
                                this.sumTravelAmount()
                            }
                        }else if(this.detailList[i].applyKbn == '030'){
                            // その他
                            console.log('その他', this.detailList[i])
                            let arr = { companyCode: this.detailList[i].companyCode
                                      , applyYm: this.detailList[i].applyYm
                                      , requestNumber: this.detailList[i].requestNumber
                                      , requestSubNumber: this.detailList[i].requestSubNumber
                                      , requestUserId: this.detailList[i].requestUserId
                                      , index: this.otherData.length + 1
                                      , term: this.detailList[i].startDate.substring(0,4) + '/' + this.detailList[i].startDate.substring(4,6) + '/' + this.detailList[i].startDate.substring(6,8) +' - ' + this.detailList[i].endDate.substring(0,4) + '/' + this.detailList[i].endDate.substring(4,6) + '/' + this.detailList[i].endDate.substring(6,8)
                                      , startDate: this.detailList[i].startDate
                                      , endDate: this.detailList[i].endDate
                                      , monthKbn: ''
                                      , month: ''
                                      , days: this.detailList[i].days
                                      , applyKbn: '030'
                                      , travelKbn: this.detailList[i].travelKbn
                                      , station: this.detailList[i].destination
                                      , departure: ''
                                      , arrival: ''
                                      , wayKbn: ''
                                      , travelWays: ''
                                      , destination: this.detailList[i].destination
                                      , recipientKbn: this.detailList[i].recipientKbn
                                      , remark: this.detailList[i].remark
                                      , institution: this.numSearchO(this.detailList[i].travelKbn)
                                      , amount: this.detailList[i].amount
                                      , otherAmount: this.detailList[i].amount
                                      , company: this.numSearchR(this.detailList[i].recipientKbn)
                                      , remark: this.detailList[i].remark
                                      , image: (this.detailList[i].image == null) ? '' : this.detailList[i].image 
                                    }
                            this.otherData.push(arr);
                            sessionStorage.setItem("050_other_expenses",JSON.stringify(this.otherData))
                            this.sumOtherAmount()
                        }
                }
                }).catch(error => {
                console.log(error)
                })
            },
            // 詳細ボタン 押下時base64の文字列をファイルオブジェクトに変換
            toBlob(base64) {
                var bin = atob(base64.replace(/^.*,/, ''));
                var buffer = new Uint8Array(bin.length);
                for (var i = 0; i < bin.length; i++) {
                    buffer[i] = bin.charCodeAt(i);
                }
                // Blobを作成
                try{
                    var blob = new Blob([buffer.buffer], {
                        type: 'image/png'
                    });
                }catch (e){
                    return false;
                }
                return blob;
            },
            //表示している画面が新規か詳細ボタンからの呼び出しかを判定し、リクエストナンバーを新規作成または取得する
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                // }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                }else if(this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            //申請・承認画面のポップアップ
            applyDialog(applyFlg){
                console.log(applyFlg)
                if(this.teikiData.length == 0 && this.travelData.length == 0 && this.otherData.length == 0) {
                    Message({
                            message: 'リスト内にデータが無いため申請できません。',
                            type: 'error',
                            duration: 4000
                        })
                }else{
                    // 申請
                    if (applyFlg == 0) {
                        this.userData.userId = this.userData.userId
                        this.reqNum = this.reqNum
                    // 承認
                    }else if (applyFlg == 1) {
                        this.userData.userId = this.rowdata.requestUserId
                        this.reqNum = this.rowdata.requestNumber
                        this.holidayDays = this.rowdata.holidayDays
                    }
                    this.prepareAllData()
                    console.log(this.userData.userId)
                    console.log('this.countEmpty', this.countEmpty)
                    if(this.countEmpty >= 1){
                        Message({
                            message: '定期券申請には画像添付は必須です。',
                            type: 'error',
                            duration: 4000
                        })
                    }else {
                        this.getCurrentTime()
                        this.requestSaveData()
                        console.log(this.requestData)
                        console.log(applyFlg)
                        this.$refs.approvalDialog.applyDialogOpen(applyFlg)
                    }
                }
                
            },
            exportPdf(){
                if(this.exportLoading == true){
                    return
                }
                this.exportLoading = true

                if(this.changebyFlg=="1"||this.changebyFlg=="2"){

                }else if(this.changebyFlg=="3"){
                    console.log('this.rowdata', this.rowdata)
                    console.log('this.rowdata', this.rowdata.requestUserId)
                    this.userData.userId = this.rowdata.requestUserId
                    this.userData.userName = this.rowdata.userName
                }
                let applyYmArr = this.applyMonth.split('-')
                let data = {
                    companyCode: this.userData.companyCode,
                    requestUserId: this.userData.userId,
                    requestNumber: this.rowdata.requestNumber,
                    applyYm: applyYmArr[1]
                }
                console.log('data',data)
                exportToPDF(data).then(res => {
                    console.log('年月',this.applyMonth);
                    let blob = new Blob([res.data], {type: "application/pdf"})
                    const href = URL.createObjectURL(blob) 
                    // window.open(href);    IEでプレビュー表示
                    const a = document.createElement('a') 
                    a.style.display = 'none'
                    a.href = href
                    console.log('this.rowdata', this.rowdata)
                    console.log('this.teikiData', this.teikiData)
                    // 定・旅・他を表示する
                    if(this.teikiData.length >= 1 && this.travelData.length == 0 && this.otherData.length == 0){
                        this.tabName = "定"
                    }else if(this.teikiData.length == 0 && this.travelData.length >= 1 && this.otherData.length == 0){
                        this.tabName = "旅"
                    }else if(this.teikiData.length == 0 && this.travelData.length == 0 && this.otherData.length >= 1){
                        this.tabName = "他"
                    }else if(this.teikiData.length >= 1 && this.travelData.length >= 1 && this.otherData.length == 0){
                        this.tabName = "定_旅"
                    }else if(this.teikiData.length >= 1 && this.travelData.length == 0 && this.otherData.length >= 1){
                        this.tabName = "定_他"
                    }else if(this.teikiData.length == 0 && this.travelData.length >= 1 && this.otherData.length >= 1){
                        this.tabName = "旅_他"
                    }else if(this.teikiData.length >= 1 && this.travelData.length >= 1 && this.otherData.length >= 1){
                        this.tabName = "定_旅_他"
                    }
                    // a.download = this.userData.userId + "_" + this.applyMonth.replaceAll('-', '') + "_月末精算申請書_" + this.userData.userName + ".pdf" //指定下载文件名
                    // a.download = this.userData.userId + "_" + this.rowdata.requestNumber + "_月末精算申請書_" + this.userData.userName + ".pdf" //指定下载文件名
                    // a.download = this.userData.userId + "_" + this.tabName + "_" + this.rowdata.requestNumber.replace(this.userData.companyCode + this.userData.userId,'').replace(this.applyMonth.replaceAll('-', ''),'') + "_月末精算_" + this.userData.userName + ".pdf" //指定下载文件名
                    console.log('getNum', this.rowdata.requestNumber.substring(6,17))
                    a.download = this.userData.userId + "_" + this.tabName + "_" + this.rowdata.requestNumber.substring(6,17) + "_月末精算_" + this.userData.userName + ".pdf" //指定下载文件名
                    console.log('a.download', a.download)
                    a.click()
                    this.exportLoading = false
                }).catch(error => {
                    this.exportLoading = false
                    console.log(error)
                });
            },
            approvalSave(number) {
                //ステータス区分：保存
                this.getCurrentTime()
                this.reqNum=number
                console.log(this.reqNum)
                this.submitBtn()
            },
            //戻るボタン（申請一覧）
            closeA() {
                this.$router.go({path: this.$router.currentRoute.path, force: true})
            },
            //戻るボタン（承認一覧）
            closeB() {
                this.$router.go(-1);
            },
        }
    }
</script>

<style lang="scss" scope>
// 申請月
.month {
    font-size: 14px;
    margin-right: 50px
}
// 追加ボタン（共通）
.add-btn {
    transition: 0.5s;
    width: 80px;
    height: 30px;
    background-color: #fd6e55;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 8px 0 rgba(253, 85, 85, 0.4);
    border-radius: 30px;
    cursor: pointer;
    color: #fff;
}
.finalize-btn {
    transition: 0.5s;
    width: 80px;
    height: 30px;
    background-color: #558afd;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 8px 0 rgba(5, 3, 122, 0.4);
    border-radius: 30px;
    cursor: pointer;
    color: #fff;
    margin-left: 650px;
    // margin-top: 10px;
}
// 取消ボタン（共通）
.gray-btn {
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
// 「上へ」「削除」ボタンサイズ
.common-btn {
    width: 80px;
}
// 定期券「追加」ボタン位置調整
.teiki-add-btn {
    margin-left: 875px;
    margin-top: -15px
}
// 旅費「追加」ボタン位置調整
.travel-add-btn {
    margin-left: 875px;
    margin-top: -15px
}
// 定期券・旅費「編集」ボタン位置調整
.list-edit-btn {
    margin-top: -15px;
    margin-left: 131px;
    width: 75px;
}
// 定期券・旅費「取消」ボタン位置調整
.list-cancel-btn {
    margin-top: 10px;
    margin-left: 131px;
    width: 80px;
}
.all-btn {
    padding: 15px 50px 0 50px;
}
// 定期・旅費・宿泊・日当合計金額
.total-amount {
    font-size: 14px;
    margin-top: 5px;
    margin-left: 530px;
}
// 入力ボタン
.input-btn {
    // margin-left: 30px;
    width: 80px;
}
// 参照ボタン
.reference-button {
    font-weight: bold;
    font-size: 14px;
    width: 80px;
}

//PDFボタン
.pdf-button {
    margin-right: 70px;
    transition: 0.5s;
    height: 30px;
    background-color: #fff;
    border: 2px #ff0000 solid;
    width: 80px;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 6px 0 rgba(250, 0, 0, 0.2);
    border-radius: 30px;
    cursor: pointer;
    color: #ff0000;
    font-weight: bold;
}

.pdf-button:hover {
    transition: 0.5s;
    border: 2px #fbd4d4 solid;
}

.header {
    display: flex;
    justify-content: space-between;
}
.btn-line{
    display: flex;
    justify-content: space-between;
}

// 申請月入力
.apply-month {
    margin-left: 35px;
    margin-bottom: 10px;
    font-size:10px;
}
// タブ画面のサイズ
.main-tab {
    width: 1000px;
    height: 500px;
    margin: auto;
}
.el-tabs__nav {
    z-index: 1 !important;
}
// リスト表のサイズ
.main-table {
    width: 100%;
    border: 1px solid #EBEEF5;
}
// 入力項目とリストの間隔調整
.input-form {
    margin-left: 5px;
    margin-top: 5px; 
}
// 経費区分・出発地・到着地の入力ボックス
.travel-kbn {
    width: 200px;
}
// 月区分
.month-kbn {
    width: 90px;
}
// 交通費到着地ラベル
.travel-arrivel-label {
    margin-left: 20px;
}
// 請求区分ボックス（共通）
.company-kbn {
     width: 90px;
}

// 添付画像ボックスサイズ（共通）
.image-upload {
    width:150px;
    height:100px;
}
.travel-img {
    margin-left: 10px;
}
// 画像
.el-upload--picture-card{
  font-size: 16px !important;
}

.el-upload{
  font-size: 16px;
}

.el-upload-list--picture-card .el-upload-list__item{
  font-size: 16px;
}

 .el-upload-list--picture-card .el-upload-list__item-actions:hover {
  opacity: 1;
  font-size: 16px;
}

.el-upload-list--picture-card .el-upload-list__item-thumbnail{
  font-size: 16px;
}
.appended-uploader .el-upload:hover {
    border-color: #409EFF;
}
.disabled .el-upload--picture-card {
    display: none;
  }
// 交通費日付ラベル
.travel-date-label {
    margin-left: 27px;

}
// 宿泊・日当の日数
.cal-days {
    margin-left: 60px;
    color: #555;
}
.remark-box {
    width: 250px;
    height: 150px;
}
//2022/6/7　加賀田(申請一覧、承認一覧に戻るボタン)
.itirann-btn {
    transition: 0.5s;
    width: 150px;
    height: 26px;
    background-color: #fff;
    font-size: 14px;
    line-height: 26px;
    text-align: center;
    box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.10);
    border: 2px #444 solid;
    border-radius: 30px;
    cursor: pointer;
    color: #444;
    font-weight: bold;
}
</style>