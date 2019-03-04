package cn.cs.Board.dao.model;

import java.util.Date;

public class Msg {
	
	private String username;
	private String profilePic;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mb_msg.id
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mb_msg.userid
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	private Long userid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mb_msg.content
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mb_msg.date
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	private Date date;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mb_msg.pic
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	private String pic;
	
	public String getUsername() {
		return username;		
	}
	public String getProfilePic() {
		return profilePic;		
	}
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mb_msg.id
	 * @return  the value of mb_msg.id
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mb_msg.id
	 * @param id  the value for mb_msg.id
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mb_msg.userid
	 * @return  the value of mb_msg.userid
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public Long getUserid() {
		return userid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mb_msg.userid
	 * @param userid  the value for mb_msg.userid
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mb_msg.content
	 * @return  the value of mb_msg.content
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mb_msg.content
	 * @param content  the value for mb_msg.content
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mb_msg.date
	 * @return  the value of mb_msg.date
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mb_msg.date
	 * @param date  the value for mb_msg.date
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mb_msg.pic
	 * @return  the value of mb_msg.pic
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mb_msg.pic
	 * @param pic  the value for mb_msg.pic
	 * @mbg.generated  Tue Feb 26 14:43:55 CST 2019
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
}