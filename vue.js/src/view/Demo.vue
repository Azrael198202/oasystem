<template>
  <div style="background-color: #fff;border: #dcdcdc 1px solid;padding: 60px;">
    <el-row type="flex" justify="center">
        <el-col :span="6">
            <el-date-picker
                v-model="value"
                type="date"
                placeholder="Pick a day">
            </el-date-picker>
        </el-col>
        <el-col :span="6">
            <div style="line-height: 40px">
               {{week()}}
            </div>
        </el-col>
    </el-row>
    <el-date-picker
        v-model="selectDate"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="yyyy-MM-dd"
        >
        </el-date-picker>
        {{selectDate}}
    <button @click="onClick">list</button>
  </div>
</template>

<script>
export default {
    name: "Demo",
    data() {
        return {
            value: "",
            selectDate: ''
        }
    },
    computed: {
        weekdays: function() {
            return ['日曜日','月曜日','火曜日','水曜日','木曜日','金曜日','土曜日']
        }
    },
    methods: {
        onClick() {
            console.log(this.dayList(this.selectDate[0],this.selectDate[1]))
        },
        week() {
            if (this.value == '' || this.value == null ) {
                return ""
            } else {
                let v = new Date(this.value).getDay()
                return this.weekdays[v]
            }
        },
        dayList (startDay,endDay) {
            let dayL = [];
            let ys=startDay.split('-')[0]
            let ms=startDay.split('-')[1]
            let ds=startDay.split('-')[2]
            let ye=endDay.split('-')[0]
            let me=endDay.split('-')[1]
            let de=endDay.split('-')[2]

            if (Number(ye) == Number(ys)) {
                if (Number(me) == Number(ms)) {
                    if(Number(ds) == Number(de)) {
                        dayL.push(startDay)
                        return dayL
                    } else {
                        for (let i = Number(ds); i <= Number(de); i++) {
                            dayL.push(ys+'-'+ms+'-'+this.stringAddMD(i))
                        }
                        return dayL
                    }
                } else {
                    for (let i = Number(ds); i <= new Date(ys,Number(ms),0).getDate(); i++) {
                        dayL.push(ys+'-'+ms+'-'+this.stringAddMD(i))
                    }
                    for (let l = 1; l <= Number(de); l++) {
                        dayL.push(ye+'-'+me+'-'+this.stringAddMD(l))
                    }
                    return dayL
                }
            }else {
               if (Number(me) == Number(ms)) {
                    if(Number(ds) == Number(de)) {
                        dayL.push(startDay)
                        return dayL
                    } else {
                        for (let i = Number(ds); i <= Number(de); i++) {
                            dayL.push(ys+'-'+ms+'-'+this.stringAddMD(i))
                        }
                        return dayL
                    }
                } else {
                    for (let i = Number(ds); i <= new Date(ys,Number(ms),0).getDate(); i++) {
                        dayL.push(ys+'-'+ms+'-'+this.stringAddMD(i))
                    }
                    for (let l = 1; l <= Number(de); l++) {
                        dayL.push(ye+'-'+me+'-'+this.stringAddMD(l))
                    }
                    return dayL
                }
            }
        },
        stringAddMD (num) {
            if(String(num).length < 2) {
                return '0' + num
            } else {
                return String(num)
            }
        }
    }
}
</script>

<style>

</style>