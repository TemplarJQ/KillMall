package com.seckillmall.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBiz
{
    private int packetId;
    private String packetName;
    private List<User> packetList;

}
