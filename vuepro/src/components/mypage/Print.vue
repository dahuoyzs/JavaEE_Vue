<template>
    <div id="maindiv" style="background-color:#F0F0F0;width:100%;height:100%;overflow:auto;">
        <button type="button" class="btn btn-success" style="float:right" @click="printPage()" >打印所选项</button>
        <div id="print" >
          <div class="col-lg-9" v-for="(order,index) in orders" style="background-color:#fff;">
              <p style="color:blue">订单号:{{ order.ordercode }}</p>
              <p style="color:blue">下单时间:{{ order.confirmdate }}</p>
              <center><h2 style="color:black">商品出货单</h2></center>

              <table class="table table-bordered" >
                <thead class="thead-default"  
                
                  <tr>
                    <th style="text-align:center;">商品名称</th>
                    <th style="text-align:center;">单价</th>
                    <th style="text-align:center;">个数</th>
                    <th style="text-align:center;">合计</th>
                    <div style="margin-left:5px;"></div>
                    <th style="text-align:center;">商品名称</th>
                    <th style="text-align:center;">单价</th>
                    <th style="text-align:center;">个数</th>
                    <th style="text-align:center;">合计</th>
                  </tr>
                </thead>
                <tbody > 
                  <tr v-for="(goods,innerindex) in danArr[index]">
                    <td style="text-align:center;" >{{ goods.goodsname }}</td>
                    <td style="text-align:center;" >{{ goods.price }}</td>
                    <td style="text-align:center;" >{{ goods.number }}</td>
                    <td style="text-align:center;" >{{ goods.number*goods.price }}</td>
                    <div style="margin-left:5px;"></div>
                    <td style="text-align:center;" >{{ shuangArr[index][innerindex].goodsname }}</td>
                    <td style="text-align:center;" >{{ shuangArr[index][innerindex].price }}</td>
                    <td style="text-align:center;" >{{ shuangArr[index][innerindex].number }}</td>
                    <td style="text-align:center;" >{{ shuangArr[index][innerindex].number*shuangArr[index][innerindex].price}}</td>
                  </tr>
                </tbody>
              </table>

              <p class="text-right" style="color:red">总计:{{ order.countprice }}元</p>
              <p class="text-right" style="color:red">销售人员:{{ order.salename }}</p>
          </div>
       </div>
    </div>
</template>
<script>
export default {
    name:"print",
    data() {
      return {
        orders:[],
        danArr:[],
        shuangArr:[]
      }
    },
    methods: {
      printPage(){    
        window.print();
      },
      getPrintOrders(){
        let params=JSON.parse(localStorage.getItem('ordercodes'));
        //console.log(params);
        this.$axios.post('http://localhost:8080/WaterWarm1.0/GetPrintOrders',params).then(res =>{

            //console.log(res.data);
            this.orders=res.data;
            this.orders.forEach((item,index,arr)=>{

              console.log(item,index,arr);
              let temArr=item.detail;
              this.danArr[index]=temArr.filter((it,innerdex,arr)=>{
                  return innerdex%2==0;
              });
              this.shuangArr[index]=temArr.filter((item,innerdex,arr)=>{
                  return innerdex%2==1;
              });
              if(this.danArr[index].length>this.shuangArr[index].length)
              {
                this.shuangArr[index].push({"number":"","price":"","goodsname":""});
              }
              
            })
        });
      }
    },
    created() {
      this.getPrintOrders()
    }
  }</script>
  <style scoped>
    table{
      border: 1px solid #151515;
    }
    thead{
      border: 1px solid #151515;
    }

    
  </style>

  
<!--
    本页面公用了1个接口
-->




