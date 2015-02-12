package com.huake.saas.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.weixin.model.WeixinTreeNode;
/**
 * 
 * @author wujiajun
 * @desc  用于处理 菜单的节点操作！！！
 *
 */
public interface WeixinTreeNodeDao extends PagingAndSortingRepository<WeixinTreeNode, Long>,JpaSpecificationExecutor<WeixinTreeNode> {

	@Query("select  node  from WeixinTreeNode   node   where node.treeid= :id ")
	public WeixinTreeNode findByTreeid(@Param("id")long id);

	/**
	 * 发送到微信的顺序
	 */
    @Query("select node from WeixinTreeNode node where node.pId= :pid and uid= :uid order by seq asc")
    public List<WeixinTreeNode> findByPidAndUid(@Param("pid")Long pid,@Param("uid")Long uid);
    /**
     * 查询所有节点，按照 seq 排序  获取所有几点的时候使用
     */
    @Query("select node from WeixinTreeNode node where node.uid= :uid order by seq asc ")
    public List<WeixinTreeNode> findByUid(@Param("uid")Long uid);
    
    
    @Query("select node from WeixinTreeNode node where node.pId= :name and uid= :uid ")
    public WeixinTreeNode findRootByPidAndUid(@Param("name")long name,@Param("uid")Long uid);
	
    @Query("select node from WeixinTreeNode node where node.pId= :pId and node.seq> :seq ")
    public List<WeixinTreeNode> findByPidAndQSeq(@Param("pId")Long pId,@Param("seq")int seq);
    
    
    /**
     * 注意  ：  id==pid
     * 
     * @param pId
     * @param uid
     * 查询父级 节点
     */
    @Query("select node from WeixinTreeNode node where node.id= :pId and node.uid= :uid ")
    public WeixinTreeNode findTreeByPid(@Param("pId")Long pId,@Param("uid")long uid);
    
    
    
}
