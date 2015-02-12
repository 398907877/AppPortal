package com.huake.saas.weixin.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;

import com.huake.saas.weixin.model.WeixinTreeNode;
import com.huake.saas.weixin.repository.WeixinTreeNodeDao;

/**
 * 
 * 
 * 
 * @author wujiajun
 * @desc  菜单节点的service
 *
 */
@Service("WeixinTreeNodeService")
public class WeixinTreeNodeService {

	@Autowired
	private  WeixinTreeNodeDao WeixinTreeNodeDao;
	
	private static final Logger logger = LoggerFactory.getLogger(WeixinUserService.class);
	
	
	//增加
	public void addTreeNode(WeixinTreeNode entity){
		
		WeixinTreeNodeDao.save(entity);
		
	}
	//删除
	public void deleteTreeNode(WeixinTreeNode entity){
		WeixinTreeNodeDao.delete(entity);
		
	}
	
	//删除
	public void deleteTreeNodeById(long id){
		WeixinTreeNodeDao.delete(id);
		
	}
	
	//修改
	public void modifyTreeNode(long id){
	    //TODO
	}
	//查询所有
	
	public List<WeixinTreeNode> findAllTreeNode(){
	
		
		return 	(List<WeixinTreeNode>) WeixinTreeNodeDao.findAll( new Sort(Direction.ASC, "id"));
	}
	
	
	public List<WeixinTreeNode> findTreeNodeByUid(long uid){
	
		
		return 	(List<WeixinTreeNode>) WeixinTreeNodeDao.findByUid(uid);
	}
	
	//查询根据treeid
	public WeixinTreeNode  findByTreeId(long id){
	
		
		return 	WeixinTreeNodeDao.findOne(id);
	}
	
	public List<WeixinTreeNode>  findByPidAndUid(long id,long uid){
	return 	WeixinTreeNodeDao.findByPidAndUid(id,uid);
	}
	
	
	public WeixinTreeNode  findRootByPidAndUid(long pid,long uid){
	return 	WeixinTreeNodeDao.findRootByPidAndUid(pid, uid);
	}
	
	/**
	 * 变更菜单节点
	 * @param nodeId 原节点
	 * @param targetId 目标节点
	 */
	public void drop(long nodeId, long targetId,String position) {
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+nodeId);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+targetId);
		WeixinTreeNode node = WeixinTreeNodeDao.findOne(nodeId);
		WeixinTreeNode targetNode = WeixinTreeNodeDao.findOne(targetId);
		
		Long pId = node.getpId();
		Long ptId = targetNode.getpId();
		/**
		 * 同父下修改
		 */
		if(pId.equals(ptId))
		{
			if(position.equals("prev"))
			{
	        	node.setSeq(targetNode.getSeq());
	        	WeixinTreeNodeDao.save(node);
	        	List<WeixinTreeNode> list = WeixinTreeNodeDao.findByPidAndQSeq(pId, targetNode.getSeq());
	        	targetNode.setSeq(targetNode.getSeq()+1);
	        	WeixinTreeNodeDao.save(targetNode);
	        	for (int i = 0; i < list.size(); i++) {
	        		System.out.println(list.get(i).getId());
	        		list.get(i).setSeq(targetNode.getSeq()+i+2);
					WeixinTreeNodeDao.save(list.get(i));
				}
			}
			else //next
			{
	        	//大于目标节点的加大
				List<WeixinTreeNode> list = WeixinTreeNodeDao.findByPidAndQSeq(pId, targetNode.getSeq());
	        	for (int i = 0; i < list.size(); i++) {
	        		System.out.println(list.get(i).getId());
	        		list.get(i).setSeq(targetNode.getSeq()+i+2);
					WeixinTreeNodeDao.save(list.get(i));
				}
	        	//移动节点加大
				node.setSeq(targetNode.getSeq()+1);
				WeixinTreeNodeDao.save(node);
			
			}
		}
		else
		{
			if(position.equals("prev"))
			{
				node.setpId(ptId);
	        	node.setSeq(targetNode.getSeq());
	        	WeixinTreeNodeDao.save(node);
	          	List<WeixinTreeNode> list = WeixinTreeNodeDao.findByPidAndQSeq(pId, targetNode.getSeq());
	        	targetNode.setSeq(targetNode.getSeq()+1);
	        	WeixinTreeNodeDao.save(targetNode);
	        	for (int i = 0; i < list.size(); i++) {
	        		System.out.println(list.get(i).getId());
	        		list.get(i).setSeq(targetNode.getSeq()+i+2);
					WeixinTreeNodeDao.save(list.get(i));
				}
			}
			else //next
			{
				//大于目标节点的加大
				List<WeixinTreeNode> list = WeixinTreeNodeDao.findByPidAndQSeq(pId, targetNode.getSeq());
	        	for (int i = 0; i < list.size(); i++) {
	        		System.out.println(list.get(i).getId());
	        		list.get(i).setSeq(targetNode.getSeq()+i+2);
					WeixinTreeNodeDao.save(list.get(i));
				}
	        	//移动节点加大
	        	node.setpId(ptId);
				node.setSeq(targetNode.getSeq()+1);
				WeixinTreeNodeDao.save(node);
			}
		}

	}
	/**
	 * 
	 * 查询到父级对象
	 */
	public WeixinTreeNode findFatherByPid(long pid,long uid){
		
		
		
		return WeixinTreeNodeDao.findTreeByPid(pid, uid);
	}
	
	
	/**
	 * 
	 * @param pid
	 * @param uid
	 *查询  pid 相等的 所有节点
	 */
	public List<WeixinTreeNode> findAllNodeByPid(long pid,long uid){
		
	
		
		return 	WeixinTreeNodeDao.findByPidAndUid(pid, uid);
	}
	
	

	
	
	
}
