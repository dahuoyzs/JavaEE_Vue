<template>
	<div>
		<div class="row" >
			<div class="col-lg-2">
				<!-- 分类 -->
				<div class="list-group">
				  <button 
				  	class="list-group-item list-group-item-action"
					@click="getGoodsAll()"
				  	>
				    全部
				    <span class="badge badge-pill badge-success">{{ goodsALLcount }}</span>
				  </button>
				  
				  <button
				  	class="list-group-item list-group-item-action"
					v-for="(value,key) in goodsClassJO" 
					@click="getGoodsByClass(key)"
				  	>
					  {{ key }}<span class="badge badge-pill badge-success">{{ value }}</span>
				  </button>

				</div>
				<br>
				<!-- 套餐 -->
				<!-- <div class="list-group">
				  <button type="button" class="list-group-item list-group-item-action">
				    套餐一
				  </button>
				  <button type="button" class="list-group-item list-group-item-action">套餐二
				  </button>
				  <button type="button" class="list-group-item list-group-item-action">套餐三</button>
				  <button type="button" class="list-group-item list-group-item-action">套餐四</button>

				</div> -->
			</div>
			<!-- 商品列表 -->
			<div class="col-lg-6" style="background-color:#fff">
				<table class="table" >
					<thead class="thead-default">
						<tr>
							<!-- <th>序号</th> -->
							<th>商品名称</th>
							<th>商品编号</th>
							<th>价格</th>
							<th>加入</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(goods,index) in resultgoods">
							<!-- <td>{{ index+1 }}</td> -->
							<td>{{ goods.goodsname }}</td>
							<td><small><i>{{ goods.goodsid }}</i></small></td>
							<td>{{ goods.price }}</td>
							<td><button 
								@click="addToBasket(goods.goodsid,goods.goodsname,goods.price,index)"
								class="btn btn-sm btn-outline-success">+</button></td>
						</tr>
					</tbody>
				</table>
				
				<!-- 分页 -->
				<div class="center offset-md-2">
					<el-pagination
					small
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="pagenow"
					:page-sizes="pagesizes"
					:page-size="pagelimit"
					layout="total, sizes, prev, pager, next, jumper"
					:total="goodscount">
					</el-pagination>
				</div>
				
			</div>
			<!-- 购物车 -->
			<div class="col-lg-4" >
				<div class="goodsbasket" v-if="selectgoods.length > 0" style="background-color:#fff">
					<table class="table">
					<thead class="thead-default">
						<tr>
							<th>数量</th>
							<th>商品名称</th>
							<th>价格</th>
							<th>删除</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="sgoods in selectgoods">
							<td>
								<button @click="reduce(sgoods)" class="btn btn-sm btn-light"
								>-</button>
								<span>{{ sgoods.number }}</span>
								<button @click="add(sgoods)" class="btn btn-sm btn-light"
								>+</button>
							</td>
							<td>{{ sgoods.goodsname }}</td>
							<td>{{ sgoods.price }}</td>
							<td>
								<button @click="removeSelectGoods(sgoods)" class="btn btn-sm btn-outline-danger"
								>x</button>
							</td>
						</tr>
					</tbody>
				</table>
				<p>总价:{{ countMoney }} RMB</p>
				<button class="btn btn-success btn-block" @click="submitOrder">提交</button>
				</div>
				<div class="basketText" v-else>
					购物车暂时没有商品
				</div>
				
			</div>
		</div>
	</div>
