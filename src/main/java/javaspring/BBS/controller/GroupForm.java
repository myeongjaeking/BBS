package javaspring.BBS.controller;

public class GroupForm {
    boolean group_private;
    String group_name;
    String group_password;

    public boolean isGroup_private() {
        return group_private;
    }

    public void setGroup_private(boolean group_private) {
        this.group_private = group_private;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_password() {
        return group_password;
    }

    public Long getGroup_num_people() {
        return group_num_people;
    }
    public Long group_max_people;

    public Long getGroup_max_people() {
        return group_max_people;
    }

    public void setGroup_max_people(Long group_max_people) {
        this.group_max_people = group_max_people;
    }

    public void setGroup_num_people(Long group_num_people) {
        this.group_num_people = group_num_people;
    }

    Long group_num_people;

    public void setGroup_password(String group_password) {
        this.group_password = group_password;
    }
}
