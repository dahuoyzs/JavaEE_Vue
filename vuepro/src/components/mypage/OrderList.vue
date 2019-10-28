<template>
  <div id="maindiv" style="background-color:#fff">
    <div class="col-lg-12">
      <!-- 表格 -->
      <table class="table table-bordered" style="width:200%; ">
        <thead class="thead-default">
          <tr>
            <th style="text-align:center;">
              <input type="checkbox" @click="selectALL()" :checked="isAll"/></th>
            <th>订单号</th>
            <th>下单时间</th>
            <th>订单总价</th>
            <th>销售人</th>
            <th>订单详情</th></tr>
        </thead>
        <tbody>
        
          <tr v-for="(order,index) in resultorders" >
          
            <td style="text-align:center;" >
              <!--   可以用
              <input type="checkbox"  :value="index"  />
              -->
              <input type="checkbox"  @click="selected(index)" :checked="selectOrders[index]"/></td>
            <td style="text-align:center;" >{{ order.ordercode }}</td>
            <td style="text-align:center;">
              <small>
                <i>{{ order.confirmdate }}</i>
              </small>
            </td>
            <td style="text-align:center;">
              <span style="color:red">{{ order.countprice }}</span></td>
            <td style="text-align:center;">{{ order.salename }}</td>
            <!--详情按钮 -->
            <td style="text-align:center;">
              <button @click="getDetails(order)" class="btn btn-sm btn-outline-success">详细</button>
            </td>
            
        
            
          </tr>
          
        </tbody>
      </table>
      <!-- 详细 -->
      <el-dialog :title="titleText" :visible.sync="dialogTableVisible">
        <el-table :data="orderDetails">
          <el-table-column property="goodsname" label="商品名称" width="200"></el-table-column>
          <el-table-column property="price" label="单价" width="80" style="color:red"></el-table-column>
          <el-table-column property="number" label="个数" width="80"></el-table-column>
          <el-table-column property="confirmdate" label="下单时间"></el-table-column>
          
        </el-table>
      </el-dialog>
      <!-- 分页 -->
      <div class="center offset-md-4">
        <el-pagination small @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pagenow" :page-sizes="pagesizes" :page-size="pagelimit" layout="total, sizes, prev, pager, next, jumper" :total="ordersize"></el-pagination>
      </div>
      <!-- 打印按钮 -->
      <div id="print">
        <button type="button" class="btn btn-outline-info" @click="print()">打印所选项</button>
      </div>
    </div>
  </div>
</template>
<script>export
default {
    name:
    "orderlist",
    data() {
      return {
        pagenow: 1,
        //当前页码
        pagelimit: 10,
        //限制一页只能显示
        ordersize: 0,
        //商品总数查数据库得到
        pagesizes: [5, 10, 15, 20],
        resultorders: [],
        selectOrders:[],
        pNOrderNum:0,
        pNRealOrderNum:0,
        isAll:false,
        orderDetails:[
          {goodsname:'青铜水龙头',price:15,number:3,goodsid:1003},
          {goodsname:'黄金水龙头',price:30,number:1,goodsid:1004}
        ],
        dialogTableVisible:false,
        titleText:'订单详情'
      }
    },
    methods: {
      print(){
        let filtIndexArr = this.selectOrders.map((val,index)=>val?{val,index}:false).filter(item=>item.val===false?false:item).map(item=>item.index);
        //console.log(filtIndexArr);//选中的下标
        let pagecount= parseInt(this.ordersize%this.pagelimit==0?this.ordersize/this.pagelimit:(this.ordersize/this.pagelimit)+1);
        
        let ordercodes=[];
        if(pagecount==this.pagenow){
          console.log(this.pagenow,this.pagelimit,this.ordersize,this.pNOrderNum);
          for(let i=0;i<this.ordersize%this.pagelimit;i++)
          {
            ordercodes[i]= this.resultorders[filtIndexArr[i]].ordercode;
          }
        }else{
           ordercodes=filtIndexArr.map(val=>this.resultorders[val].ordercode);
           //let ordercodes=filtIndexArr.map(val=>this.resultorders[val].ordercode);
        }
        console.log(ordercodes);//选中的订单号


        
        
        
        if(ordercodes.length===0){
            this.$notify({
              title: '打印提示',
              message: this.$createElement('i', { style: 'color: blue'}, '请先选择要打印的订单哦'),
              duration:2000
            });
        }else{
            
            localStorage.setItem('ordercodes',JSON.stringify(ordercodes));
            let routeData = this.$router.resolve({
              name: "print",
            });
            window.open(routeData.href, '_blank');
        }
      },
      selected(index){
        this.selectOrders[index]=!this.selectOrders[index];//选择的这个位置的bool取反
        this.isAll=this.selectOrders.every((val)=>val);//判断一下是否全部都是true了，如果是则isAll为true，否则为false;
        console.log(this.selectOrders);
      },
      selectALL(){
        this.isAll=!this.isAll;                                 //全选取反
        this.selectOrders=this.selectOrders.map(()=>this.isAll);//重构数组  让所有的选项都与全选保持一致
        console.log(this.isAll);
      },
      getDetails(order) {
        //console.log(order.ordercode);
        this.dialogTableVisible=true;
        this.$axios.get('http://localhost:8080/WaterWarm1.0/GetOrderDetail?ordercode='+order.ordercode)
        .then(res =>{
          //console.log(res.data);//返回结果
          this.orderDetails=res.data;
          this.titleText=order.ordercode+"号订单";
        });
      },
      handleSizeChange(val) {
        this.pagelimit = val;//更新每页显示的条数
        this.getOrderSize();//刷新条目数
        this.getOrder();//刷新界面
      },
      handleCurrentChange(val) {
        this.pagenow = val;//更新当前页
        this.getOrder();//刷新界面
      },
      getOrder() {
        this.selectOrders=[];
        this.$axios.get('http://localhost:8080/WaterWarm1.0/ShowOrder?pagenow=' + this.pagenow + '&pagelimit=' + this.pagelimit).then(res =>{
          this.resultorders = res.data;//返回结果
          this.pNOrderNum=this.ordersize>this.pagelimit?this.pagelimit:this.ordersize;//本页显示的条目数
          for(let i=0;i<this.pNOrderNum;i++)    //初始化所有的额选项
            this.selectOrders.push(this.isAll);
          //console.log("selectOrders的长度"+this.selectOrders.length);
        });
      },
      getOrderSize() {
        this.$axios.get('http://localhost:8080/WaterWarm1.0/GetOrderSize').then(res =>{
          this.ordersize = parseInt(res.data.ordersize);
        });
      }
    },
    created() {
      this.getOrderSize();
      this.getOrder()
    }
  }</script>
  <style scoped>
    th{
      text-align:center;
    }
   
  </style>

<!--
    本页面公用了3个接口
-->


