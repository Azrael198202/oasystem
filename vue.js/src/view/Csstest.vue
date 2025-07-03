<template>
  <div style="background-color: #fff;border: #dcdcdc 1px solid;">
    <el-row type="flex" justify="center">
        <el-date-picker
            v-model="value"
            type="date"
            placeholder="Pick a day">
        </el-date-picker>    
        <div style="line-height: 40px">
            {{week()}}
        </div>      
    </el-row>
    <el-row type="flex" justify="center">
        <el-date-picker
            v-model="selectDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
            >
        </el-date-picker>
        <el-date-picker
            v-model="selectDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期1"
            end-placeholder="结束日期1"
            value-format="yyyy-MM-dd"
            >
        </el-date-picker>
        <button @click="onClick">list</button>
    </el-row>
  </div> 
</template>

<script>
export default {
    name: "Csstest",
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

/* @media screen and (max-width: 300px) {
}

@media screen and (min-width: 300px) {
}

@media screen and (min-width: 1000px) {
} */



/* @media (min-width: 1024px){undefined
  body{font-size: 18px}
} 
@media (min-width: 1100px) {undefined
  body{font-size: 20px}
} 
@media (min-width: 1280px) {undefined
  body{font-size: 22px;}
} 
@media (min-width: 1366px) {undefined
  body{font-size: 24px;}
}  
@media (min-width: 1440px) {undefined
  body{font-size: 25px !important;}
} 
@media (min-width: 1680px) {undefined
  body{font-size: 28px;}
} 
@media (min-width: 1920px) {undefined
  body{font-size: 33px;}
}  */


@media only screen and (max-width: 500px) {
    .gridmenu {
        width:100%;
    }

    .gridmain {
        width:100%;
    }

    .gridright {
        width:100%;
    }
}
</style>