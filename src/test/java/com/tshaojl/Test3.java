package com.tshaojl;

import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;

public class Test3 {
    public static void main(String[] args) {
        Doo sd = new Doo();
        sd.setTags(Arrays.asList("ss", "dd"));
        Rtt rs = new Rtt();
        BeanUtils.copyProperties(sd, rs);
        System.out.println(rs.getTags());
        System.out.println(rs.getTags().get(0));
    }
}

class Doo {
    List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
class Rtt {
    List<Integer> tags;

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }
}