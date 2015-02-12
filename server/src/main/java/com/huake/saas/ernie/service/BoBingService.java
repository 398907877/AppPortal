package com.huake.saas.ernie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.huake.saas.ernie.entity.ErnieItem;

@Component
public class BoBingService {
	private int[] faceValue;
	private int[] count;
	private int maxCount;
	/**
	 * 摇色子 的各个点数
	 * @return
	 */
	public List<Integer> getDice(){
		Random r = new Random();
		List<Integer> dice = new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			dice.add(r.nextInt(6)+1);
		}
		return dice;
	}
	
	/**
	 * 获取 摇出骰子  对应博饼的结果
	 * @param dice
	 * @return
	 */
	public String getResult(List<Integer> dice) {
		faceValue=new int[dice.size()];
		count=new int[dice.size()];
		int i=0;
		for(Integer d: dice ){
			count[d-1]++;
			faceValue[i++]=d-1;
		}
		sort();
		maxCount();
		
		return compatable();
	}
	//排序
	private void sort(){
		int temp;
		for(int i=0;i<6;i++){
			for(int j=i+1;j<6;j++){
				if(faceValue[i]>faceValue[j]){
					temp=faceValue[j];
					faceValue[j]=faceValue[i];
					faceValue[i]=temp;
				}
			}
		}
	}
	//集合中相同元素的最大值
	private void maxCount(){
		maxCount=0;
		for(int i=0;i<6;i++){
			if(count[i]>maxCount){
				maxCount=count[i];
			}
		}
	}
	//对照  
	private String compatable(){
		//判断相同元素最大值
		if(maxCount==6){
			/* 暂时 只要判断是状元即可
			if(faceValue[0]==3){//6个4的情况  满堂红
 				
			}else if(faceValue[0]==0){ //6个1的情况  遍地锦
				
			}else{//其他 六勃黑
				
			}*/
			return ErnieItem.BOBING_ZHUANGYUAN;
		}else if(maxCount==5){
			/*if(faceValue[1]==3){
				//五王; 5个四
			}else{ 
				//五子带一秀？？ 5个非四 带一个四？
			}*/
			return ErnieItem.BOBING_ZHUANGYUAN;
		}else if(maxCount==4){
			if(faceValue[2]==3){
				if(faceValue[0]==faceValue[1]&& faceValue[0]==0){
					//状元插金花;
					return ErnieItem.BOBING_ZHUANGYUAN;
				}else{
					//状元;	4个4
					return ErnieItem.BOBING_ZHUANGYUAN;
				}
			}else{
				return ErnieItem.BOBING_SIJIN;//四进带一秀?? 二举？？？
			}
		}else if(maxCount==1){
			//对堂;
			return ErnieItem.BOBING_DUITANG;
		}else if(count[3]==3){
			//三红;
			return ErnieItem.BOBING_SANHONG;
		}else if(count[3]==2){
			//二举;
			return ErnieItem.BOBING_ERJU;
		}else if(count[3]==1){
			//一秀;
			return ErnieItem.BOBING_YIXIU;
		}else{
			//不中;
			return ErnieItem.BOBING_BUZHONG;
		}
	}
}
