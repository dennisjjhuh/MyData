package com.example.mydata;

/**
 * Created by Dennis on 2016-03-20.
 */
public class Member {

    private String age;
    private String[] hobbys;
    private String name;
    private String no;
    private String u_id;
    private String u_pw;

    public Member(String age, String[] hobbys, String name, String no, String u_id, String u_pw) {
        this.age = age;
        this.hobbys = hobbys;
        this.name = name;
        this.no = no;
        this.u_id = u_id;
        this.u_pw = u_pw;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String[] getHobbys() {
        return hobbys;
    }

    public void setHobbys(String[] hobbys) {
        this.hobbys = hobbys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_pw() {
        return u_pw;
    }

    public void setU_pw(String u_pw) {
        this.u_pw = u_pw;
    }
}
