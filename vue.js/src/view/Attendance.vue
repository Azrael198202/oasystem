<template>
    <div>
        <commonHeader></commonHeader>
        <div class="home">
            <div class="home-main">
                <div class="list-card">
                    <div class="list-card-text">
                        <el-row>
                            <!-- タイトル -->
                            <el-col :span="18">
                                <span class="card-title-big">勤務表</span>
                            </el-col>
                            <!-- 更新ボタン,Excelボタン,Pdfボタン -->
                            <el-col :span="6" class="flex-end csv">
                                <div class="update-btn" @click="applyAttendance()">
                                    更新
                                </div>
                                &nbsp;&nbsp;  <!-- 間の空白 &nbsp;&nbsp;  -->
                                <div class="white-excel-btn" v-loading='exportLoading' @click="exportCsv()">
                                    EXCEL
                                </div>
                                &nbsp;&nbsp;
                                <div class="white-pdf-btn" v-loading='exportPdfLoading' @click="exportPdf()">
                                    PDF
                                </div>
                            </el-col>
                        </el-row>
                        <el-row style="margin: 40px 0 10px">
                            <div class="flex-between">
                                <div class="flex-start">
                                    <div class="my-info">
                                        <span class="my-info-title">部門:</span>
                                        <span class="my-info-text">{{ departmentName }}</span>
                                    </div>
                                    <div class="my-info">
                                        <span class="my-info-title">社員番号:</span>
                                        <span class="my-info-text">{{ agentCode }}</span>
                                    </div>
                                    <div class="my-info">
                                        <span class="my-info-title">社員名:</span>
                                        <span class="my-info-text">{{ userName }}</span>
                                    </div>
                                    <!-- 詳細情報ボタン -->
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="詳細情報" placement="top">
                                            <div class="my-info-all" @click="userSettingInfoOpen()">
                                                <i class="fas fa-info"></i>
                                            </div>
                                        </el-tooltip>
                                    </div>
                                    <!-- 勤務表検索ボタン -->
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="勤務表検索" placement="top">
                                            <div v-if="changebyFlg == '1'" class="my-info-all" @click="searchOpen()">
                                                <i class="fas fa-search"></i>
                                            </div>
                                        </el-tooltip>
                                    </div>
                                    <!-- 自分の勤務表に戻るボタン -->
                                    <div>
                                        <div v-if="userSearchReturnFlg" class="orange-btn" style="width: 160px;margin-top: 5px" @click="userSearchReturn()">
                                            自分の勤務表に戻る
                                        </div>
                                    </div>
                                </div>

                                <div class="flex-end dateBtn">
                                    <!-- 今日の日付に飛べるボタン -->
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="今日の日付に飛べる" placement="top">
                                            <div class="white-btn" style="width: 34px;margin-left:20px;margin-top: 3px" @click="toToday()">
                                            <i class="far fa-flag"></i>
                                            </div>
                                        </el-tooltip>
                                    </div>
                                    <!-- 前月に移動ボタン -->
                                    <div v-if="lastMonthBtn" class="disable-btn" style="width: 34px;margin-left:20px;margin-top: 3px"><i class="fas fa-angle-left"></i></div>
                                    <div v-if="!lastMonthBtn" class="white-btn" style="width: 34px;margin-left:20px;margin-top: 3px" @click="lastMonth()"><i class="fas fa-angle-left"></i></div>
                                    <!-- 年月選択 -->
                                    <el-date-picker
                                        ref="datePickerMonth"
                                        v-model="dayValue"
                                        format="yyyy/MM"
                                        value-format="yyyy/MM"
                                        type="month"
                                        placeholder="月を選択"
                                        @change="timePChange()"
                                        style="margin-left:20px;"
                                        :disabled="selectMonthBtn">
                                    </el-date-picker>
                                    <!-- 来月に移動ボタン -->
                                    <div v-if="nextMonthBtn" class="disable-btn" style="margin-left:20px;width: 34px;margin-top: 3px"><i class="fas fa-angle-right"></i></div>
                                    <div v-if="!nextMonthBtn" class="white-btn" style="margin-left:20px;width: 34px;margin-top: 3px" @click="nextMonth()"><i class="fas fa-angle-right"></i></div>
                                </div>
                            </div>
                        </el-row>
                    </div>
                    <!-- テーブル -->
                    <el-table
                        :data="tableData"
                        style="width: 100%"
                        height="568px"
                        ref="tableAttendance"
                        :row-style='todayRowStyles'
                        :row-class-name="holidayRowStyle"
                        :cell-class-name="holidayCellStyle"
                        :header-cell-style="{color:'#888',background: '#e9e9e9',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                        v-loading='tableLoading'>
                        <!-- 祝日 -->
                        <el-table-column
                            prop="holidayFlg"
                            width="41">
                            <template slot-scope="scope">
                                <span style="display: none">{{ scope.row.holidayFlg}}</span>
                            </template>
                        </el-table-column>
                        <!-- 日にち -->
                        <el-table-column
                            prop="workDate"
                            label="日"
                            width="80">
                            <template slot-scope="scope">
                                <span>{{ scope.row.workDate.substr(6,8)}}</span>
                            </template>
                        </el-table-column>
                        <!-- 曜日 -->
                        <el-table-column
                            prop="workDateWeek"
                            label="曜日"
                            width="60">
                        </el-table-column>
                        <!-- 出勤時間１ -->
                        <el-table-column
                            prop="workStart1"
                            label="出勤"
                            width="140"
                            align='center'>
                            <template slot-scope="scope">
                                <el-time-select
                                    v-if="modifyFlg"
                                    v-model="scope.row.workStart1"                                    
                                    format="HH:mm"
                                    :disabled ="scope.row.workStart1Flg"
                                    value-format="HH:mm"
                                    placeholder="出勤"
                                    style="width: 110px"
                                    default-value="10:00"
                                    :picker-options="{
                                        start:'05:00',
                                        step: '00:15',
                                        end: scope.row.workEnd1 == null || scope.row.workEnd1 == '' ?'29:00': floorTime(scope.row.workEnd1)
                                    }">
                                </el-time-select>
                                <span v-else>{{ scope.row.workStart1 }}</span>
                            </template>
                        </el-table-column>
                        <!-- 退勤時間１ -->
                        <el-table-column
                            prop="workEnd1"
                            label="退勤"
                            width="140"
                            align='center'>
                            <template slot-scope="scope">
                                <el-time-select
                                    v-if="modifyFlg"
                                    v-model="scope.row.workEnd1"
                                    :disabled ="scope.row.workEnd1Flg"
                                    format="HH:mm"
                                    value-format="HH:mm"
                                    placeholder="退勤"
                                    default-value="19:00"
                                    style="width: 110px"
                                    :picker-options="{
                                        start: ceilTime(scope.row.workStart1),
                                        step: '00:15',
                                        end: scope.row.workStart2 == null || scope.row.workStart2 == '' ?'29:00': floorTime(scope.row.workStart2)
                                    }">
                                </el-time-select>
                                <span v-else>{{ scope.row.workEnd1 }}</span>
                            </template>
                        </el-table-column>
                        <!-- 出勤時間２ -->
                        <el-table-column
                            v-if="showWorkFlg == '2'"
                            prop="workStart2"
                            label="出勤2"
                            width="88">
                            <template slot-scope="scope">
                                <el-time-select
                                    v-if="modifyFlg"
                                    v-model="scope.row.workStart2"
                                    format="HH:mm"
                                    value-format="HH:mm"
                                    placeholder="出勤2"
                                    style="width: 110px"
                                    :disabled="flgKbn[0].categoryKbnName==='2' || flgKbn[0].categoryKbnName==='3'?false:true"
                                    :picker-options="{
                                        start: scope.row.workEnd1,
                                        step: '00:15',
                                        end: scope.row.workEnd2 === null?'29:00':scope.row.workEnd2
                                    }">
                                </el-time-select>
                                <span v-else>{{ scope.row.workStart2 }}</span>
                            </template>
                        </el-table-column>
                        <!-- 退勤時間２ -->
                        <el-table-column
                            v-if="showWorkFlg == '2'"
                            prop="workEnd2"
                            label="退勤2"
                            width="88">
                            <template slot-scope="scope">
                                <el-time-select
                                    v-if="modifyFlg"
                                    v-model="scope.row.workEnd2"
                                    format="HH:mm"
                                    value-format="HH:mm"
                                    placeholder="退勤2"
                                    style="width: 110px"
                                    :disabled="flgKbn[0].categoryKbnName==='2' || flgKbn[0].categoryKbnName==='3'?false:true"
                                    :picker-options="{
                                        start: scope.row.workStart2,
                                        step: '00:15',
                                        end: scope.row.workStart3 === null?'29:00':scope.row.workStart3
                                    }">
                                </el-time-select>
                                <span v-else>{{ scope.row.workEnd2 }}</span>
                            </template>
                        </el-table-column>
                        <!-- 出勤時間３ -->
                        <el-table-column
                            v-if="showWorkFlg == '3'"
                            prop="workStart3"
                            label="出勤3"
                            width="88">
                            <template slot-scope="scope">
                                <el-time-select
                                    v-if="modifyFlg"
                                    v-model="scope.row.workStart3"
                                    format="HH:mm"
                                    value-format="HH:mm"
                                    placeholder="出勤3"
                                    style="width: 110px"
                                    :disabled="flgKbn[0].categoryKbnName==='3'?false:true"
                                    :picker-options="{
                                        start: scope.row.workEnd2,
                                        step: '00:15',
                                        end: scope.row.workEnd3 === null?'29:00':scope.row.workEnd3
                                    }">
                                </el-time-select>
                                <span v-else>{{ scope.row.workStart3 }}</span>
                            </template>
                        </el-table-column>
                        <!-- 退勤時間３ -->
                        <el-table-column
                            v-if="showWorkFlg == '3'"
                            prop="workEnd3"
                            label="退勤3"
                            width="88">
                            <template slot-scope="scope">
                                <el-time-select
                                    v-if="modifyFlg"
                                    v-model="scope.row.workEnd3"
                                    format="HH:mm"
                                    value-format="HH:mm"
                                    placeholder="退勤3"
                                    style="width: 110px"
                                    :disabled="flgKbn[0].categoryKbnName==='3'?false:true"
                                    :picker-options="{
                                        start: scope.row.workStart3,
                                        step: '00:15',
                                        end: '29:00'
                                    }">
                                </el-time-select>
                                <span v-else>{{ scope.row.workEnd3 }}</span>
                            </template>
                        </el-table-column>
                        <!-- 勤務区分 -->
                        <el-table-column
                            prop="workKbn"
                            label="区分"
                            width="175"
                            align='center'>
                            <template slot-scope="scope">
                                <el-select
                                    v-if="modifyFlg" 
                                    v-model="scope.row.workKbn" 
                                    placeholder="出勤区分"  
                                    :disabled ="scope.row.kbnSelectFlg"
                                    @change="changeEvent(scope.row)" 
                                    clearable>
                                    <el-option
                                        v-for="item in kbnList"
                                        :key="item.categoryKbn"
                                        :label="item.categoryKbnName"
                                        :value="item.categoryKbn">
                                        {{item.categoryKbnName}}
                                    </el-option>
                                </el-select>
                                <span v-else>
                                    {{ kbnListSee(scope.row.workKbn)}}
                                </span>
                            </template>
                        </el-table-column>
                        <!-- 備考欄 -->
                        <el-table-column
                            prop="remark"
                            label="備考"
                            style="width: 630px; height: 34px;">
                            <template slot-scope="{row}">
                                <div>
                                    <el-input  v-if="modifyFlg" style="width: 600px" placeholder="" v-model="row.remark" clearable></el-input>
                                    <el-tooltip v-else class="item" effect="dark" :content="row.remark" placement="top">
                                        <div class="hidden">{{row.remark}}</div>
                                    </el-tooltip>
                                </div>
                            </template>
                        </el-table-column>
                        <!-- /休憩時間合計 -->
                        <el-table-column
                            prop="workSumHoliTime"
                            label=""
                            width="1"
                            align='center'>
                            <template slot-scope="scope">
                                <div style="display: none;">
                                    <div v-if="scope.row.workStart1 !== '' && scope.row.workEnd1 !== '' && scope.row.workStart1 !== null && scope.row.workEnd1 !== null">
                                        {{ scope.row.workSumHoliTime = minuteFormat(minuteChange(scope.row.workBreakTime1) + minuteChange(scope.row.workBreakTime2) + minuteChange(scope.row.workBreakTime3)) }}
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <!-- 休憩時間１ -->
                        <el-table-column
                            prop="workBreakTime1"                
                            width="1">
                            <template slot-scope="scope">
                                <span style="display: none">
                                    {{ scope.row.workBreakTime1 = minuteFormat(restTime2(startTimeFormat(scope.row.workStart1), endTimeFormat(scope.row.workEnd1))[0]) }}
                                </span>
                            </template>
                        </el-table-column>
                        <!-- 休憩時間２ -->
                        <el-table-column
                            prop="workBreakTime2"                          
                            width="1">
                            <template slot-scope="scope">
                                <span style="display: none">
                                    {{ scope.row.workBreakTime2 = minuteFormat(restTime2(startTimeFormat(scope.row.workStart1), endTimeFormat(scope.row.workEnd1))[1]) }}
                                </span>
                            </template>
                        </el-table-column>
                        <!-- 休憩時間３ -->
                        <el-table-column
                            prop="workBreakTime3"
                            width="1">
                            <template slot-scope="scope">
                                <span style="display: none">
                                    {{ scope.row.workBreakTime3 = minuteFormat(restTime2(startTimeFormat(scope.row.workStart1), endTimeFormat(scope.row.workEnd1))[2]) }}
                                </span>
                                    
                            </template>
                        </el-table-column>
                        <!-- /所定時間 -->
                        <el-table-column
                            prop="workPrescribedTime"
                            label=""
                            width="1">
                            <template slot-scope="scope">
                                <div style="display: none">
                               		<div v-if="scope.row.workStart1 !== '' && scope.row.worEnd1 !== '' && scope.row.workStart1 !== null && scope.row.workEnd1 !== null">
                                        <span v-if="scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1'">
                                            {{ scope.row.workOverTime = '' }}
                                        </span>
	                                </div>
	                                <div v-if="scope.row.workKbn !== ''">
	                                    <span v-if="scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1'">
	                                        {{ scope.row.workOverTime = '' }}
	                                    </span>
	                                    <span v-else>{{ scope.row.workPrescribedTime = settingInfo.workPrescribedTime }}</span>
	                                </div>
	                                <div v-else>
	                                    {{ scope.row.workPrescribedTime = '' }}
	                                </div>
                                </div>    
                            </template>
                        </el-table-column>
                        <!-- /実働勤務時間 -->
                        <el-table-column
                            prop="workSumTime"
                            label=""
                            width="1">
                            <template slot-scope="scope">
                                <div style="display: none;">
                                    <div v-if="scope.row.workStart1 !== '' && scope.row.workEnd1 !== '' && scope.row.workStart1 !== null && scope.row.workEnd1 !== null">
                                        <span>{{ scope.row.workSumTime = allTime(startTimeFormat(scope.row.workStart1), endTimeFormat(scope.row.workEnd1), scope.row.workStart2, scope.row.workEnd2, scope.row.workStart3, scope.row.workEnd3, scope.row.workSumHoliTime) }}</span>
                                    </div>
                                    <div v-else>
                                        {{ scope.row.workSumTime = '' }}
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <!-- 労働時間 -->
                        <el-table-column
                            prop="workTime"
                            width="1">
                            <template slot-scope="scope">
                                <div style="display:none" v-if="scope.row.workStart1 !== '' && scope.row.worEnd1 !== '' && scope.row.workStart1 !== null && scope.row.workEnd1 !== null">
                                    <span v-if="scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1'">
                                        {{ scope.row.workTime = '' }}
                                    </span>
                                </div>
                                <div style="display:none" v-if="scope.row.workKbn !== ''">
                                    <span v-if="scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1'">
                                        {{ scope.row.workTime = '' }}
                                    </span>
                                    <span v-else>{{ scope.row.workTime = scope.row.workPrescribedTime }}</span>
                                </div>
                                <div style="display:none" v-else>
                                    {{ scope.row.workTime = '' }}
                                </div>
                            </template>
                        </el-table-column>
                        <!-- 普通残業時間 -->
                        <el-table-column
                            prop="workOverTime"
                            label=""
                            width="1">
                            <template slot-scope="scope">
                               <div style="display:none;">
                                    <div v-if="scope.row.workStart1 !== '' && scope.row.worEnd1 !== '' && scope.row.workStart1 !== null && scope.row.workEnd1 !== null">
                                        <span v-if="scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1'">
                                            {{ scope.row.workOverTime = '' }}
                                        </span>
                                        <span v-else-if="minuteChange(scope.row.workEnd1) <= minuteChange(kbnNight)">
                                            {{ scope.row.workOverTime = minuteFormat(minuteChange(scope.row.workSumTime) - minuteChange(scope.row.workPrescribedTime)) }}
                                        </span>
                                        <span v-else-if="minuteChange(scope.row.workEnd1) > minuteChange(kbnNight)">
                                            <span>
                                                {{ scope.row.workOverTime = minuteFormat(minuteChange(scope.row.workSumTime) - minuteChange(scope.row.workPrescribedTime) - minuteChange(scope.row.workMidnightTime)) }}
                                            </span>minute
                                        </span>
                                    </div>
                                    <span v-else-if="minuteChange(scope.row.workEnd1) <= minuteChange(kbnNight)">
                                        {{ scope.row.workOverTime = minuteFormat(minuteChange(scope.row.workSumTime) - minuteChange(scope.row.workPrescribedTime)) }}
                                    </span>
                                    <div v-else>
                                        {{ scope.row.workOverTime = '' }}
                                    </div>
                                </div>                            
                            </template>
                        </el-table-column>
                        <!-- 深夜残業時間 -->
                        <el-table-column
                            prop="workMidnightTime"
                            label=""
                            width="1">
                            <template slot-scope="scope">
                                <div style="display: none;">
                                    <div v-if="scope.row.workStart1 !== '' && scope.row.workEnd1 !== '' && scope.row.workStart1 !== null && scope.row.workEnd1 !== null">
                                        <span v-if="scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1'">
                                            {{ scope.row.workMidnightTime = '' }}
                                        </span>
                                        <span v-else-if="minuteChange(scope.row.workStart1)<=minuteChange(settingInfo.workBreakEnd3)">
                                            {{ scope.row.workMidnightTime = minuteFormat(minuteChange(scope.row.workEnd1) - minuteChange(kbnNight) - minuteChange(settingRestTime3)) }}
                                        </span>
                                        <span v-else>
                                             {{scope.row.workMidnightTime= minuteFormat(minuteChange(scope.row.workEnd1)-minuteChange(scope.row.workStart1))}}
                                        </span>
                                    </div>
                                    <div v-else>
                                        {{ scope.row.workMidnightTime = '' }}
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <!-- 休日出勤 -->
                        <el-table-column
                            prop="workHoliTime"
                            label=""
                            width="1">
                            <template slot-scope="scope">
                                <div style="display: none;">
                                    <div v-if="scope.row.workStart1 !== '' &&
                                        scope.row.workEnd1 !== '' &&
                                        scope.row.workStart1 !== null &&
                                        scope.row.workEnd1 !== null &&
                                        (scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1')">
	                                    <span>
	                                        {{ scope.row.workHoliTime = scope.row.workSumTime }}
	                                    </span>
                                    </div>
                                    <div v-else>
                                        {{ scope.row.workHoliTime = '' }}
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <!-- 休日深夜 -->
                        <el-table-column
                            prop="workMidnightHoliTime"
                            label=""
                            width="1">
                            <template slot-scope="scope">
                                <div style="display: none;">
                                    <div v-if="scope.row.workStart1 !== '' &&
                                        scope.row.workEnd1 !== '' &&
                                        scope.row.workStart1 !== null &&
                                        scope.row.workEnd1 !== null ">
                                        <span v-if="scope.row.workDateWeek == '土' || scope.row.workDateWeek == '日' || scope.row.holidayFlg == '1'">
                                            <span v-if="minuteChange(scope.row.workStart1)<=minuteChange(settingInfo.workBreakEnd3)">
                                            {{ scope.row.workMidnightHoliTime = minuteFormat(minuteChange(scope.row.workEnd1) - minuteChange(kbnNight) - minuteChange(settingRestTime3)) }}
                                            </span>
                                            <span v-else>
                                                {{scope.row.workMidnightHoliTime = minuteFormat(minuteChange(scope.row.workEnd1)-minuteChange(scope.row.workStart1))}}
                                            </span>
                                        </span>
		                                <span v-else>
		                                    {{ scope.row.workMidnightHoliTime = '' }}
		                                </span>
                                    </div>
                                    <div v-else>
                                        {{ scope.row.workMidnightHoliTime = '' }}
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-row class="btn-area">
                        <!-- 戻るボタン,保存ボタン -->
                        <el-col :span="12" class="flex-start">
                            <div class="back-btn" @click="closeA()">戻る</div>
                            <div class="green-btn" @click="modifyBtn($event)" v-if="modifyFlgsave" >保存</div>
                        </el-col>
                        <!-- 申請ボタン,取戻ボタン,差戻ボタン,承認ボタン -->
                        <el-col :span="12" class="flex-end">
                            <div class="orange-btn" @click="applyopenreq($event)" v-if="reqFlg">申請</div>
                            <div class="blue-btn"  @click="applyopende()" v-if="changebyFlg == '2' && this.rowdata.requestStatusKbn == '003'">取戻</div>
                            <div class="red-btn" @click="applyopenwf()" v-if="changebyFlgwf">差戻</div>
                            <div class="green-btn approval-btn" @click="applyopenapp($event)" v-if="changebyFlgapp">承認</div>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </div>
        <!-- 取戻のダイアログ内 -->
        <el-dialog
            title="取戻でよろしいですか"
            :visible.sync="opende"
            width="300px"
            :close-on-click-modal="true">
            <!-- 取戻の確定ボタン,キャンセルボタン -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteRes()">確定</el-button>
                <el-button @click="opende = false">キャンセル</el-button>
            </span>
        </el-dialog>
        <!-- 差戻のダイアログ内 -->
        <el-dialog
            title="差戻理由"
            :visible.sync="openwf"
            width="410px"
            :close-on-click-modal="true">
            <el-input
                ref="textareaInput"
                type="textarea"
                placeholder="差戻理由"
                v-model="appcomment"
                maxlength="200"
                :row="10"
                show-word-limit>
            </el-input>
            <!-- 差戻の確定ボタン -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="returnRes()">確 定</el-button>
            </span>
        </el-dialog>
        <!-- 詳細情報のダイアログ内 -->
        <el-dialog
            title="詳細情報"
            :visible.sync="dialogUserInfo"
            width="640px"
            :close-on-click-modal="true"
            class="dialogUserInfoClass">
            <el-row>
                <el-col :span='6' class="my-info">
                    <span class="name-title">{{ userName }}</span>
                    <span class="my-info-title">様</span>
                </el-col>
                <el-col :span='6' class="my-info" style="margin-top: 10px">
                <span class="my-info-title">社員番号:</span>
                    <span class="my-info-text">{{ agentCode }}</span>
                </el-col>
                <el-col :span='6' class="my-info" style="margin-top: 10px">
                    <span class="my-info-title">部門:</span>
                    <span class="my-info-text">{{ departmentName }}</span>
                </el-col>
            </el-row>
            <el-row class="info-card">
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">計算単位</div>
                        <span class="my-info-text" style="margin-left: 30px">{{ settingInfo.workEveryTime == '' || settingInfo.workEveryTime == null ? '' : settingInfo.workEveryTime }}(分)</span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">所定労働時間</div>
                        <span class="my-info-text" style="margin-left: 30px">{{ settingInfo.workPrescribedTime }}</span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">始業終業</div>
                        <span class="my-info-text" style="margin-left: 30px">{{ settingInfo.workStartTime }} ~ {{ settingInfo.workEndTime }}</span>

                    </el-col>
                </el-row>
                <el-row>
                    <el-col class="my-info">
                        <div class="my-info-title" style="display: inline-block;width: 90px;">休憩時間</div>
                        <span class="my-info-text" style="margin-left: 30px" v-if="restFlg1">{{ settingInfo.workBreakStart1 }} ~ {{ settingInfo.workBreakEnd1 }}</span>
                        <span class="my-info-text" style="margin-left: 14px" v-if="restFlg2">{{ settingInfo.workBreakStart2 }} ~ {{ settingInfo.workBreakEnd2 }}</span>
                        <span class="my-info-text" style="margin-left: 14px" v-if="restFlg3">{{ settingInfo.workBreakStart3 }} ~ {{ settingInfo.workBreakEnd3 }}</span>
                    </el-col>
                </el-row>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogUserInfo = false">キャンセル</el-button>
            </span>
        </el-dialog>
        <!-- 勤務表検索のダイアログ内 -->
        <el-dialog
            title="勤務表検索"
            :visible.sync="dialogSee"
            width="560px"
            :close-on-click-modal="true"
            @close="userSearchClose()">
            <el-form ref="formSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                <el-form-item prop="search_id">
                    <el-row :gutter='10'>
                        <el-col :span="19">
                            <el-input
                                ref="searchInput"
                                placeholder="社員番号または社員名を入力してください"
                                v-model="formSearch.search_id"
                                @keyup.enter.native="userSearch()">
                            </el-input>
                        </el-col>
                        <!-- 検索ボタン -->
                        <el-col :span="5">
                            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px;" @click="userSearch()">検索</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </el-form>

            <div v-if="searchUserInfo" class="searchUserInfoArea">
                <div v-for="(item , index) in userSearchR" :key="index">
                    <!-- 選択ボタン（検索結果が表示） -->
                    <div class="searchUserInfo-btn" @click="userSearchIn(item.firstName + ' ' + item.lastName, item.userId, item.departmentName)">
                        <el-col :span='22'>
                            <el-row>
                                <el-col :span='24' class="my-info">
                                    <span class="my-info-title" style="line-height: 30px">社員名前:</span>
                                    <span class="my-info-text" style="line-height: 30px">{{ item.firstName + ' ' + item.lastName }}</span>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span='10' class="my-info">
                                    <span class="my-info-title" style="line-height: 30px">社員番号:</span>
                                    <span class="my-info-text" style="line-height: 30px">{{ item.userId }}</span>
                                </el-col>
                                <el-col :span='8' class="my-info">
                                    <span class="my-info-title" style="line-height: 30px">部門:</span>
                                    <span class="my-info-text" style="line-height: 30px">{{ item.departmentName }}</span>
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
            <div v-if="searchUserInfo" style="font-size: 16px;color: #444;margin: 10px 0 0 10px">
                検索結果:<span style="font-size: 20px;color: #ffa811">{{this.userSearchR.length}}</span>名
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="userSearchClose()">キャンセル</el-button>
            </span>
        </el-dialog>
        <!-- 申請前、承認者選択のダイアログ内 -->
        <el-dialog
            title="承認者選択"
            :visible.sync="openreq"
            width="900px"
            :close-on-click-modal="true">
                <el-row justify="space-between"> 
                    <el-col :span="8" class="rout">
                        <div style="margin-bottom: 10px"><h4>現在までの承認ルート</h4></div>
                        <div class="block">
                            <el-timeline :reverse='true'>
                                <el-timeline-item v-for="(item,index) in routerList" :key='index' :timestamp='item.reqAppDatetime' placement="top" :color='routerColor(item.wfStatusKbn)'>
                                    <el-card>
                                        <div class="flex-between"><h4>{{item.userName}}</h4><h4 :class="routerStatusColor(item.categoryKbnName)">{{item.categoryKbnName}}</h4></div>
                                        <p>{{item.reqAppComment}}</p>
                                    </el-card>
                                </el-timeline-item>
                            </el-timeline>
                            </div>
                    </el-col>
                    <el-col :span="14" style="justify-content: flex-end;">
                        <div>
                            <el-form ref="formSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                                <el-form-item prop="search_id">
                                    <el-row :gutter='10'>
                                        <el-col :span="19">
                                            <el-input
                                                ref="searchInput"
                                                placeholder="承認者の社員番号または承認者の名前を入力してください"
                                                v-model="formSearch.search_id"
                                                @keyup.enter.native="userSearch()">
                                            </el-input>
                                        </el-col>
                                        <!-- 検索ボタン -->
                                        <el-col :span="5">
                                            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px;" @click="userSearch()">検索</el-button>
                                        </el-col>
                                    </el-row>
                                </el-form-item>
                            </el-form>
                            <div v-if="searchUserInfo" class="searchUserInfoArea">
                                <div v-for="(item , index) in userSearchR" :key="index">
                                    <!-- 選択ボタン（検索結果が表示） -->
                                    <div class="searchUserInfo-btn" @click="userSearchInreq(item.firstName + ' ' + item.lastName, item.userId, item.departmentName)">
                                        <el-col :span='22'>
                                            <el-row>
                                                <el-col :span='24' class="my-info">
                                                    <span class="my-info-title" style="line-height: 30px">社員名前:</span>
                                                    <span class="my-info-text" style="line-height: 30px">{{ item.firstName + ' ' + item.lastName }}</span>
                                                </el-col>
                                            </el-row>
                                            <el-row>
                                                <el-col :span='10' class="my-info">
                                                    <span class="my-info-title" style="line-height: 30px">社員番号:</span>
                                                    <span class="my-info-text" style="line-height: 30px" >{{ item.userId }}</span>
                                                </el-col>
                                                <el-col :span='8' class="my-info">
                                                    <span class="my-info-title" style="line-height: 30px">部門:</span>
                                                    <span class="my-info-text" style="line-height: 30px">{{ item.departmentName }}</span>
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
                            <div v-if="searchUserInfo" style="font-size: 14px;color: #444;margin: 10px 0 0 10px">
                                検索結果:<span style="font-size: 16px;color: #ffa811">{{this.userSearchR.length}}</span>名
                            </div>
                        </div>
                        <div class="approval-area">
                            <el-row>
                                <el-col :span='4'>
                                    <div class="approval-area-title">承認者選択</div>
                                </el-col>
                                <el-col v-if="!searchUserInfomail" :span='20'>
                                    <span class="approval-area-notext">未選択</span> 
                                </el-col>
                                <el-col v-if="searchUserInfomail" :span='20'>
                                    <span class="approval-area-text">{{name_req}}</span>
                                    <span class="approval-area-text">{{userId_req}}</span>
                                    <span class="approval-area-text">{{departmentName_req}}</span>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span='4' style="margin-top: 10px">
                                    <div class="approval-area-title">申請概要</div>                                
                                </el-col>                                
                                <el-col :span='20' style="margin-top: 10px">
                                    <el-input placeholder="" v-model="remarkreq" v-if="remarkreqif"></el-input>
                                </el-col>
                            </el-row>
                        </div>
                        <!-- 次の人へ回覧ボタン -->
                        <el-button v-if="searchUserInfomailnext" type="primary" round @click="insertRes()" style="margin-top: 15px">次の人へ回覧</el-button>
                    </el-col>
                </el-row>    
            <span slot="footer" class="dialog-footer">
                <el-button @click="openreq = false">キャンセル</el-button>
            </span>
        </el-dialog>
        <!-- 申請後、承認者選択のダイアログ内 -->
        <el-dialog
            title="承認者選択"
            :visible.sync="openlast"
            width="900px"
            :close-on-click-modal="true">
            <el-row justify="space-between">
                <el-col :span="8" class="rout">
                    <div><h3>現在までの承認ルート</h3></div>
                    <div class="block">
                        <el-timeline :reverse='true'>
                            <el-timeline-item v-for="(item,index) in routerList" :key='index' :timestamp='item.reqAppDatetime' placement="top" :color='routerColor(item.wfStatusKbn)'>
                                <el-card>
                                    <div class="flex-between"><h4>{{item.userName}}</h4><h4 :class="routerStatusColor(item.categoryKbnName)">{{item.categoryKbnName}}</h4></div>
                                    <p>{{item.reqAppComment}}</p>
                                </el-card>
                            </el-timeline-item>
                        </el-timeline>
                    </div>
                </el-col>
                <el-col :span="14" style="justify-content: flex-end;" >
                    <div>
                        <el-form ref="formSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                            <el-form-item prop="search_id">
                                <el-row :gutter='10'>
                                    <el-col :span="19">
                                        <el-input
                                            ref="searchInput"
                                            placeholder="承認者の社員番号または承認者の名前を入力してください"
                                            v-model="formSearch.search_id"
                                            @keyup.enter.native="userSearch()">
                                        </el-input>
                                    </el-col>
                                    <!-- 検索ボタン -->
                                    <el-col :span="5">
                                        <el-button type="primary" icon="el-icon-search" style="margin-left: 10px;" @click="userSearch()">検索</el-button>
                                    </el-col>
                                </el-row>
                            </el-form-item>
                        </el-form>
                        <div v-if="searchUserInfo" class="searchUserInfoArea">
                            <div v-for="(item , index) in userSearchR" :key="index">
                                <!-- 選択ボタン（検索結果が表示） -->
                                <div class="searchUserInfo-btn" @click="userSearchIn(item.firstName + ' ' + item.lastName, item.userId, item.departmentName)">
                                    <el-col :span='22'>
                                        <el-row>
                                            <el-col :span='24' class="my-info">
                                                <span class="my-info-title" style="line-height: 30px">社員名前:</span>
                                                <span class="my-info-text" style="line-height: 30px">{{ item.firstName + ' ' + item.lastName }}</span>
                                            </el-col>
                                        </el-row>
                                        <el-row>
                                            <el-col :span='10' class="my-info">
                                                <span class="my-info-title" style="line-height: 30px">社員番号:</span>
                                                <span class="my-info-text" style="line-height: 30px">{{ item.userId }}</span>
                                            </el-col>
                                            <el-col :span='8' class="my-info">
                                                <span class="my-info-title" style="line-height: 30px">部門:</span>
                                                <span class="my-info-text" style="line-height: 30px">{{ item.departmentName }}</span>
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
                        <div v-if="searchUserInfo" style="font-size: 16px;color: #444;margin: 10px 0 0 10px">
                            検索結果:<span style="font-size: 20px;color: #ffa811">{{this.userSearchR.length}}</span>名
                        </div>
                    </div>
                    <div class="approval-area">
                        <el-row>
                            <el-col :span='4'>
                                <div class="approval-area-title">承認者選択</div>                                
                            </el-col>                                
                            <el-col v-if="!searchUserInfomail" :span='20'>
                                <span class="approval-area-notext">未選択</span> 
                            </el-col>
                            <el-col v-if="searchUserInfomail" :span='20'>
                                <span class="approval-area-text">{{name_req}}</span>
                                <span class="approval-area-text">{{userId_req}}</span>
                                <span class="approval-area-text">{{departmentName_req}}</span>
                            </el-col>
                        </el-row>
                    </div>
                    <!-- 次の人へ回覧ボタン -->
                    <el-button type="primary" round @click="admitwf()" style="margin-top: 15px">承認＆次の人へ回覧</el-button>
                    <!-- 最終承認ボタン -->
                    <el-button type="primary" round @click="lastadmit()" v-if="openApproval" style="margin-top: 15px">最終承認</el-button>
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="openlast = false">キャンセル</el-button>
            </span>
        </el-dialog>
        <commonFootter></commonFootter>
    </div>
</template>

<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { sendreqMailApi } from '@/api/findPass'
import { getWorkFlowList } from '@/api/workFlow'
import { getAttendanceDetail,getApplyAttendanceActivity, getAtndStting, attendanceCreate,attendanceCreateUpdate, exportToProveExcel ,exportToAttendancePdf,exportToAttendancePdfByEmployee} from '@/api/attendance'
import { getCategory, getHoliday } from '@/api/category'
import { getUserInfo, getUserDetailedInfo } from '@/utils/auth'
import { formatDate } from '@/utils/formatDate'
import { getUserDetail } from '@/api/user'
import Papa from 'papaparse'
import { insertRequest, updateSkRequest, deleteRequest, updateLaRequest } from '@/api/requestList'
import { insertRequestAttendance,deleteRequestAttendance, getRequestAttendanceByList, getStatusAttendance } from '@/api/requestAttendanceList'
import { getWfList,insertNewWf, insertWf, updateWf, updateadmitWf, updatelastadmitWf, deleteAllWf, deleteDataWf } from '@/api/wfList'
import { Message } from 'element-ui';

export default {
    name: "Attendance",
    components: {
        commonHeader,
        commonFootter,
        Papa
    },
    filters: {
        formatDate : function (time) {
            let date = new Date(time)
            return formatDate(date, 'hh:mm')
        }
    },
    // 初期画面
    created() {
        this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
        this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
        this.addDate()
        this.mYear = String(this.iYear)
        this.mMonth = String(this.iMonth)
        this.mDayDate = this.iDayDate
        this.userInfoInit()
        // 勤務表申請　申請ボタン表示/非表示 
        // 勤務表画面の場合　⇒　テーブルを表示させ、条件によって申請ボタン,保存ボタンの表示/非表示
        if(this.changebyFlg == "1"){
            let datacbflg = {
                companyCode: '000001',
                userId: this.agentCode,
                workDateYm:  String(this.mYear) + String(this.mMonth)
            }
            // 申請テーブル(グループメンバー取得) テーブルが表示
            getRequestAttendanceByList(datacbflg).then(res =>{
                // 申請データがある場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                if(res.data){
                    this.reqFlg = false
                    this.modifyFlgsave = false
                // 申請データがない場合　⇒申請ボタンが表示,保存ボタンが表示
                }else{
                    this.reqFlg = true
                    this.modifyFlgsave = true
                }
            })
            this.tableLoading = true
        }
        // 勤務表画面の場合　⇒ 前月に移動ボタンが非表示,来月に移動ボタンが非表示
        if(this.changebyFlg == "1"){
            this.lastMonthBtn = false
            this.nextMonthBtn = false
            let datedisplay = {
                companyCode: this.companyCode_user,
                userId: this.agentCode,
                workDateYm: String(this.mYear) + String(this.mMonth)
            }
            this.getDetail(datedisplay)
        // 申請一覧画面の場合　または　承認一覧画面の場合　⇒　前月に移動ボタン,年月選択,来月に移動ボタンが表示
        }else if(this.changebyFlg == "2" || this.changebyFlg == "3"){
            this.lastMonthBtn = true
            this.selectMonthBtn = true
            this.nextMonthBtn = true
            // 申請一覧画面の場合　かつ　状態が差戻の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
            if(this.changebyFlg == "2"&&this.rowdata.requestStatusKbn !== "010"){
                this.reqFlg = false
                this.modifyFlgsave = false
            }
            // 承認一覧画面の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
            if(this.changebyFlg == "3"){
                this.reqFlg = false
                this.modifyFlgsave = false
                // 差戻・承認ボタン表示/非表示 浦 不具合対応 2022/6/3 start
                // 差戻の場合　⇒　差戻ボタンが表示,承認ボタンが表示
                if(this.rowdata.wfStatusKbn =="020"){
                    this.changebyFlgwf = true
                    this.changebyFlgapp = true
                // 未承認の場合　⇒  差戻ボタンが非表示,承認ボタンが非表示 
                }else if(this.rowdata.wfStatusKbn == "021") {
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                // 承認済の場合　または　承認完了の場合　⇒　差戻ボタンが表示,承認ボタンが非表示
                }else if(this.rowdata.wfStatusKbn =="025"|| this.rowdata.wfStatusKbn =="900") {
                    this.changebyFlgwf = true
                    this.changebyFlgapp = false
                }
                // 差戻・承認ボタン表示/非表示 浦 不具合対応 2022/6/3 end
            }       
            this.dayValue = this.rowdata.workDateYm.substring(0,4)+"/"+this.rowdata.workDateYm.substring(4,6)
            this.dayValueR = this.dayValue
            this.mYear = this.rowdata.workDateYm.substring(0,4)
            this.mMonth = this.rowdata.workDateYm.substring(4,6)
            let dataAll = new Date(this.mYear, this.mMonth, 0);
            this.iDayDate = parseInt(dataAll.getDate());
            this.mDayDate = this.iDayDate
            this.userName = this.rowdata.userName
            this.agentCode = this.rowdata.requestUserId

            let datehide = {
                companyCode: this.rowdata.companyCode,
                userId: this.rowdata.requestUserId,
                workDateYm: this.rowdata.workDateYm
            }
            this.getDetail(datehide)
        }
        
    },
    data() {
        // 検証ルール(validateId)
        const validateId = (rule, value, callback) => {
            // データが空　の場合　⇒　エラーメッセージが表示
            if(value.replaceAll(' ', '').replaceAll('  ', '') === '') {
                callback(new Error('必須入力です。'))
            //　それ以外　の場合　⇒　実行
            }else{
                callback()
            }
        }
        return {
            companyCode_user: '',
            workKbn:'',
            workEnd: '',
            wfList: '',
            checkStatus: '',
            rowdata: [],
            changebyFlg: '',
            remarkreq: '',
            showWorkFlg: '1',
            routerList: [],
            open:false,
            settingRestTime1: '',
            settingRestTime2: '',
            settingRestTime3: '',
            // 年
            mYear: '',
            // 月
            mMonth: '',
            // 日にち
            mDaydate: '',
            open:false,
            // 現在の年 + 現在の月 + 現在の日にち
            yMd: '',
            // 現在の年
            iYear: '',
            // 現在の月
            iMonth: '',
            // 現在の日にち
            iDay: '',
            iDayDate: '',
            // 現在の年 + '/' + 現在の月 + '/' + 現在の日にち + 現在の時間 + ':' +  現在の分 
            allTimeType: '',
            // 現在の年 + 現在の月 + 現在の日にち + 現在の時間 + 現在の分 + 現在の秒+ミリ秒
            aTime: '',
            // ワークフローテーブル
            reviseReqAppOrder: '',
            total: 0,
            // 入力項目
            input: '',
            // 申請概要入力　表示/非表示
            remarkreqif: true,
            // 申請ボタン　表示/非表示
            reqFlg: true,
            // 保存ボタン　表示/非表示
            modifyFlgsave: true,
            // 最終承認ボタン　表示/非表示
            openApproval: true,
            // 承認ボタン　表示/非表示
            changebyFlgapp: false,
            // 差戻ボタン　表示/非表示
            changebyFlgwf: false,
            // 申請前、承認者選択のダイアログ内 表示/非表示
            openreq: false,
            // 申請後、承認者選択のダイアログ内　表示/非表示
            openlast: false,
            // 差戻のダイアログ　表示/非表示
            openwf: false,
            // 差戻理由欄
            appcomment: '',
            // 取戻のダイアログ　表示/非表示
            opende: false,
            // 今日の日付に飛べるボタン　表示/非表示
            toTodayFlg: false,
            // テーブル　表示/非表示
            tableLoading: false,
            // 備考欄　表示/非表示
            modifyFlg: true,
            // 詳細情報のダイアログ　表示/非表示
            dialogUserInfo: false,
            // 勤務表検索のダイアログ　表示/非表示
            dialogSee: false,
            // 検索結果　表示/非表示
            searchUserInfo: false,
            searchUserInfomail: false,
            // 次の人へ回覧ボタン　表示/非表示
            searchUserInfomailnext: true,
            // 他の社員の勤務表　表示/非表示
            userSearchReturnFlg: false,
            // Excelボタン　ダウンロード表現　表示/非表示
            exportLoading: false,
            // Pdfボタン　ダウンロード表現　表示/非表示
            exportPdfLoading: false,
            // 区分名
            kbnToday: '',
            // 普通残業,深夜残業,休日深夜
            kbnNight: '',
            // 年月選択
            dayValue: '',
            dayValueR: '',
            // 休憩時間12:00~13:00　表示/非表示
            restFlg1: true,
            // 休憩時間18:00~18:30　表示/非表示
            restFlg2: true,
            // 休憩時間22:00~22:30　表示/非表示
            restFlg3: true,
            // 管理者コード
            permissionKbn_user: '',
            // 社員番号
            agentCode:  '',
            agentCode_kari:  '',
            userId_req: '',
            resUserId: '',
            // 社員名
            userName:  '',
            userName_kari:  '',
            name_req: '',
            // 所属名
            departmentName:  '',
            departmentName_kari:  '',
            departmentName_req: '',
            // 区分リスト
            kbnList: {index: '',categoryKbnName: '' },
            // テーブル
            tableData: [],
            tableData_kari: [],
            settingInfo: [],
            holidayDate: [],
            rowDataRemark: [],
            rest: [],
            flgKbn:[],
            // 社員番号＆社員名　検索
            userSearchR: [],
            formSearch: {
                search_id: ''
            },
            // 検索項目必須
            // 前月に移動ボタン　表示/非表示
            lastMonthBtn: true,
            // 年月選択　表示/非表示
            selectMonthBtn: false,
            // 来月に移動ボタン　表示/非表示
            nextMonthBtn: true
        }
    },
    methods:{
        // 曜日名
        switchDay(day){
            switch(day){
                case '月':
                return '火';
                case '火':
                return '水';
                case '水':
                return '木';
                case '木':
                return '金';
                case '金':
                return '土';
                case '土':
                return '日';
                case '日':
                return '月';
            }
        },

        // 承認状態区分名
        routerStatusColor(categoryKbnName){
            switch(categoryKbnName){
                case '未承認':
                    return 'blue-text'
                case '承認完了':
                    return 'green-text'
                case '差戻':
                    return 'red-text'
                case '申請済み':
                    return 'orange-text'
                case '承認済':
                    return 'orange-text'
            }
        },
        // 申請者用＆承認用状態区分コード
        routerColor(wfStatusKbn){
            switch(wfStatusKbn){
                case '012':
                    return '#ffa811'
                case '020':
                    return '#4685ff'
                case '021':
                    return '#fd5555'
                case '025':
                  return '#ffa811'
                case '900':
                  return '#54c336'
            }
        },

        // 今日の日付を表示　背景の色変更
        todayRowStyles({row, rowIndex}) {
            //年と現在の年　かつ　月と現在の月　かつ　現在の日にち　の場合　⇒　背景の色変更
            if(this.mYear == this.iYear && this.mMonth == this.iMonth && Number(row.workDate.substring(6,8)) == this.iDay){
                let styleJson = {
                    "background-color": "#efffeb !important"
                };
                return styleJson
            }
        },

        // 土曜日,日曜日,祝日　の場合　⇒　背景の色変更
        holidayRowStyle({row,rowIndex}){
            // 曜日が　土曜日,日曜日,祝日('1')の場合　⇒　背景の色変更（灰色）
            if(row.workDateWeek == '土' || row.workDateWeek == '日' || row.holidayFlg == '1'){
                return 'holiday-bg'
            // それ場合　⇒　色変更（白）
            }else{
                return 'd-row'
            }
        },
        // 土曜日,日曜日,祝日　の場合　⇒　日にち,曜日の色変更
        holidayCellStyle({row, column, rowIndex, columnIndex}){
            //　曜日が　日曜日,祝日('１') の場合　⇒　日にち,曜日の色変更（赤）
            if(row.workDateWeek == '日' || row.holidayFlg == '1'){
                // 　日にち（1）　または　曜日(2)の　場合　⇒　日にち,曜日の色変更（赤）
                if(columnIndex == 1 || columnIndex == 2){
                    return 'sunday-cell'
                // それ以外 の場合　⇒　空
                }else{
                    return ''
                }
            //　曜日が　土曜日　の場合　⇒　日にち,曜日の色変更（青）
            }else if(row.workDateWeek == '土') {
                // 　日にち（1）　または　曜日(2)の　場合　⇒　色変更（青）
                if(columnIndex == 1 || columnIndex == 2){
                    return 'saturday-cell'
                // それ以外　の場合　⇒　空
                }else{
                    return ''
                }
            // それ以外　の場合　⇒　空
            }else{
                return ''
            }
        },

        // 詳細情報ボタン押下時の処理
        userSettingInfoOpen(){
            this.dialogUserInfo = true
        },

        // 勤務表検索ボタン押下時の処理
        searchOpen(){
            this.dialogSee = true
        },
        // 検索ボタン(社員番号＆社員名)押下時の処理     
        userSearch(){
            this.userSearchR = []
            // 検索項目必須
            let data = {
                companyCode: this.companyCode_user.trim(),
                userId: this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', ''),
            }
            // 利用者マスタ（グループメンバー取得　該当ユーザー存在していない）
            getUserDetail(data).then( res => {
                this.userSearchR = res.data
                this.searchUserInfo = true
            }).catch(error => {
                console.log(error)
            });
        },

        // キャンセルボタン押下時の処理
        userSearchClose(){
            this.dialogSee = false
            this.searchUserInfo = false
            this.formSearch.search_id = ''
        },

        // 今日の日付に飛べるボタン押下時の処理
        toToday(){
            this.toTodayFlg = false
            let nowDate = new Date();
            let month = String(nowDate.getMonth() + 1);
            this.iDay = nowDate.getDate();
            // 一桁（月）　の場合　⇒　0を頭に足す（'0' + 月）
            if(month.length == 1){
                month = '0' + month;
            }
            //　年月と月が同じ　の場合　⇒　開いたときに今日の日付に自動的に移動
            if(this.dayValue.split('/')[1] == month){
                let vmEl = this.$refs.tableAttendance.$el
                let a = vmEl.querySelectorAll('.el-table__body tr')[this.iDay - 1]
                //　開いたときに今日の日付に自動的に移動
                if(a !== undefined) {
                    this.$nextTick(function(){
                        if(!vmEl) return
                        const targetTop = vmEl.querySelectorAll('.el-table__body tr')[this.iDay - 1].offsetHeight
                        this.$refs.tableAttendance.bodyWrapper.scrollTop = targetTop*(this.iDay-1);
                    })
                }
            //　それ以外　の場合　⇒　今日の日付に飛べるボタンが表示
            }else{
                this.toTodayFlg = true
                this.addDate()
                this.timePChange()
            }
        },

        // 前月ボタン押下時の処理
        lastMonth(){
            let monthS = ''
            let yearN = Number(this.dayValueR.split('/')[0])
            let monthN = Number(this.dayValueR.split('/')[1])
            // 現在の月が１月　の場合　⇒　前の年の１２月に移動
            if(monthN == 1){
                yearN--
                monthN = 12
                monthS = monthN
            // それ以外　の場合　⇒　前の月に移動
            }else{
                monthN--
                monthS = monthN
            }
            // 一桁（現在の年）の場合　⇒　0を頭に足す（'0' + 現在の年）
            if(String(monthN).length == 1){
                monthS = '0' + monthN
            }
            this.dayValue = String(yearN) + '/' + String(monthS)
            this.dayValueR = this.dayValue
            this.updatebtnflg()
        },
        // 来月ボタン押下時の処理
        nextMonth(){
            let monthS = ''
            let yearN = Number(this.dayValueR.split('/')[0])
            let monthN = Number(this.dayValueR.split('/')[1])
            // 現在の月が１２月　の場合　⇒　次の年の１月に移動
            if(monthN == 12){
                yearN++
                monthN = 1
                monthS = monthN
            // それ以外　の場合　⇒　前の月に移動
            }else{
                monthN++
                monthS = monthN
            }
            // 一桁（現在の年）の場合　⇒　0を頭に足す（'0' + 現在の年）
            if(String(monthN).length == 1){
                monthS = '0' + monthN
            }
            this.dayValue = String(yearN) + '/' + String(monthS)
            this.dayValueR = this.dayValue
            this.updatebtnflg()
        },

        // 年月選択の処理　年月選択がnull　の場合　⇒　エラーメッセージが表示　現在の日付の日数を取得
        timePChange(){
            this.dayValueR = this.dayValue
            // 年月選択　が　null　の場合　⇒　エラーメッセージが表示　申請ボタンが非表示,保存ボタンが非表示
            if(this.dayValue == null){
                Message({
                    message: '月を選択してください。',
                    type: 'error',
                    duration: 4 * 1000
                })
                this.reqFlg = false
                this.modifyFlgsave = false
                return
            }
            this.mYear = this.dayValue.split('/')[0]
            this.mMonth = this.dayValue.split('/')[1]
            let dataAll = new Date(this.mYear, this.mMonth, 0);
            this.mDayDate = parseInt(dataAll.getDate());// 获取当前日期的天数（現在の日付の日数を取得します）
            let date = {
                companyCode: getUserInfo().companyCode,
                userId: this.agentCode,
                workDateYm: this.mYear + this.mMonth
            }
            // 保存・申請ボタン表示/非表示 浦 不具合対応 2022/6/3 start
            // 申請,勤務表申請テーブル(グループメンバー取得)　保存ボタン,申請ボタンが表示/非表示
            getStatusAttendance(date).then(res =>{
                this.checkStatus = res.data
                // 申請している　場合　⇒　保存ボタン,申請ボタンが表示/非表示
                if(this.checkStatus){
                    // 管理者コード　'500' かつ　'550' の以外の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                    if(this.permissionKbn_user !== '500' && this.permissionKbn_user !== '550'){
                        this.reqFlg = false
                        this.modifyFlgsave = false
                    // 管理者コード　'500' または　'550' の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                    }else if(this.permissionKbn_user == '500' || this.permissionKbn_user == '550'){
                        // 自分の画面　の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                        if(this.agentCode == getUserInfo().userId){
                            this.reqFlg = false
                            this.modifyFlgsave = false
                        // 自分の画面　以外の画面　⇒　申請ボタン,保存ボタンが表示/非表示
                        }else if(this.agentCode !== getUserInfo().userId){
                            // 最終承認（999）　の場合　⇒　申請ボタンが非表示,保存ボタンが表示
                            if(this.checkStatus.requestStatusKbn == 999){
                                this.reqFlg = false
                                this.modifyFlgsave = true
                            // 最終承認（999）　以外の場合　⇒　申請ボタンが表示,保存ボタンが表示
                            }else{
                                this.modifyFlgsave = true
                                this.reqFlg = true
                            }
                        }
                    }
                // 申請してない　場合　⇒　保存ボタン,申請ボタンが表示/非表示
                }else{
                    // 管理者コード　'500' かつ　'550' の以外の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                    if(this.permissionKbn_user !== '500' && this.permissionKbn_user !== '550'){
                        // 自分の画面　の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                        if(this.agentCode !== getUserInfo().userId){
                            this.reqFlg = false
                            this.modifyFlgsave = false
                        // 自分の画面　以外場合　⇒　申請ボタンが表示,保存ボタンが非表示
                        }else{
                            this.reqFlg = true
                            this.modifyFlgsave = true
                        }
                    }
                }
            }).catch(error => {
                console.log(error)
            })
            // 浦 不具合対応 2022/6/3 end
            this.tableData = []
            this.tableData_kari = []
            this.tableLoading = true
            this.getDetail(date)
        },

        // 戻るボタン押下時の処理
        closeA(){
            this.$router.go(-1);
        },

        // 保存ボタン押下時の処理
        modifyBtn(arg){
            // 勤務表に登録（グループメンバー取得　保存後）
            attendanceCreate(this.tableData ).then(res => {
                Message({
                    message: '勤務表を保存しました。',
                    type: 'success',
                    duration: 4 * 1000
                })
            }).catch(error => {
                console.log(error)
            })
        },

        // 申請ボタン押下時の処理
        applyopenreq(arg) {
            for (var i = 0 ;i < this.tableData.length;i++){
                if ((this.tableData[i].workKbn == '002' || this.tableData[i].workKbn == '003'
                  || this.tableData[i].workKbn == '005' || this.tableData[i].workKbn == '006')
                  && this.tableData[i].workSumTime == ''){
                    Message({
                        message: '休日出勤日または振替出勤日に出退勤時間の未入力がある為、申請できません。ご確認ください。',
                        type: 'error',
                        duration: 4 * 1000
                    })
                    return
                  }
            }
            // 勤務表画面の場合　⇒　承認者選択ダイアログ内の申請概要の入力欄の設定
            if(this.changebyFlg == "1"){
                this.remarkreq = Number(this.dayValue.split('/')[1])+'月分勤務申請'
            }
            // 申請一覧画面の場合　⇒　申請概要の入力欄を概要に表示
            else if(this.changebyFlg == "2"){
                this.remarkreq = this.rowdata.requestOverview
            }
            this.routerList = []
            // 勤務表画面の場合
            if(this.changebyFlg == "1"){
            let data = {
                companyCode: '000001',
                requestNumber: ''
            }
            // ワークフローテーブル（区分マスタ：033　グループメンバー取得）　承認者選択ダイアログが表示
            getWorkFlowList(data).then(res => {
                this.routerList = res.data
                this.openreq = true
            }).catch(error => {
                console.log(error)
            })
            // 申請一覧画面の場合または状態が差戻の場合
            }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                let data = {
                    companyCode: '000001',
                    requestNumber: this.rowdata.requestNumber,
                }
                // ワークフローテーブル（区分マスタ：033　グループメンバー取得）承認者選択ダイアログが表示
                getWorkFlowList(data).then(res => {
                    this.routerList = res.data
                    this.openreq = true
                }).catch(error => {
                    console.log(error)
                })
            }
        },

        // 承認ボタン押下時の処理
        applyopenapp(arg) {
            this.routerList = []
            let data = {
                companyCode: '000001',
                requestNumber: this.rowdata.requestNumber,
            }
            // ワークフローテーブル（区分マスタ：033　グループメンバー取得）承認者選択のダイアログ内　表示
            getWorkFlowList(data).then(res => {
                this.routerList = res.data
                this.openlast = true
            }).catch(error => {
                console.log(error)
            })
            // 管理者コード='999'　または　'550'　または　'500'　の以外の場合　⇒　最終承認が非表示
            if(this.permissionKbn_user !== '999' && this.permissionKbn_user !== '550' && this.permissionKbn_user !== '500'){
                this.openApproval = false
            }
        },

        // 承認＆次の人へ回覧ボタン押下時の処理
        admitwf(){
            // 社員番号＆社員名が空の場合　⇒　エラーメッセージが表示
            if(this.userId_req == ''){
                Message({
                    message: '承認者を選択してください',
                    type: 'error',
                    duration: 1000
                })
                return
            }
            this.getCurrentTime()
            let data = {
                companyCode: '000001',
                requestNumber: this.rowdata.requestNumber,
            }
            //　既存データの確認
            getWfList(data).then(res =>{
                //　ワークフローテーブルにデータがある場合
                if(res.data){
                    this.total = res.data.length
                //　ワークフローテーブルにデータがない場合
                }else{
                    this.total = 0
                }
                let dataadmitwf = {
                    companyCode:   '000001',
                    requestNumber: this.rowdata.requestNumber,
                    reqAppOrder:   this.total,
                    updateUser:    this.rowdata.reqAppUserId
                }
                // 承認済
                updateadmitWf(dataadmitwf).then((res) => {
                }).catch((error) => {
                    console.log(error)
                });
                let datainsertwf = {
                    companyCode:    '000001',
                    requestNumber:  this.rowdata.requestNumber,
                    reqAppOrder:    this.total + 1,
                    reqAppComment:  '',
                    reqAppUserId:   this.userId_req,
                    reqAppUpUserId: getUserInfo().userId
                }
                // 承認
                insertWf(datainsertwf).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
            }).catch(error => {
                console.log(error)
            })
            let datareqmail = {
                companyCode: '000001',
                userId: this.rowdata.requestUserId,
                userAppId: this.userId_req,
                userName: this.rowdata.userName,
                appName: this.name_req,
                reqMailKbn: '勤務申請',
                appMailKbn: '1',
                requestOverview: this.rowdata.requestOverview
            }
            // 勤務表申請　の場合　⇒　次の人へ回覧押下時の処理　602=エラーメッセージが表示,それ以外=メッセージが表示　
            sendreqMailApi(datareqmail).then(response => {
                // status が 602　の場合　⇒　エラーメッセージが表示
                if(response.status === 602){
                    Message({
                        message: response.msg,
                        type: 'error',
                        duration: 1500
                    })
                // それ以外　の場合  ⇒　メッセージが表示  
                }else{
                    Message({
                        message: 'メールを送信しました。',
                        type: 'success',
                        duration: 1500
                    })
                }
            }).catch(error => {
                console.log(error)
            })
            this.openlast = false
            this.changebyFlgwf = false
            this.changebyFlgapp = false
            // 処理の時間（一秒）
            setTimeout(()=>{
                this.$router.go(-1);
            },1000)
        },

        // 最終承認ボタン押下時の処理
        lastadmit(){
            let dataLareq = {
                companyCode: '000001',
                requestNumber: this.rowdata.requestNumber,
                updateUser: this.rowdata.reqAppUserId
            }
            // 申請テーブル（グループメンバー取得　(最終承認後)申請テーブルにデータ更新）
            updateLaRequest(dataLareq).then((res) => {
            })
            .catch((error) => {
                console.log(error)
            });
            this.getCurrentTime()
            let data = {
                companyCode: '000001',
                requestNumber: this.rowdata.requestNumber,
            }
            //　既存データの確認
            getWfList(data).then(res =>{
                //　ワークフローテーブルにデータがある場合
                if(res.data){
                    this.total = res.data.length
                //　ワークフローテーブルにデータがない場合　⇒　データ=0
                }else{
                    this.total = 0
                }
                let datawpdatewf = {
                    companyCode:   '000001',
                    requestNumber: this.rowdata.requestNumber,
                    reqAppOrder:   this.total,
                    updateUser:    this.rowdata.reqAppUserId
                }
                // 最終承認
                updatelastadmitWf(datawpdatewf)
                Message({
                    message: '最終承認しました。',
                    type: 'success',
                    duration: 1500
                })
            }).catch(error => {
                console.log(error)
            })
            let datasendreqmail = {
                companyCode: '000001',
                requestNumber: this.rowdata.requestNumber,
                userId: this.agentCode,
                userAppId: this.userId_req,
                userName: '',
                appName: this.userName,
                reqMailKbn: '勤務申請',
                appMailKbn: '3',
                requestOverview: this.remarkreq
            }
            // 勤務表申請（最終承認）　の場合　⇒　602=エラーメッセージが表示,それ以外=メッセージが表示
            sendreqMailApi(datasendreqmail).then(response => {
                // status　が 602　の場合　⇒　エラーメッセージが表示
                if(response.status === 602){
                    Message({
                        message: response.msg,
                        type: 'error',
                        duration: 1500
                    })
                // それ以外　の場合　⇒　メッセージが表示
                }else{
                    Message({
                        message: 'メールを送信しました。',
                        type: 'success',
                        duration: 1500
                    })
                }
            }).catch(error => {
                console.log(error)
            })
            this.openlast = false
            this.changebyFlgwf = false
            this.changebyFlgapp = false
            // 処理の時間（一秒）
            setTimeout(()=>{
            this.$router.go(-1);
            },1000)
        },

        // 差戻ボタン押下時の処理
        applyopenwf(){
            this.openwf = true
        },
        // 差戻処理（確定ボタン押下時の処理） 浦修正20220608 start
        returnRes(){
            // 差戻理由入力欄が空の場合　⇒　エラーメッセージが表示
            if(this.appcomment == ''){
                Message({
                    message: '差戻理由を入力してください',
                    type: 'error',
                    duration: 1000
                })
                return
            }
            this.getCurrentTime()
            let data = {
                companyCode: '000001',
                requestNumber: this.rowdata.requestNumber,
            }
            //　既存データの確認
            getWfList(data).then(res =>{
                this.wfList = res.data
                let arr = []
                for(let i = 0; i < this.wfList.length;i++) {
                    if(this.wfList[i].reqAppUserId == getUserInfo().userId && this.wfList[i].reqAppKbn == '002') {
                    // if(this.wfList[i].reqAppUserId == getUserInfo().userId) {
                        console.log('this.wfList[i].reqAppOrder', this.wfList[i].reqAppOrder)
                        arr.push(this.wfList[i].reqAppOrder)
                    }
                }
                console.log('arr', arr)
                this.reviseReqAppOrder = Math.max(...arr)
                console.log('this.reviseReqAppOrder', this.reviseReqAppOrder)

                let dataupdatewf = {
                    companyCode: '000001',
                    requestNumber: this.rowdata.requestNumber,
                    reqAppOrder: this.reviseReqAppOrder,
                    reqAppComment: this.appcomment,
                    updateUser: this.rowdata.reqAppUserId,
                    reqAppDatetime: new Date(),
                }
                // 差戻する承認者のレコードを差戻に更新
                updateWf(dataupdatewf).then((res) => {
                    let deleteList = []
                    for(let i = 0; i < this.wfList.length;i++){
                        // 今のワークフローテーブルよりワークフローテーブルが大きい場合　⇒　データを取得する（ワークフローテーブル更新）
                        if(this.wfList[i].reqAppOrder > this.reviseReqAppOrder){
                            let deleteData = {
                                companyCode: this.rowdata.companyCode,
                                requestNumber: this.rowdata.requestNumber,
                                reqAppOrder: this.wfList[i].reqAppOrder
                            }
                            deleteList.push(deleteData)
                        }
                    }
                    // 差戻する承認者のレコード以降のデータを全て削除
                    deleteDataWf(deleteList).then((res) => {
                    })
                    .catch((error) => {
                        console.log(error)
                    });
                    // 申請テーブルの状態を差戻に更新
                    let dataskreq = {
                        companyCode: '000001',
                        requestNumber: this.rowdata.requestNumber,
                        updateUser: this.rowdata.reqAppUserId
                    }
                    //申請テーブル（グループメンバー取得　(差戻後)申請テーブルにデータ更新）
                    updateSkRequest(dataskreq).then((res) => {
                        Message({
                            message: '差し戻しました。',
                            type: 'success',
                            duration: 1500
                        })
                        let datasendmail = {
                            companyCode: '000001',
                            requestNumber: this.rowdata.requestNumber,
                            userId: this.agentCode,
                            userAppId: this.userId_req,
                            userName: '',
                            appName: getUserInfo().userName,
                            reqMailKbn: '勤務申請',
                            appMailKbn: '2',
                            requestOverview: this.remarkreq,
                            reqAppComment:  this.appcomment
                        }
                        // 勤務表申請（差戻）　の場合　⇒　次の人へ回覧押下時の処理　602=エラーメッセージが表示,それ以外=メッセージが表示
                        sendreqMailApi(datasendmail).then(response => {
                            // status が 602　の場合　⇒　エラーメッセージが表示
                            if(response.status === 602){
                                Message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1500
                                })
                            // それ以外　の場合　⇒　メッセージが表示
                            }else{
                                Message({
                                    message: 'メールを送信しました。',
                                    type: 'success',
                                    duration: 1500
                                })
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                        this.openwf = false
                        this.changebyFlgwf = false
                        this.changebyFlgapp = false
                    })
                    .catch((error) => {
                        console.log(error)
                    });
                })
                .catch((error) => {
                    console.log(error)
                });
            })
            .catch((error) => {
                console.log(error)
            });
            // 処理の時間（一秒）
            setTimeout(()=>{
                this.$router.go(-1);
            },1000)
        },
        // 差戻処理 浦修正20220608 end

        // 取戻ボタン押下時の処理
        applyopende(){
            this.opende = true
        },
        // 取戻（確定ボタン押下時の処理）  浦修正20220606 start
        deleteRes(){
            let data = {
                companyCode: this.rowdata.companyCode,
                requestNumber: this.rowdata.requestNumber,
            }
            // ワークフロー全削除
            deleteAllWf(data).then(res =>{
                // 申請テーブル（グループメンバー取得　申請テーブルにデータ削除）
                deleteRequest(data).then((res) => {
                    let datadeletereqatt = {
                        companyCode: "000001",
                        userId: getUserInfo().userId,
                        workDateYm: String(this.mYear) + String(this.mMonth),
                    }
                    // 勤務表申請テーブル（グループメンバー取得 勤務申請テーブルにデータ削除) 
                    deleteRequestAttendance(datadeletereqatt).then((res) => {
                        Message({
                            message: '取り戻しました。',
                            type: 'success',
                            duration: 1500
                        })
                    }).catch((error) => {
                        console.log(error)
                    });
                }).catch((error) => {
                    console.log(error)
                });
            }).catch(error => {
                console.log(error)
            })
            // 処理の時間（一秒）
            setTimeout(()=>{
                this.$router.go(-1);
            },1000)
        },
        // 取戻  浦修正20220606 end

        // 次の人へ回覧ボタン押下時の処理
        insertRes(){
            // 社員番号＆社員名の入力欄が空の場合　⇒　エラーメッセージが表示
            if(this.userId_req == ''){
                Message({
                    message: '承認者を選択してください',
                    type: 'error',
                    duration: 1000
                })
                return
            }
            // 勤務表画面の場合　⇒ データ取得　ワークフローテーブルに取得＆登録
            if(this.changebyFlg == "1"){
                this.getCurrentTime()
                let data40 = {
                    companyCode: '000001',
                    requestNumber: this.aTime + '000001' + this.agentCode,
                }
                //　既存データの確認
                getWfList(data40).then(res =>{
                    //　ワークフローテーブルにデータがある場合
                    if(res.data){
                        this.total = res.data.length
                    //　ワークフローテーブルにデータがない場合　⇒　データ=0
                    }else{
                        this.total = 0
                    }
                    let datainswf = {
                        companyCode:      '000001',
                        requestNumber:    this.aTime + '000001' + this.agentCode,
                        total:            this.total,
                        reqAppComment:    '',
                        reqRequestUserId: this.agentCode,
                        reqApproveUserId: this.userId_req
                    }
                    //　最初に作るデータ（申請,承認）
                    insertNewWf(datainswf).then(res =>{
                    }).catch(error => {
                        console.log(error)
                    })
                }).catch(error => {
                    console.log(error)
                })
                let datainsertreq = {
                    companyCode: '000001',
                    requestNumber: this.aTime + '000001' + this.agentCode,
                    requestUserId: this.agentCode,
                    requestDate: this.yMd,
                    requestAypeKbn: '',
                    requestOverview: this.remarkreq,
                    requestStatusKbn: '003'
                }
                // 申請テーブル（グループメンバー取得　申請/申請状態区分＝回覧中：003　(再申請の場合)申請テーブルにデータ登録）
                insertRequest(datainsertreq).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
                let datainsertreqatt = {
                    companyCode: '000001',
                    requestNumber: this.aTime + '000001' + this.agentCode,
                    userId: this.agentCode,
                    workDateYm:String(this.mYear) + String(this.mMonth)
                }
                // 勤務表申請テーブル（グループメンバー取得 勤務申請テーブルにデータ登録）
                insertRequestAttendance(datainsertreqatt).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
                // 勤務表に登録（グループメンバー取得　申請の後）
                attendanceCreate(this.tableData).then(res => {
                    Message({
                        message: '勤務表を申請しました。',
                        type: 'success',
                        duration: 1500
                    })
                    let datareqmailapi = {
                        companyCode: '000001',
                        userId: this.agentCode,
                        userAppId: this.userId_req,
                        userName: this.userName,
                        appName: this.name_req,
                        reqMailKbn: '勤務申請',
                        appMailKbn: '1',
                        requestOverview: this.remarkreq
                    }
                    // 勤務表申請（回覧中）　の場合　⇒　次の人へ回覧押下時の処理　602=エラーメッセージが表示,それ以外=メッセージが表示　
                    sendreqMailApi(datareqmailapi).then(response => {
                        // status が 602　の場合　⇒　エラーメッセージが表示
                        if(response.status === 602){
                            Message({
                                message: response.msg,
                                type: 'error',
                                duration: 1500
                            })
                        // それ以外　の場合　⇒　メッセージが表示
                        }else{
                            Message({
                                message: 'メールを送信しました。',
                                type: 'success',
                                duration: 1500
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }).catch(error => {
                    console.log(error)
                })
                this.openreq = false
                this.reqFlg = false
                this.modifyFlgsave = false
            // 申請一覧画面の場合　または　状態が差戻の場合　⇒　データ取得　ワークフローテーブルに取得＆登録　　
            }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                this.getCurrentTime()
                let data4 = {
                    companyCode: '000001',
                    requestNumber: this.rowdata.requestNumber,
                }
                //　既存データの確認
                getWfList(data4).then(res =>{
                    //　ワークフローテーブルにデータがある場合
                    if(res.data){
                        this.total = res.data.length
                    //　ワークフローテーブルにデータがない場合　⇒　データ=0
                    }else{
                        this.total = 0
                    }
                    let data2 = {
                        companyCode:      '000001',
                        requestNumber:    this.rowdata.requestNumber,
                        total:            this.total,
                        reqAppComment:    '',
                        reqRequestUserId: this.agentCode,
                        reqApproveUserId: this.userId_req
                    }
                    //　最初に作るデータ（申請,承認）
                    insertNewWf(data2).then(res =>{
                    }).catch(error => {
                        console.log(error)
                    })
                }).catch(error => {
                    console.log(error)
                })
                let data5 = {
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                    requestOverview: this.remarkreq,
                    UserId: this.rowdata.requestUserId,
                    UserName: this.userName,
                    requestUserId: getUserInfo().userId
                }
                // 申請テーブル（グループメンバー取得　申請/申請状態区分＝回覧中：003　(再申請の場合)申請テーブルにデータ登録）
                insertRequest(data5).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
                // 勤務表に登録（グループメンバー取得　申請の後）
                attendanceCreate(this.tableData ).then(res => {
                    Message({
                        message: '勤務表を申請しました。',
                        type: 'success',
                        duration: 1500
                    })
                    let datasendM = {
                        companyCode: '000001',
                        userId: this.rowdata.requestUserId,
                        userAppId: this.userId_req,
                        userName: getUserInfo().userName,
                        appName: this.name_req,
                        reqMailKbn: '勤務申請',
                        appMailKbn: '1',
                        requestOverview: this.rowdata.requestOverview
                    }
                    // 勤務表申請（回覧中）　の場合　⇒　次の人へ回覧押下時の処理　602=エラーメッセージが表示,それ以外=メッセージが表示　
                    sendreqMailApi(datasendM).then(response => {
                        // status が 602　の場合　⇒　エラーメッセージが表示
                        if(response.status === 602){
                            Message({
                                message: response.msg,
                                type: 'error',
                                duration: 1500
                            })
                        // それ以外　の場合　⇒　メッセージが表示
                        }else{
                            Message({
                                message: 'メールを送信しました。',
                                type: 'success',
                                duration: 1500
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }).catch(error => {
                    console.log(error)
                })
                this.openreq = false
                this.reqFlg = false
                this.modifyFlgsave = false
            }
        },

        // 会社コード,社員番号,社員名,所属名,管理者コードを取得
        userInfoInit(){
            this.companyCode_user = getUserInfo().companyCode
            this.agentCode = getUserInfo().userId
            this.userName = getUserInfo().userName
            this.departmentName = getUserDetailedInfo().departmentName
            this.permissionKbn_user = getUserInfo().permissionKbn
        },
        //　区分リスト取得　
        kbnListSee(data){
            for(let i = 0; i < this.kbnList.length; i++){
                // 区分リストの区分コードと勤務区分が同じ　の場合　⇒　区分名を返す　
                if(this.kbnList[i].categoryKbn == data){
                    return this.kbnList[i].categoryKbnName
                }
            }
            return ''
        },
        // 年,月,曜日,日にち取得
        getFirstDayByMonths(year,month){
            let firstMonthDate = new Date(year , month , 1);
            let weekDays = ['日','月','火','水','木','金','土'];
            let date = weekDays[firstMonthDate.getDay()]
            return date;
        },
        // 現在の年,月,日にち,時間,分,秒,ミリ秒　に設定
        getCurrentTime(){
            let yy = String(new Date().getFullYear())
            let mm = String((new Date().getMonth()+1) <10 ? '0' + (new Date().getMonth()+1) : (new Date().getMonth()+1))
            let dd = String(new Date().getDate() <10 ? '0' + new Date().getDate() : new Date().getDate());
            let hh = String(new Date().getHours() <10 ? '0' + new Date().getHours() : new Date().getHours());
            let mf = String(new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes());
            let ss = String(new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds());
            let ms = String(new Date().getMilliseconds()<10 ? '00'+new Date().getMilliseconds() : new Date().getMilliseconds()<100 ? '0'+new Date().getMilliseconds() : new Date().getMilliseconds());
            this.aTime = yy+mm+dd+hh+mf+ss+ms;
            this.allTimeType = yy + '/' + mm + '/' + dd + ' ' + hh + ':' + mf;
            this.yM = yy+mm
            this.yMd = yy+mm+dd
        },

        // 出勤時間１,退勤時間１　（時間.最小5:00～最大29:00）
        floorTime(time) {
            // 出勤時間　または　退勤時間が空の場合　⇒　値が空
            if(time == ''){
                return ''
            }
            let timeSum = this.minuteChange(time)
            //　時間の合計値が0　の場合　⇒　数値タイプの分を時間文字列に変換
            if(timeSum % 15 == 0){
                return this.minuteFormat(timeSum)
            //　それ以外　の場合　⇒　数値タイプの分を時間文字列に変換＋計算追加
            }else{
                return this.minuteFormat(timeSum - (timeSum % 15))
            }
        },
        // 退勤時間１
        ceilTime(time) {
            // 退勤時間が空の場合 　⇒　値が空　
            if(time == ''){
                return ''
            }
            let timeSum = this.minuteChange(time)
            // 時間の合計値が0　の場合　⇒　数値タイプの分を時間文字列に変換
            if(timeSum % 15 == 0){
                return this.minuteFormat(timeSum)
            // それ以外　の場合　⇒　数値タイプの分を時間文字列に変換＋計算追加 
            }else{
                return this.minuteFormat(timeSum + 15 - (timeSum % 15))
            }
        },
        // 実働勤務時間 start1（12:00）, end1(13:00), start2(18:00), end2(18:30), start3(22:00), end3(22:30)
        allTime(start1, end1, start2, end2, start3, end3, rest){
            let all1 = this.minuteChange(end1) - this.minuteChange(start1)
            let all2 = 0
            let all3 = 0
            // 出勤18:00が空,null以外 または 退勤18:30が空,null　の場合
            if(start2 == '' || start2 == null || end2 == '' || end2 == null){
                all2 = 0
            // 出勤18:00が空,null以外 または 退勤18:30が空,null以外の場合 以外の場合
            }else{
                all2 = this.minuteChange(end2) - this.minuteChange(start2)
            }
            // 出勤22:00が空,null以外 または 退勤22:30が空,null　の場合
            if(start3 == '' || start3 == null || end3 == '' || end3 == null){
                all3 = 0
            // 出勤22:00が空,null以外 または 退勤22:30が空,null　以外の場合
            }else{
                all3 = this.minuteChange(end3) - this.minuteChange(start3)
            }
            let a =  this.minuteFormat(all1 + all2 + all3 - this.minuteChange(rest))
            return a
        },
        // 休憩時間１,休憩時時間２,休憩時間３の設定  start1（12:00）, end1(13:00)
        restTime2(start1, end1){
            let startRest1 = this.minuteChange(this.settingInfo.workBreakStart1)
            let startRestEnd1 = this.minuteChange(this.settingInfo.workBreakEnd1)
            let startRest2 = this.minuteChange(this.settingInfo.workBreakStart2)
            let startRestEnd2 = this.minuteChange(this.settingInfo.workBreakEnd2)
            let startRest3 = this.minuteChange(this.settingInfo.workBreakStart3)
            let startRestEnd3 = this.minuteChange(this.settingInfo.workBreakEnd3)
            let startTime1 = this.minuteChange(start1)
            let endTime1 = this.minuteChange(end1)
            let rest = []  
            rest = this.whereTime(startTime1,endTime1, startRest1, startRestEnd1, startRest2, startRestEnd2, startRest3, startRestEnd3)
            return rest
        },
        // 休憩時間１,休憩時時間２,休憩時間３の時間設定  cStart（出勤）, cEnd（退勤）startRest1（12:00）, startRestEnd1(13:00), start2(18:00), startRestEnd2(18:30), start3(22:00), startRest3(22:30)         
        whereTime(cStart, cEnd, startRest1, startRestEnd1, startRest2, startRestEnd2, startRest3, startRestEnd3){
            let rest = 0
            let restArr = []
            // 出勤時間 が 12:00より前　の場合　⇒　条件によって休憩時間を設定し取得する
            if(cStart <= startRest1){
                // 退勤時間  が　12:00より前　の場合　⇒　休憩時間 = 0に設定
                if(cEnd <= startRest1){
                    rest = 0
                    restArr.push(rest)
                    restArr.push(0)
                    restArr.push(0)
                // 退勤時間　が　12:00の後　かつ　退勤時間 が 13:00　の場合　⇒　休憩時間　= 退勤時間-12:00に設定
                }else if(cEnd > startRest1 && cEnd <= startRestEnd1){
                    rest = cEnd - startRest1
                    restArr.push(rest)
                    restArr.push(0)
                    restArr.push(0)
                // 退勤時間　が　13:00の後　かつ　退勤時間　が 18:00　の場合　⇒　休憩時間　= 13:00-12:00に設定
                }else if(cEnd > startRestEnd1 && cEnd <= startRest2){
                    rest = startRestEnd1 - startRest1
                    restArr.push(rest)
                    restArr.push(0)
                    restArr.push(0)
                // 退勤時間　が　 18:00の後　かつ 退勤時間　が　18:30　の場合　⇒　休憩時間　= 13:00-12:00,退勤時間-18:00に設定
                }else if(cEnd > startRest2 && cEnd <= startRestEnd2){
                    rest = startRestEnd1 - startRest1 + cEnd - startRest2
                    restArr.push(startRestEnd1 - startRest1)
                    restArr.push(cEnd - startRest2)
                    restArr.push(0)
                // 退勤時間　が　 18:30の後　かつ 退勤時間　が　22:00　の場合　⇒　休憩時間　= 13:00-12:00,18:30-18:00に設定
                }else if(cEnd > startRestEnd2 && cEnd <= startRest3){
                    rest = startRestEnd1 - startRest1 + startRestEnd2 - startRest2
                    restArr.push(startRestEnd1 - startRest1)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(0)
                // 退勤時間　が　 22:00の後　かつ 退勤時間　が　22:30　の場合　⇒　休憩時間　= 13:00-12:00,18:30-18:00,退勤時間-22:00に設定
                }else if(cEnd > startRest3 && cEnd <= startRestEnd3){
                    rest = startRestEnd1 - startRest1 + startRestEnd2 - startRest2 + cEnd - startRest3
                    restArr.push(startRestEnd1 - startRest1)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(cEnd - startRest3)
                // 退勤時間　が　22:30の後　の場合　⇒　休憩時間　= 13:00-12:00,18:30-18:00,22:00-22:30に設定
                }else if(cEnd > startRestEnd3){
                    rest = startRestEnd1 - startRest1 + startRestEnd2 - startRest2 + startRestEnd3 - startRest3
                    restArr.push(startRestEnd1 - startRest1)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(startRestEnd3 - startRest3)
                }
            // 出勤時間　が　12:00の後　かつ　出勤時間 が 13:00　の場合　⇒　条件によって休憩時間を設定し取得する
            }else if(cStart >= startRest1 && cStart < startRestEnd1){
                // 退勤時間　が　12:00の後　かつ　退勤時間 が 13:00　の場合　⇒　休憩時間　= 退勤時間-12:00に設定
                if(cEnd > startRest1 && cEnd <= startRestEnd1){
                    rest = cEnd - cStart
                    restArr.push(cEnd - cStart)
                    restArr.push(0)
                    restArr.push(0)
                // 退勤時間　が　13:00の後　かつ　退勤時間　が 18:00　の場合　⇒　休憩時間　= 13:00-12:00に設定
                }else if(cEnd > startRestEnd1 && cEnd <= startRest2){
                    rest = startRestEnd1 - startRest1
                    restArr.push(startRestEnd1 - cStart)
                    restArr.push(0)
                    restArr.push(0)
                // 退勤時間　が　 18:00の後　かつ 退勤時間　が　18:30　の場合　⇒　休憩時間　= 13:00-12:00,退勤時間-18:00に設定
                }else if(cEnd > startRest2 && cEnd <= startRestEnd2){
                    rest =   cEnd - startRest2
                    restArr.push(startRestEnd1 - cStart)
                    restArr.push(cEnd - startRest2)
                    restArr.push(0)
                // 退勤時間　が　 18:30の後　かつ 退勤時間　が　22:00　の場合　⇒　休憩時間　= 13:00-12:00,18:30-18:00に設定
                }else if(cEnd > startRestEnd2 && cEnd <= startRest3){
                    rest =  startRestEnd2 - startRest2
                    restArr.push(startRestEnd1 - cStart)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(0)
                // 退勤時間　が　 22:00の後　かつ 退勤時間　が　22:30　の場合　⇒　休憩時間　= 13:00-12:00,18:30-18:00,退勤時間-22:00に設定
                }else if(cEnd > startRest3 && cEnd <= startRestEnd3){
                    rest = startRestEnd2 - startRest2 + cEnd - startRest3
                    restArr.push(startRestEnd1 - cStart)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(cEnd - startRest3)
                // 退勤時間　が　22:30の後　の場合　⇒　休憩時間　= 13:00-12:00,18:30-18:00,22:00-22:30に設定
                }else if(cEnd > startRestEnd3){
                    rest = startRestEnd1 - startRest1 + startRestEnd2 - startRest2 + startRestEnd3 - startRest3
                    restArr.push(startRestEnd1 - cStart)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(startRestEnd3 - startRest3)
                }
            // 出勤時間　が　13:00の後　かつ　出勤時間 が 18:00　の場合　⇒　条件によって休憩時間を設定し取得する
            }else if(cStart >= startRestEnd1 && cStart <= startRest2){
                // / 退勤時間　が　13:00の後　かつ　退勤時間　が 18:00　の場合　⇒　休憩時間　= 0に設定
                if(cEnd > startRestEnd1 && cEnd <= startRest2){
                    rest = 0
                    restArr.push(0)
                    restArr.push(0)
                    restArr.push(0)
                // 退勤時間　が　 18:00の後　かつ 退勤時間　が　18:30　の場合　⇒　休憩時間　= 退勤時間-18:00に設定
                }else if(cEnd > startRest2 && cEnd <= startRestEnd2){
                    rest = cEnd - startRest2
                    restArr.push(0)
                    restArr.push(cEnd - startRest2)
                    restArr.push(0)
                // 退勤時間　が　 18:30の後　かつ 退勤時間　が　22:00　の場合　⇒　休憩時間　= 18:30-18:00に設定
                }else if(cEnd > startRestEnd2 && cEnd <= startRest3){
                    rest =  startRestEnd2 - startRest2
                    restArr.push(0)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(0)
                // 退勤時間　が　 22:00の後　かつ 退勤時間　が　22:30　の場合　⇒　休憩時間　= 18:30-18:00,退勤時間-22:00に設定
                }else if(cEnd > startRest3 && cEnd <= startRestEnd3){
                    rest = startRestEnd2 - startRest2 + cEnd - startRest3
                    restArr.push(0)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(cEnd - startRest3)
                // 退勤時間　が　22:30の後　の場合　⇒　休憩時間　= 18:30-18:00,22:00-22:30に設定
                }else if(cEnd > startRestEnd3){
                    rest = startRestEnd2 - startRest2 + startRestEnd3 - startRest3
                    restArr.push(0)
                    restArr.push(startRestEnd2 - startRest2)
                    restArr.push(startRestEnd3 - startRest3)
                }
            // 出勤時間　が　18:00の後　かつ　出勤時間 が 18:30　の場合　⇒　条件によって休憩時間を設定し取得する
            }else if(cStart >= startRest2 && cStart < startRestEnd2){
                // 退勤時間　が　 18:00の後　かつ 退勤時間　が　18:30　の場合　⇒　休憩時間　= 退勤時間-18:00に設定
                if(cEnd > startRest2 && cEnd <= startRestEnd2){
                    rest =  cEnd - cStart
                    restArr.push(0)
                    restArr.push(cEnd - cStart)
                    restArr.push(0)
                // 退勤時間　が　 18:30の後　かつ 退勤時間　が　22:00　の場合　⇒　休憩時間　= 18:30-18:00に設定
                }else if(cEnd > startRestEnd2 && cEnd <= startRest3){
                    rest =  startRestEnd2 - startRest2
                    restArr.push(0)
                    restArr.push(startRestEnd2 - cStart)
                    restArr.push(0)
                // 退勤時間　が　 22:00の後　かつ 退勤時間　が　22:30　の場合　⇒　休憩時間　= 18:30-18:00,退勤時間-22:00に設定
                }else if(cEnd > startRest3 && cEnd <= startRestEnd3){
                    rest = cEnd - startRest3
                    restArr.push(0)
                    restArr.push(startRestEnd2 - cStart)
                    restArr.push(cEnd - startRest3)
                // 退勤時間　が　22:30の後　の場合　⇒　休憩時間　= 18:30-18:00,22:00-22:30に設定
                }else if(cEnd > startRestEnd3){
                    rest = startRestEnd2 - startRest2 + startRestEnd3 - startRest3
                    restArr.push(0)
                    restArr.push(startRestEnd2 - cStart)
                    restArr.push(startRestEnd3 - startRest3)
                }
            // 出勤時間　が　18:30の後　かつ　出勤時間 が 22:00　の場合　⇒　条件によって休憩時間を設定し取得する
            }else if(cStart > startRestEnd2 && cStart <= startRest3){
                // 退勤時間　が　 18:30の後　かつ 退勤時間　が　22:00　の場合　⇒　休憩時間　= 0に設定
                if(cEnd > startRestEnd2 && cEnd <= startRest3){
                    rest =  0
                    restArr.push(0)
                    restArr.push(0)
                    restArr.push(0)
                // 退勤時間　が　 22:00の後　かつ 退勤時間　が　22:30　の場合　⇒　休憩時間　= 退勤時間-22:00に設定
                }else if(cEnd > startRest3 && cEnd <= startRestEnd3){
                    rest =  cEnd - startRest3
                    restArr.push(0)
                    restArr.push(0)
                    restArr.push(cEnd - startRest3)
                // 退勤時間　が　22:30の後　の場合　⇒　休憩時間　= 18:30-18:00,22:00-22:30に設定
                }else if(cEnd > startRestEnd3){
                    rest =  startRestEnd3 - startRest3
                    restArr.push(0)
                    restArr.push(0)
                    restArr.push(startRestEnd3 - startRest3)
                }
            // 出勤時間　が　22:00の後　かつ　出勤時間 が 22:30　の場合　⇒　条件によって休憩時間を設定し取得する
            }else if(cStart > startRest3 && cStart <= startRestEnd3){
                // 退勤時間　が　 22:00の後　かつ 退勤時間　が　22:30　の場合　⇒　休憩時間　= 退勤時間-22:00に設定
                if(cEnd > startRest3 && cEnd <= startRestEnd3){
                    rest =  cEnd - cStart
                    restArr.push(0)
                    restArr.push(0)
                    restArr.push(cEnd - cStart)
                // 退勤時間　が　22:30の後　の場合　⇒　休憩時間　= 22:00-22:30に設定
                }else if(cEnd > startRestEnd3){
                    rest =  startRestEnd3 - startRest3
                    restArr.push(0)
                    restArr.push(0)
                    restArr.push(startRestEnd3 - cStart)
                }
            // 出勤時間 が 22:30の後　の場合　⇒　休憩時間　= 0に設定
            }else if(cStart > startRestEnd3){
                rest =  0
                restArr.push(0)
                restArr.push(0)
                restArr.push(0)
            }
            return restArr
        },
        // 時間の合計
        timeSum(time1 , time2, time3){
            var time = this.minuteFormat(this.minuteChange(time2) - this.minuteChange(time1) - this.minuteChange(time3));
            return time;
        },
        // 时间字符串转化为数字类型分钟（時間文字列を数値タイプの分に変換します）
        minuteChange(time){
            var time_h;
            var time_m;
            var minute;
            // 時間が　空　または　nullの場合　⇒　値が'0'
            if(time == '' || time == null){
                return 0
            }
            time_h = time.split(':')[0];
            time_m = time.split(':')[1];
            //　分'0'　かつ　分が二桁　が同じ　の場合　⇒　数値タイプの分に変更
            if(time_h.substring(0,1) == '0' && time_h.length == 2){
                time_h = time_h.substring(1,2);
            }
            minute = Number(time_h) * 60 + Number(time_m);
            return minute;
        },
        // 数字类型分钟转化为时间字符串（数値タイプの分を時間文字列に変換します）
        minuteFormat(time){
            // 時間が0　の場合　⇒　値が空　時間は60分,分は60秒
            if(time == 0){
                return ""
            }
            let time_h = String(Math.floor(time / 60));
            let time_m = String(time % 60);
            let minute;
            // 時間　または　分　が空の場合　⇒　値が空
            if(isNaN(time_h) || isNaN(time_m)){
                minute = "";
            // 時間　または　分　が空、以外の場合　
            }else{
                //　分の数値タイプが(0,1)と"-"が同じ　または　分の数値タイプ（1,2）と"-"が同じ　⇒　分が空
                if(time_h.substring(0,1) == "-" || time_h.substring(1,2) == "-"){
                    minute = ""
                // それ以外　の場合　0を頭に足す　時間:分に表示
                }else{
                    // 一桁（時間)　の場合　⇒　0を頭に足す（'0' + 時間）
                    if(time_h.length == 1){
                        time_h = "0" + time_h;
                    }
                    // 一桁（分)　の場合　⇒　0を頭に足す（'0' + 分）
                    if(time_m.length == 1){
                        time_m = "0" + time_m;
                    }
                    minute = time_h + ":" + time_m;
                }
            }
            return minute;
        },
        // 出勤時間（休憩時間1,休憩時間2,休憩時間3,実働勤務時間）
        startTimeFormat(time){
            let time_h;
            let time_m;
            // 時間が　空　または　null　の場合　⇒　値が空
            if(time == '' || time == null){
                return ''
            }
            time_h = time.split(':')[0];
            time_m = time.split(':')[1];
            // TODO：
            if(this.minuteChange(this.settingInfo.workStartTime) - this.minuteChange(time) < this.kbnStartTime&& this.minuteChange(this.settingInfo.workStartTime) > this.minuteChange(time)){
                return this.settingInfo.workStartTime;
            }
            return time_h + ':' + time_m
        },
        // 退勤時間（休憩時間1,休憩時間2,休憩時間3,実働勤務時間）
        endTimeFormat(time){
            let time_h;
            let time_m;
            //　時間が　空　または　null　の場合　⇒　値が空　
            if(time == '' || time == null){
                return ''
            }
            time_h = time.split(':')[0];
            time_m = time.split(':')[1];
            return time_h + ':' + time_m
        },

        // 勤務表詳細情報
        getDetail(date){
            // 勤务表详细取得（スケジュールを詳しく知る）
            getAttendanceDetail(date).then(res => {
                this.tableData_kari = res.data;
                for(let i = 0; i < this.tableData_kari.length; i++){
                    //  勤務区分　が　欠勤(010)　または　休職(070)　または　年末年始(080)　の場合　⇒ 出勤時間が非活性,退勤時間が非活性
                    if(this.tableData_kari[i].workKbn == '010' || this.tableData_kari[i].workKbn == '070' || this.tableData_kari[i].workKbn == '080'){
                        this.tableData_kari[i].workStart1 = ''
                        this.tableData_kari[i].workEnd1 = ''
                        this.tableData_kari[i].workStart1Flg = true                  
                        this.tableData_kari[i].workEnd1Flg = true
                    }
                }
                let hDate = {
                    companyCode: this.companyCode_user,
                    holidayYear: this.mYear
                }
                this.getHolidayDate(hDate)
                this.applyAttendance()
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
        },
        // 休日（祝日）情報
        getHolidayDate(date){
            // 勤务表休日情报取得（ワークシートと休日に関する情報の取得）
            getHoliday(date).then(res => {
                this.holidayDate = []
                for(let i = 0; i < res.data.length; i++){
                    // 休日（祝日）月の情報と現在の月が同じ　の場合　⇒　holidayDateに休日（祝日）のデータを代入
                    if(res.data[i].holidayMonth == this.mMonth){
                        this.holidayDate.push(res.data[i].holidayDay)
                    }
                }
                let kbnDate = {
                    companyCode: this.companyCode_user,
                    categoryClassNumber: '010' //勤務区分
                }
                this.getACategory(kbnDate)
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            });
        },
        // 勤務表出退勤情報（出退勤回数=1or3)
        getACategory(date){
            // 区分マスタ（グループメンバー取得）
            getCategory(date).then(res => {
                this.kbnList = res.data
                // 区分マスタの情報　の場合　⇒　区分コード,区分名が空
                if(this.kbnList){
                    let blockArr = { categoryKbn: '', categoryKbnName: ''}
                    this.kbnList.unshift(blockArr)
                }
                let kbnTDate = {
                    companyCode: this.companyCode_user,
                    categoryClassNumber: '012' // 出退勤回数=1or3
                }
                this.getBCategory(kbnTDate)
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
        },
        // 勤務表出退勤 （深夜の判断時間,始業前取り扱い外〇分(30分))
        getBCategory(date){
            // 区分マスタ（グループメンバー取得）
            getCategory(date).then(res => {
                this.flgKbn = res.data
                let kbnTDate = {
                    companyCode: this.companyCode_user,
                    categoryClassNumber: '011' // （深夜の判断時間,始業前取り扱い外〇分(30分))
                }
                this.getTCategory(kbnTDate)
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
        },
        //  区分マスタ（グループメンバー取得）
        getTCategory(date){
            // 区分マスタ（グループメンバー取得）
            getCategory(date).then(res => {
                this.kbnToday = res.data[0].categoryKbnName
                this.kbnNight = res.data[1].categoryKbnName
                this.kbnStartTime = res.data[2].categoryKbnName
                this.kbnEndTime = res.data[3].categoryKbnName
                let settingDate = {
                    companyCode: this.companyCode_user,
                    userId: this.agentCode,
                    applicationDate: String(this.mYear) + this.mMonth
                }
                this.getSettingInfo(settingDate)
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
        },
        // 詳細情報（計算単位,所定時間,始業時間,休憩時間）
        getSettingInfo(settingDate){
            // 勤务表设定情报取得(スケジュール設定に関する情報の取得)
            getAtndStting(settingDate).then(res => {
                this.settingInfo = []
                // データが　空　または　null の場合　⇒　テーブルが非表示され、エラーメッセージが表示
                if(res.data == '' || res.data == null){
                    this.tableData = []
                    this.tableLoading = false
                    Message({
                        message: 'データ不正',
                        type: 'error',
                        duration: 4 * 1000
                    })
                //  それ以外　の場合　　
                }else{
                    this.settingInfo = res.data[res.data.length - 1]
                    this.settingRestTime1 = this.isNullInfo(this.settingInfo.workBreakStart1, this.settingInfo.workBreakEnd1)
                    this.settingRestTime2 = this.isNullInfo(this.settingInfo.workBreakStart2, this.settingInfo.workBreakEnd2)
                    this.settingRestTime3 = this.isNullInfo(this.settingInfo.workBreakStart3, this.settingInfo.workBreakEnd3)
                    this.restFlg1 = this.settingInfo.workBreakStart1 == '' || this.settingInfo.workBreakStart1 == null || this.settingInfo.workBreakEnd1 == '' || this.settingInfo.workBreakEnd1 == null ? false :true,
                    this.restFlg2 = this.settingInfo.workBreakStart2 == '' || this.settingInfo.workBreakStart2 == null || this.settingInfo.workBreakEnd2 == '' || this.settingInfo.workBreakEnd2 == null ? false :true,
                    this.restFlg3 = this.settingInfo.workBreakStart3 == '' || this.settingInfo.workBreakStart3 == null || this.settingInfo.workBreakEnd3 == '' || this.settingInfo.workBreakEnd3 == null ? false :true,
                    this.tableDataComplete()
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
        },

        // 2022/06/10　加賀田　start　勤務区分の選択押下時のチェンジ処理　空＆エラーメッセージが表示
        changeEvent(e){
            let categoryName = ''//宣言「名前を決める」
            for(let Y = 0; Y < this.kbnList.length; Y++){
                // 区分リストと勤務区分コードが同じ　場合　⇒　宣言した名前に区分名を代入
                if(this.kbnList[Y].categoryKbn == e.workKbn){
                    // 宣言した名前に代入する
                    categoryName = this.kbnList[Y].categoryKbnName
                    break 
                }
            }
            for(let i = 0; i < this.tableData.length; i++){
                // 勤務表の日付と勤務区分変更する日付が同じ　場合　⇒　空,非活性,エラーメッセージが表示
                if(this.tableData[i].workDate == e.workDate){
                    // 勤務区分が欠勤,休職,年末年始を選んだ　場合　⇒　出勤時間が空,非活性、退勤時間が空,非活性
                    if(e.workKbn == "010"  || e.workKbn == "070" || e.workKbn == "080" ){
                        this.tableData[i].workStart1 = '' 
                        this.tableData[i].workEnd1 = ''
                        this.tableData[i].workStart1Flg = true
                        this.tableData[i].workEnd1Flg = true
                    // 勤務区分が欠勤,休職,年末年始以外選択した　場合　⇒　出勤時間が活性、退勤時間が活性 
                    }else {
                        // 管理者コード'100'　で　勤怠区分　'空','出勤','退勤','欠勤','休職','年末年始'　の場合　⇒　勤怠区分が空になりエラーメッセージが表示
                        if(this.permissionKbn_user == '100'){
                            // 勤怠区分　'空','出勤','退勤','欠勤','休職','年末年始'　の場合　⇒　勤怠区分が空になりエラーメッセージが表示,出勤時間が活性、退勤時間が活性
                            if(e.workKbn !== ""  && e.workKbn !== "001" && e.workKbn !== "004" && e.workKbn !== "010" && e.workKbn !== "070"&& e.workKbn !== "080"){
                                Message({
                                    message: categoryName + '申請をしてください',
                                    type: 'error',
                                    duration: 5000
                                })
                                this.tableData[i].workKbn = ""
                                this.tableData[i].workStart1Flg = false
                                this.tableData[i].workEnd1Flg = false
                                break;
                            }
                        // 管理者コード'999','550','500'で勤務区分が欠勤,休職,年末年始以外選択した　場合　⇒　出勤時間が活性、退勤時間が活性
                         //TODO:800は？
                        }else if(this.permissionKbn_user == '999' || this.permissionKbn_user == '550' || this.permissionKbn_user == '500'){
                            this.tableData[i].workStart1Flg = false
                            this.tableData[i].workEnd1Flg = false
                        }
                        this.tableData[i].workStart1Flg = false
                        this.tableData[i].workEnd1Flg = false
                    }
                }  
            }
            let date = {
                companyCode: this.companyCode_user,
                userId: this.agentCode,
                workDateYm: String(this.mYear) + String(this.mMonth)
            }
            // 勤務表画面　の場合　⇒　データを取得
            /*
            *changebyFlg :  1 勤務表画面　
            *               2 申請一覧画面
            *               3 承認一覧画面
            */
            if(this.changebyFlg != "1"){
                date = {
                    companyCode: this.rowdata.companyCode,
                    userId: this.rowdata.requestUserId,
                    workDateYm: this.rowdata.workDateYm
                }
            }
            // 申請テーブル,休暇申請内容テーブル,休日出勤申請内容テーブル,振替出勤申請内容テーブル（グループメンバー取得）
            getApplyAttendanceActivity(date).then( res => {
                for(let i = 0; i < res.data.length; i++){
                    // 申請データと勤務区分変更する日付が同じ　場合　⇒ エラーメッセージが表示
                    if(res.data[i].workDate == e.workDate){
                        // 差戻(010)された申請データと勤務区分変更する日付が同じ　場合　⇒　エラーメッセージが表示
                        if(res.data[i].requestStatusKbn == '010'){
                            Message({
                                message: '差戻されたデータが存在しています',
                                type: 'error',
                                duration: 5000
                            })
                            this.applyAttendance()
                        
                        // 回覧中(003)された申請データと勤務区分変更する日付が同じ　場合　⇒　エラーメッセージが表示
                        }else if(res.data[i].requestStatusKbn == '003'){
                            Message({
                                message:  '申請したデータが存在しています',
                                type: 'error',
                                duration: 5000
                            })  
                            this.applyAttendance()
                        }
                    }
                }
            }).catch(error => {
                console.log(error)
            })
        },
        // 2022/06/10　加賀田　end

        // 休暇時期が空とnull以外　の場合　⇒　timeSum実行
        isNullInfo(workBreakStart, workBreakEnd){
            return workBreakStart !== '' && workBreakStart !== null &&workBreakEnd !== '' && workBreakEnd !== null ?
            this.timeSum(workBreakStart, workBreakEnd, '0:00') : ''
        },

        // 現在の日付の日数を取得
        addDate(){
            let nowDate = new Date();
            this.iYear = nowDate.getFullYear();
            let month = nowDate.getMonth() + 1;
            let dataAll = new Date(this.iYear, month, 0);
            this.iDayDate = parseInt(dataAll.getDate());// 获取当前日期的天数（現在の日付の日数を取得します）
            let day = nowDate.getDate();
            // 一桁（月）　の場合　⇒　頭に'0'を足す（'0' + 月）
            if(String(month).length == 1){
                this.iMonth = '0' + month;
            // 二桁（月）　の場合　⇒　月を現在の月に代入
            }else{
                this.iMonth = month;
            }
            // 一桁（日にち）　の場合　⇒　頭に'0'を足す（'0' + 日にち）
            if(String(day).length == 1){
                this.iDay = '0' + day;
            // 二桁（日にち）　の場合　⇒　日にちを日にちの月に代入
            }else{
                this.iDay = day;
            }
            this.dayValue = this.iYear + '/' + this.iMonth
            this.dayValueR = this.dayValue
        },

        // 更新ボタン押下時の処理
        async applyAttendance(){
            let date = {
                companyCode: this.companyCode_user,
                userId: this.agentCode,
                workDateYm: String(this.mYear) + String(this.mMonth)
            }
            // 勤務表画面　の場合　⇒　データを取得
            if(this.changebyFlg != "1"){
                date = {
                    companyCode: this.rowdata.companyCode,
                    userId: this.rowdata.requestUserId,
                    workDateYm: this.rowdata.workDateYm
                }
            }
            // 申請テーブル,休暇申請内容テーブル,休日出勤申請内容テーブル,振替出勤申請内容テーブル（グループメンバー取得）
            // 勤務表更新　勤怠区分=活性,非活性
            await getApplyAttendanceActivity(date).then(async res => {
                console.log('AAAres.data', res.data)   
                for(let i = 0; i < res.data.length; i++){
                    let dataNum = res.data[i].workDate.slice(-2)-1
                    // 勤務表保存（001）　の場合　⇒　出勤時間が活性,退勤時間が活性,出勤区分は出勤（001）
                    if(res.data[i].requestStatusKbn == '001'){
                        this.tableData[dataNum].workStart1Flg = false
                        this.tableData[dataNum].workEnd1Flg = false
                        this.tableData[dataNum].workKbn = '001'
                    // 勤務表差戻(010)　の場合　⇒　出勤時間が活性,退勤時間が活性,出勤区分は出勤(001)
                    }else if(res.data[i].requestStatusKbn == '010'){
                        this.tableData[dataNum].workStart1Flg = false
                        this.tableData[dataNum].workEnd1Flg = false
                        this.tableData[dataNum].workKbn = '001'
                    // 勤務表回覧(003)　の場合　⇒　出勤時間が空,退勤時間が空
                    }else if(res.data[i].requestStatusKbn == '003'){
                        // 勤務区分　が　有給全日(020)　または　特別休暇(022)　または　代休全日(025)　または　振替休全日(025)　または　夏季休暇(081)　の場合　⇒ 出勤時間が非活性,退勤時間が非活性
                        if(res.data[i].workKbn == '020' || res.data[i].workKbn == '022' || res.data[i].workKbn == '025' ||res.data[i].workKbn == '023' || res.data[i].workKbn == '081'){
                            this.tableData[dataNum].workStart1 = ''
                            this.tableData[dataNum].workEnd1 = ''
                            this.tableData[dataNum].workStart1Flg = true
                            this.tableData[dataNum].workEnd1Flg = true
                        // 勤務区分　が　有給半日(021)　または　代休半日(026)　または　休日出勤半日(005)　または　休日出勤全日(002)　または　振替休全日(024)　または　振替出勤半日（006）または　振替出勤全日（003）　の場合　⇒　出勤時間が活性,退勤時間が活性
                        }else if(res.data[i].workKbn == '021' || res.data[i].workKbn == '026' || res.data[i].workKbn == '005' || res.data[i].workKbn == '002' || res.data[i].workKbn == '024' || res.data[i].workKbn == '006' || res.data[i].workKbn == '003'){
                            this.tableData[dataNum].workStart1Flg = false
                            this.tableData[dataNum].workEnd1Flg = false
                        }
                        // 勤務区分が非活性
                        this.tableData[dataNum].kbnSelectFlg = false
                        this.tableData[dataNum].workKbn = res.data[i].workKbn
                    // 承認完了(999)　の場合　⇒　出勤時間が空,退勤時間が空
                    }else if(res.data[i].requestStatusKbn == '999'){
                        // 勤務区分　が　有給全日(020)　または　特別休暇(022)　または　代休全日(025)　または　振替休全日(025)　または　夏季休暇(081)　の場合　⇒出勤時間が非活性,退勤時間が非活性
                        if(res.data[i].workKbn == '020' || res.data[i].workKbn == '022' || res.data[i].workKbn == '025' ||res.data[i].workKbn == '023' || res.data[i].workKbn == '081'){
                            this.tableData[dataNum].workStart1 = ''
                            this.tableData[dataNum].workEnd1 = ''
                            this.tableData[dataNum].workStart1Flg = true
                            this.tableData[dataNum].workEnd1Flg = true
                        // 勤務区分　が　有給半日(021)　または　代休半日(026)　または　休日出勤半日(005)　または　休日出勤全日(002)　または　振替休全日(024)　または　振替出勤半日（006）または　振替出勤全日（003）　の場合　　⇒　出勤時間が活性,退勤時間が活性
                        }else if(res.data[i].workKbn == '021' || res.data[i].workKbn == '026' || res.data[i].workKbn == '005' || res.data[i].workKbn == '002' || res.data[i].workKbn == '024' || res.data[i].workKbn == '006' || res.data[i].workKbn == '003'){
                            this.tableData[dataNum].workStart1Flg = false
                            this.tableData[dataNum].workEnd1Flg = false
                        }
                        // 勤務区分が非活性
                        this.tableData[dataNum].kbnSelectFlg = true
                        this.tableData[dataNum].workKbn = res.data[i].workKbn
                    }
                }  
                // 勤務表に登録（グループメンバー取得　勤務表が更新）
                await attendanceCreateUpdate(this.tableData).then(async res => {
                }).catch(error => {
                    console.log(error)
                })
            }).catch(error => {
                this.tableLoading = true
                console.log(error)
            });
        },
        // Excelボタン押下時の処理
        async exportCsv(){
            await this.applyAttendance()  
            // ダウンロード表現が表示している場合　⇒　値を返す          
            if(this.exportLoading == true){                
                return
            }
            this.exportLoading = true
            let date = {
                companyCode: this.companyCode_user,
                userId: this.agentCode,
                workDateYm: String(this.mYear) + String(this.mMonth)
            }
            // Excel出力
            exportToProveExcel(date).then(res => {
                let blob = new Blob([res.data], {type: "application/vnd.ms-excel"})
                const href = URL.createObjectURL(blob) // 创建新的URL表示指定的blob对象（指定されたblobオブジェクトを表す新しいURLを作成します）
                const a = document.createElement('a') // 创建a标签（タグを作成する）
                a.style.display = 'none'
                a.href = href // 指定下载链接（ダウンロードリンクを指定）
                a.download = this.agentCode + "_" + String(this.mYear) + String(this.mMonth) + "_" + "勤務表" + "_" +this.userName + ".xlsx" // 指定下载文件名（ダウンロードファイル名を指定します）
                a.click() // 触发下载（トリガーダウンロード）
                this.exportLoading = false
            }).catch(error => {
                this.exportLoading = false
                console.log(error)
            });
        },
        // Pdfボタン押下時の処理
        async exportPdf(){
            await this.applyAttendance()
            // ダウンロード表現が表示している場合　⇒　値を返す
            if(this.exportPdfLoading == true){
                return
            }
            this.exportPdfLoading = true

            let date = {
                companyCode: this.companyCode_user,
                userId: this.agentCode,
                workDateYm: String(this.mYear) + String(this.mMonth),
                accessMode:3
            } 
            // Pdf出力
            exportToAttendancePdfByEmployee(date).then(res => {
                let blob = new Blob([res.data], {type: "application/pdf"})
                const href = URL.createObjectURL(blob) 
                const a = document.createElement('a') 
                a.style.display = 'none'
                a.href = href 
                a.download = this.agentCode + "_" + String(this.mYear) + String(this.mMonth) + "_"+ "勤務表_" +this.userName + ".pdf" // 指定下载文件名（ダウンロードファイル名を指定します）
                a.click() 
                this.exportPdfLoading = false
            }).catch(error => {
                this.exportPdfLoading = false
                console.log(error)
            });
        },
        // ダイアログ内の承認者選択（申請前）の選択押下時の処理
        userSearchInreq(userName_kari, agentCode_kari, departmentName_kari){
            this.name_req=userName_kari
            this.userId_req=agentCode_kari
            this.departmentName_req=departmentName_kari
            this.searchUserInfomail = true
        },
        // ダイアログ内の勤務表検索とダイアログ内の承認者選択（申請後）の選択押下時の処理    
        userSearchIn(userName_kari, agentCode_kari, departmentName_kari){
            this.name_req=userName_kari
            this.userId_req=agentCode_kari
            this.departmentName_req=departmentName_kari
            this.searchUserInfomail = true
            this.resUserId = agentCode_kari
            // 社員を選択したら、画面遷移しない
            // 申請後、承認者選択のダイアログ内　以外の場合　保存ボタンが非表示
            if(!this.openlast){
                let mineFlg = false
                // 自分の画面　の場合　⇒　他の社員の勤務表が表示
                if(agentCode_kari == getUserInfo().userId){
                    mineFlg = true
                }
                // 社員番号が同じ　場合　⇒　キャンセルボタン押下時の処理
                if(this.agentCode == getUserInfo().userId && agentCode_kari == getUserInfo().userId){
                    this.userSearchClose()
                    return
                }
                this.userName = userName_kari
                this.agentCode = agentCode_kari
                this.departmentName = departmentName_kari
                let date = {
                    companyCode: this.companyCode_user,
                    userId: this.agentCode,
                    workDateYm: String(this.mYear) + String(this.mMonth)
                }
                // 浦 不具合対応 2022/6/3 start
                // 申請,勤務表申請テーブル(グループメンバー取得)　保存ボタン,申請ボタンが表示/非表示
                getStatusAttendance(date).then(res =>{
                    this.checkStatus = res.data
                    // 申請している　場合　⇒　保存ボタン,申請ボタンが表示/非表示
                    if(this.checkStatus) {
                        //  管理者コード　'500' かつ　'550' の以外の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                        if(this.permissionKbn_user !== '500' && this.permissionKbn_user !== '550'){
                            this.reqFlg = false
                            this.modifyFlgsave = false
                        // 管理者コード　'500' または　'550' の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                        }else if(this.permissionKbn_user == '500' || this.permissionKbn_user == '550'){
                            // 自分の画面　の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                            if(this.agentCode == getUserInfo().userId){
                                this.reqFlg = false
                                this.modifyFlgsave = false
                            // 自分の画面　以外の画面　⇒　申請ボタン,保存ボタンが表示/非表示
                            }else if(this.agentCode !== getUserInfo().userId){
                                // 最終承認（999）　の場合　⇒　申請ボタンが非表示,保存ボタンが表示
                                if(this.checkStatus.requestStatusKbn == '999'){
                                    this.reqFlg = false
                                    this.modifyFlgsave = true
                                // 最終承認（999）　以外の場合　⇒　申請ボタンが表示,保存ボタンが表示
                                }else{
                                    this.modifyFlgsave = true
                                    this.reqFlg = true
                                }
                            }
                        }
                    // 申請してない　場合　⇒　保存ボタン,申請ボタンが表示/非表示
                    }else{
                        // 管理者コード　'500' かつ　'550' の以外の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                        if(this.permissionKbn_user !== '500' && this.permissionKbn_user !== '550'){
                            // 自分の画面　の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                            if(this.agentCode !== getUserInfo().userId){
                                this.reqFlg = false
                                this.modifyFlgsave = false
                            // 自分の画面　以外場合　⇒　申請ボタンが表示,保存ボタンが表示
                            }else{
                                this.reqFlg = true
                                this.modifyFlgsave = true
                            }
                        // 管理者コード　'500' または　'550' の場合　⇒　申請ボタンが表示,保存ボタンが表示
                        }else if(this.permissionKbn_user == '500' || this.permissionKbn_user == '550'){
                            this.modifyFlgsave = true
                            this.reqFlg = true
                        }
                    }
                    }).catch(error => {
                        console.log(error)
                    })
                    // 浦 不具合対応 2022/6/3 end            
                    this.tableData = []
                    // 管理者コード　'500' かつ　'550' の以外の場合　⇒　保存ボタンが非表示
                    if(this.permissionKbn_user !== '500' && this.permissionKbn_user !== '550'){
                        this.modifyFlg = false
                    }
                    //  他の社員の勤務表　の場合　⇒　他の社員の勤務表が非表示,保存ボタンが表示
                    if(mineFlg){
                        this.userSearchReturnFlg = false
                        this.modifyFlg = true
                    //  他の社員の勤務表　以外場合　⇒　他の社員の勤務表が表示,保存ボタンが非表示
                    }else{
                        this.userSearchReturnFlg = true
                        this.reqFlg = false
                    }
                this.dialogSee = false
                this.searchUserInfo = false
                this.formSearch.search_id = ''
                this.tableLoading = true
                this.getDetail(date)
            }
        },
        // 自分の勤務表に戻るボタン押下時の処理
        userSearchReturn(){
            this.agentCode = getUserInfo().userId
            this.userName = getUserInfo().userName
            this.departmentName = getUserDetailedInfo().departmentName
            let date = {
                companyCode: this.companyCode_user,
                userId: this.agentCode,
                workDateYm: String(this.mYear) + String(this.mMonth)
            }
            this.tableData = []
            this.modifyFlg = true
            this.userSearchReturnFlg = false
            this.tableLoading = true
            this.getDetail(date)
            // 保存・申請ボタン表示/非表示 浦 不具合対応 2022/6/3 start
            // 申請,勤務表申請テーブル(グループメンバー取得)　保存ボタン,申請ボタンが表示/非表示
            getStatusAttendance(date).then(res =>{
                this.checkStatus = res.data
                // 申請している　場合　⇒　保存ボタン,申請ボタンが表示/非表示
                if(this.checkStatus){
                    //  管理者コード　'500' かつ　'550' の以外の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                    if(this.permissionKbn_user !== '500' && this.permissionKbn_user !== '550'){
                        this.reqFlg = false
                        this.modifyFlgsave = false
                    // 管理者コード　'500' または　'550' の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                    }else if(this.permissionKbn_user == '500' || this.permissionKbn_user == '550'){
                        // 自分の画面　の場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                        if(this.agentCode == getUserInfo().userId){
                            this.reqFlg = false
                            this.modifyFlgsave = false
                        // 自分の画面　以外場合　⇒　申請ボタンが表示,保存ボタンが表示
                        }else if(this.agentCode !== getUserInfo().userId){
                            // 最終承認（999）　の場合　⇒　申請ボタンが非表示,保存ボタンが表示
                            if(this.checkStatus.requestStatusKbn == 999){
                                this.reqFlg = false
                                this.modifyFlgsave = true
                            // 最終承認（999）　以外の場合　⇒　申請ボタンが表示,保存ボタンが表示
                            }else{
                                this.modifyFlgsave = true
                                this.reqFlg = true
                            }
                        }
                    }
                // 申請してない　場合　⇒　申請ボタンが表示,保存ボタンが表示
                }else{
                    this.reqFlg = true
                    this.modifyFlgsave = true
                }
            }).catch(error => {
                console.log(error)
            })
            // 保存・申請ボタン表示/非表示 浦 不具合対応 2022/6/3 end
        },

        // テーブルデータの設定（表示＆作成）　更新
        tableDataComplete(){
            let temp = 0;
            let flag = false
            let firstDayFlg = false
            let firstDayWeed = this.getFirstDayByMonths(Number(this.mYear), Number(this.mMonth) - 1)
            let firstDay = this.mYear + this.mMonth + "01"
            let tableKariLength = this.tableData_kari.length;
            let tableLength = this.tableData.length;
            let oFlag = tableKariLength == 0 || this.tableData_kari[0].workDate.substring(6,8) !== "01"
            // 勤務テーブルが0　の場合　⇒　firstDayFlg（true）
            if(tableKariLength == 0){
                firstDayFlg = true
            }
            for(let i = 0; i < this.mDayDate; i++){
                let flag = false
                let tableLength = this.tableData.length;
                let workDate_kari = this.mYear + this.mMonth + this.strComplete(i+1)
                let holiFlg = this.isHoliday(i + 1)
                let data
                //　勤務テーブルにデータがない（firstDayFlgがtrue)　場合　⇒　テーブルデータ作成 
                if(!firstDayFlg){
                    //　勤務表データとデータが同じ　場合　⇒　テーブルデータ作成 
                    if(this.tableData_kari[temp].workDate == workDate_kari){
                        flag = true
                        let tableNewData = {
                            companyCode: this.tableData_kari[temp].companyCode,
                            departmentName: this.tableData_kari[temp].departmentName,
                            userId: this.tableData_kari[temp].userId,
                            userName: this.tableData_kari[temp].userName,
                            sendReportFlg: this.tableData_kari[temp].sendReportFlg,
                            workBreakTime1: this.tableData_kari[temp].workBreakTime1,
                            workBreakTime2: this.tableData_kari[temp].workBreakTime2,
                            workBreakTime3: this.tableData_kari[temp].workBreakTime3,
                            workSumHoliTime: this.tableData_kari[temp].workSumHoliTime,
                            workSumTime: this.tableData_kari[temp].workSumTime,
                            workDateYm: this.tableData_kari[temp].workDateYm,
                            holidayFlg: this.tableData_kari[temp].holidayFlg,
                            workStart1Flg: this.tableData_kari[temp].workStart1Flg,
                            workEnd1Flg: this.tableData_kari[temp].workEnd1Flg,                            
                            kbnSelectFlg: this.tableData_kari[temp].kbnSelectFlg,
                            workDate : this.tableData_kari[temp].workDate,
                            workDateWeek: this.tableData_kari[temp].workDateWeek,
                            workStart1: !!this.tableData_kari[temp].workStart1 ? this.tableData_kari[temp].workStart1.split(' ')[1] : '',
                            workEnd1: !!this.tableData_kari[temp].workStart1 && !!this.tableData_kari[temp].workEnd1 ? this.dayCompare(this.tableData_kari[temp].workStart1,this.tableData_kari[temp].workEnd1) ? this.tableData_kari[temp].workEnd1.split(' ')[1] : this.minuteFormat(this.minuteChange(this.tableData_kari[temp].workEnd1.split(' ')[1]) + 24 * 60) : this.tableData_kari[temp].workEnd1,
                            workStart2: !!this.tableData_kari[temp].workStart2 ? this.tableData_kari[temp].workStart2.split(' ')[1] : '',
                            workEnd2: !!this.tableData_kari[temp].workStart2 && !!this.tableData_kari[temp].workEnd2 ? this.dayCompare(this.tableData_kari[temp].workStart2,this.tableData_kari[temp].workEnd2) ? this.tableData_kari[temp].workEnd2.split(' ')[1] : this.minuteFormat(this.minuteChange(this.tableData_kari[temp].workEnd2.split(' ')[1]) + 24 * 60) : this.tableData_kari[temp].workEnd2,
                            workStart3: !!this.tableData_kari[temp].workStart3 ? this.tableData_kari[temp].workStart3.split(' ')[1] :  '',
                            workEnd3: !!this.tableData_kari[temp].workStart3 && !!this.tableData_kari[temp].workEnd3 ? this.dayCompare(this.tableData_kari[temp].workStart3,this.tableData_kari[temp].workEnd3) ? this.tableData_kari[temp].workEnd3.split(' ')[1] : this.minuteFormat(this.minuteChange(this.tableData_kari[temp].workEnd3.split(' ')[1]) + 24 * 60) : this.tableData_kari[temp].workEnd3,
                            workKbn: this.tableData_kari[temp].workKbn,
                            workTime: this.tableData_kari[temp].workTime,
                            workOverTime: this.tableData_kari[temp].workOverTime,
                            workMidnightTime: this.tableData_kari[temp].workMidnightTime,
                            workHoliTime: this.tableData_kari[temp].workHoliTime,
                            workMidnightHoliTime: this.tableData_kari[temp].workMidnightHoliTime,
                            remark: this.tableData_kari[temp].remark
                        }
                        this.tableData.push(tableNewData)
                        // 勤務テーブルデータが多い　場合　⇒　行を追加
                        if(temp < tableKariLength - 1){
                            temp++
                        // それ以外　の場合　firstDayFlg（true）
                        }else{
                            firstDayFlg = true
                        }
                    }
                }
                // // 日にちが１日の場合firstDayFlg（true）　の場合　⇒　実行
                if(!flag){
                    // oFlag（true）　の場合　⇒　実行
                    if(oFlag){
                        let tableNewData = {
                            companyCode: this.companyCode_user,
                            departmentName: this.departmentName,
                            userId: this.agentCode,
                            userName: this.userName,
                            sendReportFlg: '0',
                            workBreakTime1: '',
                            workBreakTime2: '',
                            workBreakTime3: '',
                            workSumHoliTime: firstDayWeed == '土' || firstDayWeed == '日' || holiFlg == '1' ? '' : this.settingRestTime1,
                            workSumTime: '',
                            workDateYm: this.mYear + this.mMonth,
                            holidayFlg: holiFlg,
                            workStart1Flg: false,
                            workEnd1Flg: false,
                            kbnSelectFlg: false,
                            workDate : firstDay,
                            workDateWeek: firstDayWeed,
                            workStart1: '',
                            workEnd1: '',
                            workStart2: '',
                            workEnd2: '',
                            workStart3: '',
                            workEnd3: '',
                            workKbn: firstDayWeed == '土' || firstDayWeed == '日' || holiFlg == '1' ? '' : '001',
                            workTime: '',
                            workOverTime: '',
                            workMidnightTime: '',
                            workHoliTime: '',
                            workMidnightHoliTime: '',
                            remark: ''
                        }
                        this.tableData.push(tableNewData)
                        oFlag = false
                    // 日にちが１日　以外場合(oFlagがfalse）　⇒　実行
                    }else{
                        let wDW = this.switchDay(this.tableData[tableLength - 1].workDateWeek)
                        let tableNewData = {
                            companyCode: this.companyCode_user,
                            departmentName: this.departmentName,
                            userId: this.agentCode,
                            userName: this.userName,
                            sendReportFlg: '0',
                            workBreakTime1: '',
                            workBreakTime2: '',
                            workBreakTime3: '',
                            workSumHoliTime: '',
                            workSumTime: '',
                            workDateYm: this.mYear + this.mMonth,
                            holidayFlg: holiFlg,
                            workStart1Flg: false,
                            workEnd1Flg: false,                            
                            kbnSelectFlg: false,
                            workDate : String(Number(this.tableData[tableLength - 1].workDate) + 1),
                            workDateWeek: wDW,
                            workStart1: '',
                            workEnd1: '',
                            workStart2: '',
                            workEnd2: '',
                            workStart3: '',
                            workEnd3: '',
                            workKbn: wDW == '土' || wDW == '日' || holiFlg == '1' ? '' : '001',
                            workBreakTime: wDW == '土' || wDW == '日' || holiFlg == '1' ? '' : this.settingRestTime1,
                            workTime: '',
                            workOverTime: '',
                            workMidnightTime: '',
                            workHoliTime: '',
                            workMidnightHoliTime: '',
                            remark: ''
                        }
                        this.tableData.push(tableNewData)
                    }
                }
            }
            this.tableLoading = false
            // 今日の日付の場合　⇒　開いたときに今日の日付に自動的に移動
            if(this.toTodayFlg){
                let vmEl = this.$refs.tableAttendance.$el
                this.$nextTick(function(){
                    //　開いたときに今日の日付に自動的に移動
                    if(!vmEl) return
                    const targetTop = vmEl.querySelectorAll('.el-table__body tr')[this.iDay - 1].offsetHeight
                    this.$refs.tableAttendance.bodyWrapper.scrollTop = targetTop*(this.iDay-1);
                })
            }
            this.applyAttendance()
        },
        // 出勤時間１,出勤時間２,出勤時間３,退勤時間１,退勤時間２,退勤時間３
        dayCompare(startDate, endDate){
            // 出勤時間と退勤時間が同じ　場合　⇒　実行
            if(startDate.split(' ')[0] == endDate.split(' ')[0]){
                return true
            // それ以外　の場合　⇒　実行なし
            }else{
                false
            }
        },
        // 祝日がある場合その日にちは'1'に設定　祝日ではない場合'0'
        isHoliday(day){
            for(let i = 0; i < this.holidayDate.length; i++){
                // 勤務の日にちと祝日データが同じ場合　⇒　'1'
                if(day == Number(this.holidayDate[i])){
                    return '1'
                }
            }
            // 祝日ではない　場合　⇒　'0'
            return '0'
        },  
        // 日にちの設定　一桁（日にち）の場合,二桁（日にち）の場合
        strComplete(data){
            let data_str = String(data)
            // 一桁（日にち）　の場合　⇒　0を頭に足す（'0' ＋ 日にち）
            if(data_str.length == 1){
                return "0" + data_str
            // 二桁（日にち）　の場合
            }else{
                return data_str
            }
        },

        // 小畠　関数追加　2022/03/09 start
        // 申請ボタン,保存ボタンが表示/非表示
        updatebtnflg(){
            // 勤務表画面　の場合　⇒　データ取得　申請ボタン,保存ボタンが表示/非表示
            if(this.changebyFlg == "1"){
                let data1 = {
                    companyCode: '000001',
                    userId: this.agentCode,
                    workDateYm: this.dayValue.replace('/','')
                }
                // 申請テーブル(グループメンバー取得) 申請ボタン,保存ボタンが表示/非表示
                getRequestAttendanceByList(data1).then(res =>{   
                // 申請データがある場合　⇒　申請ボタンが非表示,保存ボタンが非表示
                if(res.data){
                    this.reqFlg = false
                    this.modifyFlgsave = false
                    // 申請データがある場合　⇒　申請ボタンが表示,保存ボタンが表示
                    }else{
                        this.reqFlg = true
                        this.modifyFlgsave = true
                    }
                    this.timePChange()
                })
                this.tableLoading = true
            // 申請一覧画面　または　承認一覧画面　の場合　⇒　年月選択押下時の処理
            }else if(this.changebyFlg == "2" || this.changebyFlg == "3"){
                this.timePChange()
            }
        // 小畠　関数追加　2022/03/09 end
        },
    }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';
// 初期画面
.list-card {
    height: auto;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    border-radius: 6px;
    padding: 20px 0px;
    margin-bottom: 60px;

    .list-card-text {
        height: auto;
        padding: 0 30px;
    }
}
// テーブル,入力
.el-table .el-input {
    width: 100%;
}

.el-table thead {
    color: #fff;
}

.el-table__body {
  width: 100%;
  table-layout: fixed !important;
}
// 備考欄
.hidden {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
// 祝日
.holiday-bg {
    height: 65px;
    background-color: #f5f5f5 !important;
}.d-row {
    height: 65px;
}
// 土曜日
.saturday-cell {
    color: #4685ff;
}
// 日曜日,祝日
.sunday-cell {
    color: #fd5555 !important;
}
// 勤務表,承認者選択の検索押下時のダイアログのサイズ（スクロール表示）
.searchUserInfoArea {
    height: auto;
    max-height: 320px;
    overflow-y: scroll;
}
// 勤務表検索ボタン,承認者選択の検索後のダイアログ
.searchUserInfo-btn {
    transition: 0.5s;
    width: 380px;
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
    transition: 0.5s;
    box-shadow: 0 3px 20px 0 rgba(104, 104, 104, 0.3);
    border: 2px #ffa811 solid;
}
// 承認者選択の検索の後,選択後の表示
.approval-area {
    width:100%;
    height: auto;
    padding: 10px;
    margin: 10px 0;
    background-color: #f5f5f5;
    // 承認者選択の表示（タイトル：承認者）
    .approval-area-title {
        color: #888;
        line-height: 40px;
    }
    // 承認者選択の未選択
    .approval-area-notext {
        color: #fd5555;
        line-height: 40px;
        font-weight: bold;
    }
    // 承認者選択の選択後の表示（社員名,社員番号,所属名）
    .approval-area-text {
        color: #444;
        font-weight: bold;
        line-height: 40px;
        margin-right: 20px;
        font-size: 16px;
    }
}
// 詳細情報ボタン,勤務表検索ボタン
.my-info-all {
    margin-top: 3px;
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
// 詳細情報ダイアログ内の社員名
.dialogUserInfoClass {
    .name-title {
        font-size: 26px;
        color: #444;
        font-weight: bold;
    }
}
// 詳細情報ダイアログ内の詳細部分
.info-card {
    background-color: #f7f7f7;
    padding: 10px 20px;
    border-radius: 4px;
    height: auto;
    margin-top: 10px;
}
// ボタンの位置（戻る,保存,申請,取戻,差戻,承認）
.btn-area {
    height: 30px;
    background-color: #fff;
    margin-top: 20px;
    padding: 0px 30px;
}
</style>

<style lang="scss">
@import '../style/index.scss';
// 承認ボタンの位置
.approval-btn {
    margin-left: 20px;
}
// 承認者選択ダイアログ
.rout {
    margin-right: 40px;
    padding: 20px;
    background-color: #f5f5f5;
    height: 500px;
    overflow-y: scroll
}
// ボタン押下時の処理（ダウンロード表現の枠）
.csv .el-loading-mask {
    margin-left: 0px !important;
    border-radius: 50%;
}
// ボタン押下時の処理（ダウンロード表現のサイズ）
.csv .el-loading-spinner .circular {
    height: 26px !important;
    width: 26px !important;
}
// ボタン押下時の処理（ダウンロード表現の位置）
.csv .el-loading-spinner {
    margin-top: -14px !important;
}
// 更新ボタン
.update-btn {
    transition: 0.5s;
    width: 150px;
    height: 30px;
    background-color: #fd6e55;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 6px 0 rgba(253, 85, 85, 0.4);
    border-radius: 30px;
    cursor: pointer;
    color: #fff;
}
.update-btn:hover {
    transition: 0.5s;
    background-color: #f8aaaa;
}
//Excelボタン
.white-excel-btn {
    transition: 0.5s;
    width: 150px;
    height: 30px;
    background-color: #fff;
    border: 2px #54c336 solid;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 6px 0 rgba(84, 195, 54, 0.2);
    border-radius: 30px;
    cursor: pointer;
    color: #54c336;
    font-weight: bold;
}
.white-excel-btn:hover {
    transition: 0.5s;
    background-color: #dcfbd4;
}
//Pdfボタン
.white-pdf-btn {
    transition: 0.5s;
    width: 150px;
    height: 30px;
    background-color: #fff;
    border: 2px #ff0000 solid;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 6px 0 rgba(250, 0, 0, 0.2);
    border-radius: 30px;
    cursor: pointer;
    color: #ff0000;
    font-weight: bold;
}
.white-pdf-btn:hover {
    transition: 0.5s;
    background-color: #fbd4d4;
}

</style>