</template>
<script type="text/javascript">
	
	export default{
		name:'goodslist',
		data(){
			return {
				pagenow:1,//当前页码
				pagelimit:10,//限制一页只能显示
				goodscount:0,//商品总数查数据库得到
				goodsALLcount:0,//商品总数查数据库得到
				nowclass:'全部',			//用于保存当前分类，  当下次换页的时候根据nowclass查询
				pagesizes:[5,10,15,20],
				pagelist:[],
				selectgoods:[],
				resultgoods:[],
				goodsClassJO:{},
				// resultbasket:[]//可以写个数据库
			}
		},
		computed:{
			countMoney(){
				let sum=0;
				for(let index in this.selectgoods){
					let obj=this.selectgoods[index];
					sum+=obj.number*obj.price;
				}
				return sum;
			}
		},
		methods: {
			submitOrder(){
				Date.prototype.format = function(format)
				{
					var o = {
					"M+" : this.getMonth()+1, //month
					"d+" : this.getDate(),    //day
					"h+" : this.getHours(),   //hour
					"m+" : this.getMinutes(), //minute
					"s+" : this.getSeconds(), //second
					"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
					"S" : this.getMilliseconds() //millisecond
					}
					if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
					(this.getFullYear()+"").substr(4 - RegExp.$1.length));
					for(var k in o)if(new RegExp("("+ k +")").test(format))
					format = format.replace(RegExp.$1,
					RegExp.$1.length==1 ? o[k] :
					("00"+ o[k]).substr((""+ o[k]).length));
					return format;
				}
				let jsonparam={};
				jsonparam['confirmdate']=new Date().format('yyyy-MM-dd hh:mm:ss');
				jsonparam['countprice']=this.countMoney;
				jsonparam['salename']=localStorage.getItem('ms_username');//let username = localStorage.getItem('ms_username');
				jsonparam['orderdetails']=this.selectgoods;
				this.$axios.get('http://localhost:8080/WaterWarm1.0/GetToDayOrderMax?date='+new Date().format('yyyy-MM-dd'))
				.then(res=> {
					let todyres=res.data;
					//console.log(todyres);
					let ordercode;
					ordercode=todyres.code=='200'?new Date().format('yyyyMMdd')+(parseInt(todyres.maxordercode.substring(8))+1):ordercode=new Date().format('yyyyMMdd')+"1001";
					jsonparam['ordercode']=ordercode;
					console.log(jsonparam);
					this.$axios.post('http://localhost:8080/WaterWarm1.0/AddOrder',jsonparam)
					.then(res=> {
						let orderres=res.data;
						console.log(orderres);
						if(orderres.code=='200')
						{
							this.$message({
								message: '商品订单已提交成功',
								type: 'success'
							});
							this.selectgoods=[];
						}else{
							this.$message({
								message: '订单提交失败',
								type: 'warning'
							});
						}
					});
				});
				

			},
			addToBasket:function(goodsid,goodsname,price,index){
				let tempgoods={
					goodsid,
					goodsname,
					price,
					index,
					number:1
				}
				
				if (this.selectgoods.length>0) {
					let afterFilterArr=this.selectgoods.filter(
						function(item){
							return (item.goodsname===goodsname&&item.price===price)
						}
					)
					if (afterFilterArr!=null&&afterFilterArr.length>0) {
						afterFilterArr[0].number++;
					}else{
						this.selectgoods.push(tempgoods);
					}
					
				}else{
					this.selectgoods.push(tempgoods);
				}

				this.selectgoods.push()
			},
			reduce:function(goods){
				goods.number--;
				if (goods.number<1) {
					this.removeSelectGoods(goods);
				}
			},
			add:function(goods){
				goods.number++;
			},
			handleSizeChange(val) {					//分页  每页显示条目
				console.log(`每页 ${val} 条`);
				this.pagelimit=val;
				if(this.nowclass==='全部')
					this.getGoodsAll();
				else
					this.getGoodsByClass(val);

			},
			handleCurrentChange(val) {				//分页  当前页监听事件
				console.log(`当前页: ${val}`);
				this.pagenow=val;
				if(this.nowclass==='全部')
					this.getGoodsAll();
				else
					this.getGoodsByClass(val);
			},
			removeSelectGoods(goods){
				this.selectgoods.splice(this.selectgoods.indexOf(goods),1);
			},
			getGoodsAll(){
				this.nowclass='全部';
				this.$axios.get('http://localhost:8080/WaterWarm1.0/ShowGoods?pagenow='
				+this.pagenow+'&pagelimit='+this.pagelimit)
				.then(res=> {
					this.resultgoods=res.data;
					this.goodscount=this.goodsALLcount;
				});
			}
			,
			getGoodsByClass(gc){
				this.nowclass=gc;
				this.$axios.get('http://localhost:8080/WaterWarm1.0/ShowGoods?pagenow='
				+this.pagenow+'&pagelimit='+this.pagelimit+"&goodsclass="+gc)
				.then(res=> {
					this.resultgoods=res.data;
					this.goodscount=this.resultgoods.length;

				});
			}
			
		},
		created(){
			// 获取商品分类列表
			this.$axios.get('http://localhost:8080/WaterWarm1.0/GetGoodsClass')
				.then(res=> {
					this.goodsClassJO=res.data;
					// console.log(res.data);//输出返回的结果
					this.goodsALLcount=0;
					let nums=Object.values(this.goodsClassJO);//获取到值
					for (var i = 0; i < nums.length; i++) {
						this.goodsALLcount+=nums[i];		//找出每个分类的个数叠加
					}
					this.goodscount=this.goodsALLcount;
					// console.warn("pagenow:"+this.pagenow);
					// console.warn("pagelimit:"+this.pagelimit);
					// console.warn("goodscount:"+this.goodscount);
					// console.warn("goodsALLcount:"+this.goodsALLcount);
					// console.warn("pagecount:"+this.pagecount);
				});
			// 获取商品列表
			this.$axios.get('http://localhost:8080/WaterWarm1.0/ShowGoods?pagenow='
			+this.pagenow+'&pagelimit='+this.pagelimit)
				.then(res=> {
					this.resultgoods=res.data;
				});
		}

	}
	

</script>

<!-- 


http://p0.meituan.net/codeman/33ff80dc00f832d697f3e20fc030799560495.jpg
http://p1.meituan.net/codeman/bb0abb3435a60c44d87e90ed4237b61039329.jpg
http://p0.meituan.net/codeman/a97baf515235f4c5a2b1323a741e577185048.jpg
http://p1.meituan.net/codeman/826a5ed09dab49af658c34624d75491861404.jpg
http://p0.meituan.net/codeman/daa73310c9e57454dc97f0146640fd9f69772.jpg


 -->