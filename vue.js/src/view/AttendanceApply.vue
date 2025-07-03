<template>
    <div class="home-bg">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="attendanceApply-area">
                    <div class="apply-area-title">
                        <el-row>
                            <span class="card-title-big">勤務表承認一覧</span>
                        </el-row>
                        <el-row style="margin-top: 40px">
                            <el-col :span="5" class="flex-start">
                                <el-select v-model="select" placeholder="请选择活动区域" width='100px'>
                                    <el-option label="区域一" value="shanghai"></el-option>
                                    <el-option label="区域二" value="beijing"></el-option>
                                </el-select>
                            </el-col>
                            <el-col :span="5" class="flex-start">
                                <el-select v-model="select" placeholder="请选择活动区域" width='100px'>
                                    <el-option label="区域一" value="shanghai"></el-option>
                                    <el-option label="区域二" value="beijing"></el-option>
                                </el-select>
                            </el-col>
                            <el-col :span="14" class="flex-end" style="margin-top:5px">
                                <div class="green-btn">検索</div>
                            </el-col>
                        </el-row>
                    </div>

                    <el-table
                        :data="applyList"
                        :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                        :cell-class-name="cellStyle"
                        style="width: 100%"
                        height="560px">
                        <el-table-column
                            width="80">
                        </el-table-column>
                        <el-table-column
                            prop="date"
                            label="申請日"
                            width="200">
                        </el-table-column>
                        <el-table-column
                            prop="name"
                            label="申請人"
                            width="200">
                        </el-table-column>
                        <el-table-column
                            prop="code"
                            label="社員番号"
                            width="200">
                        </el-table-column>
                        <el-table-column
                            prop="departmentName"
                            label="部門"
                            width="280">
                        </el-table-column>
                        <el-table-column
                            prop="status"
                            label="状態"
                            width="140">
                        </el-table-column>
                        <el-table-column
                            label="ルート"
                            width="100">
                            <template>
                                <div style="height: 40px">
                                    <div class='white-btn' style="width: 30px;height: 30px;margin-top: 6px;" @click="dialogRouterOpen()">
                                        <i class="fas fa-sitemap" style="font-size: 10px"></i>
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="詳細"
                            width="100">
                            <template>
                                <div style="height: 40px">
                                    <div class='white-btn' style="width: 30px;height: 30px;margin-top: 6px;">
                                        <i class="fas fa-ellipsis-h" style="font-size: 10px;"></i>
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
            <el-dialog
                title="ルート"
                :visible.sync="dialogRouter"
                width="500px"
                :close-on-click-modal="true"
                >
                <div class="block">
                    <el-timeline :reverse='true'>
                        <el-timeline-item timestamp="2021/08/05 20:46" placement="top" color="#ffa811">
                            <el-card>
                                <div class="flex-between"><h4>陳 晶晶</h4><h4 class="orange-text">新規申請</h4></div>
                                <p style="color:#888">申請時間 2021/08/05 20:46</p>
                            </el-card>
                        </el-timeline-item>
                        <el-timeline-item timestamp="2021/08/06 09:30" placement="top" color="#54c336">
                            <el-card>
                                <div class="flex-between"><h4>小坂 一哲</h4><h4 class="green-text">承認</h4></div>
                                <p style="color:#888">同意です</p>
                            </el-card>
                        </el-timeline-item>
                        <el-timeline-item timestamp="2021/08/06 10:30" placement="top" color="#fd5555">
                            <el-card>
                                <div class="flex-between"><h4>張 偉明</h4><h4 class="red-text">拒否</h4></div>
                                <p style="color:#888">申請材料不足です</p>
                            </el-card>
                        </el-timeline-item>
                    </el-timeline>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogRouter = false">キャンセル</el-button>
                </span>
            </el-dialog>
        </div>
        <commonFootter></commonFootter>
    </div>

</template>

<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";

export default {
    name: "AttendanceApply",
    components: {
        commonHeader,
        commonFootter,
    },
    data() {
        return {
            activeIndex: '1',
            activeIndex2: '1',
            step: '0',
            input: '',
            select: '',
            dialogRouter: false,
            applyList: [
                {date: '2021/07', name: '陳 晶晶', code: '171',departmentName: '九州事業部', status: '新規'},
                {date: '2021/07', name: '李 斌', code: '151',departmentName: '九州事業部', status: '申請成功'},
                {date: '2021/07', name: '陳 文菊', code: '111',departmentName: '九州事業部', status: '差戻'},
                {date: '2021/07', name: '董 波', code: '123',departmentName: '九州事業部', status: '申請中'},
                {date: '2021/07', name: '陳 晶晶', code: '171',departmentName: '九州事業部', status: '申請成功'},
                {date: '2021/07', name: '李 斌', code: '151',departmentName: '九州事業部', status: '新規'},
                {date: '2021/07', name: '陳 文菊', code: '111',departmentName: '九州事業部', status: '申請中'},
                {date: '2021/07', name: '董 波', code: '123',departmentName: '九州事業部', status: '新規'},
                {date: '2021/07', name: '陳 晶晶', code: '171',departmentName: '九州事業部', status: '申請中'},
                {date: '2021/07', name: '李 斌', code: '151',departmentName: '九州事業部', status: '申請成功'},
                {date: '2021/07', name: '陳 文菊', code: '111',departmentName: '九州事業部', status: '差戻'},
                {date: '2021/07', name: '董 波', code: '123',departmentName: '九州事業部', status: '新規'},
                {date: '2021/07', name: '陳 晶晶', code: '171',departmentName: '九州事業部', status: '新規'},
                {date: '2021/07', name: '李 斌', code: '151',departmentName: '九州事業部', status: '差戻'},
                {date: '2021/07', name: '陳 文菊', code: '111',departmentName: '九州事業部', status: '新規'},
                {date: '2021/07', name: '董 波', code: '123',departmentName: '九州事業部', status: '新規'},
            ]
        }
    },
    methods: {
      cellStyle({row, column, rowIndex, columnIndex}){
        switch(row.status) {
            case '新規': 
                if(columnIndex == 5) {
                    return 'orange-text' 
                }else {
                    return ''
                }

            case '申請中': 
                if(columnIndex == 5) {
                    return 'blue-text' 
                }else {
                    return ''
                }

            case '申請成功': 
                if(columnIndex == 5) {
                    return 'green-text' 
                }else {
                    return ''
                }
            
            case '差戻': 
                if(columnIndex == 5) {
                    return 'red-text' 
                }else {
                    return ''
                }
        }
      },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      dialogRouterOpen() {
          this.dialogRouter = true
      },
      stepChange0() {
          this.step = '0'
      },
      stepChange1() {
          this.step = '1'
      },
      stepChange2() {
          this.step = '2'
      },
      stepChange3() {
          this.step = '3'
      },
      stepChange4() {
          this.step = '4'
      },
      stepChange5() {
          this.step = '5'
      },
      stepChange6() {
          this.step = '6'
      }
    }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.el-menu-item {
    font-size: 16px;
    padding: 0 10px 0 30px !important;
}

.el-menu {
    border-right: none;
}

.attendanceApply-area {
    width: 1300px;
    margin-right: 10px;
    height: 740px;
    background-color: #fff;
    border: 1px #dcdcdc solid;
    border-radius: 6px;
    // box-shadow: 0 2px 6px 0 rgba(127, 127, 127, 0.2);
    padding: 20px 0px;
}

.apply-area-title {
    padding: 0 20px;
    margin-bottom: 20px;
}

</style>
