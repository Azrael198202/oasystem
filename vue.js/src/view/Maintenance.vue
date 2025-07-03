<template>
    <div>
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class='maintenance-area'>
                    <el-container>
                        <el-header>
                            <span class="card-title-big">メンテナンス一覧</span>
                        </el-header>
                        <el-main>
                            <div class='maintenance-content'>
                                <el-col class="btn-area-block">
                                    <router-link :to="'/paidHolidayMaintenance'">
                                        <el-button type="primary" class="paidholiday-maintenance-btn">
                                            <div class="maintenance-btn-icon-position" style="font-size: 80px"><i :class="'fas fa-wrench'"></i></div>
                                            <div class="maintenance-btn-text-position">{{'有休メンテナンス'}}</div>
                                        </el-button>
                                    </router-link>
                                </el-col>
                                <el-col class="btn-area-block">
                                    <router-link :to="'/dayOffMaintenance'">
                                        <el-button type="primary" class="paidholiday-maintenance-btn">
                                            <div class="maintenance-btn-icon-position" style="font-size: 80px"><i :class="'fas fa-wrench'"></i></div>
                                            <div class="maintenance-btn-text-position">{{'代休メンテナンス'}}</div>
                                        </el-button>
                                    </router-link>
                                </el-col>
                                <el-col class="btn-area-block">
                                    <router-link :to="'/noticeMaintenance'">
                                        <el-button type="primary" class="paidholiday-maintenance-btn">
                                            <div class="maintenance-btn-icon-position" style="font-size: 80px"><i :class="'fas fa-wrench'"></i></div>
                                            <div class="maintenance-btn-text-position">{{'お知らせ管理'}}</div>
                                        </el-button>
                                    </router-link>
                                </el-col>

                                <el-col class="btn-area-block">
                                    <router-link :to="'/subHolidayInput'">
                                        <el-button type="primary" class="paidholiday-maintenance-btn">
                                            <div class="maintenance-btn-icon-position" style="font-size: 80px"><i :class="'fas fa-wrench'"></i></div>
                                            <div class="maintenance-btn-text-position">{{'代休残入力'}}</div>
                                        </el-button>
                                    </router-link>
                                </el-col>
                                <el-col class="btn-area-block">
                                    <router-link :to="'/privilegeManagement'">
                                        <el-button type="primary" class="paidholiday-maintenance-btn">
                                            <div class="maintenance-btn-icon-position" style="font-size: 80px"><i :class="'fas fa-wrench'"></i></div>
                                            <div class="maintenance-btn-text-position">{{'権限管理'}}</div>
                                        </el-button>
                                    </router-link>
                                </el-col>
                            </div>
                        </el-main>
                        <el-footer>
                            <div class="maintenance-back-btn" @click="closeA()">戻る</div>
                        </el-footer>
                    </el-container>
                </div>
            </div>
        </div>
        <commonFootter></commonFootter> 
    </div>   
</template>

<script>
    import commonHeader from "@/layout/commonHeader.vue";
    import commonFootter from "@/layout/commonFootter.vue";

    export default {
        name: "Maintenance",
        components: {
            commonHeader,
            commonFootter
        },
        created() {
        },
        data() {
            return {
            }
        },
        methods: {
            closeA() {
                this.$router.push({
                    path: '/toppage'                  
                })

            },

        }
    }
</script>

<style lang="scss" scope>
.maintenance-area {
    width: 100%;
    height: 750px;
    position: relative;
    background-color: #fff;
    margin:0 auto;
    border-radius: 6px;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 30px;
    background-color: #fff;
}
.maintenance-content {
    display: flex;
    flex-wrap: wrap;
    justify-content: start;
    align-content: flex-start;
    margin-right: 20px;

    .btn-area-block {
        width: 295px;
        height: 295px;
        margin-bottom: 10px;
    }

    .el-button {
        white-space: pre-wrap !important;
        line-height: 20px;
    }
}
.paidholiday-maintenance-btn {
    position: relative;
    border-Color: #fedc5e;
    font-Size: 24px;
    margin: 6px 0 0 6px;
    height: 260px;
    width: 260px;
    color: #666666;
    background-Color: #fedc5e;
    box-Shadow: 0 4px 10px 0 #fedc5e 80;
}

.paidholiday-maintenance-btn:hover {
    background-color: transparent;
    border: 2px solid #e6c300;
    color: #e6c300 !important;
}

.maintenance-btn-icon-position {
    // position: absolute;
    // top: 16px;
    // left: 50px;
    margin: 0 auto;
    margin-top: -30px;
}
.maintenance-btn-text-position {
    height: 60px;
    width: 220px;
    position: absolute;
    bottom: 10px;
    left: 20px;
}

.maintenance-back-btn {
    transition: 0.5s;
    width: 80px;
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
    position: absolute;
    left: 56px;
    top: 720px;
}

.maintenance-back-btn:hover {
    transition: 0.5s;
    color: #aaa;
    border-color: #aaa;
}

</style>