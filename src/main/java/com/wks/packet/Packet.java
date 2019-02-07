package com.wks.packet;

import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.Data;


/**
 * 数据包类，包含 packet 与buffer之间的转化，但不应该包含其他操作如 序列化数据等
 */
@Data
public class Packet implements PacketAnalysis<Packet> {
    /**
     * 魔法数
     */
    public Integer magicNum;

    /**
     * 协议版本
     */
    public byte version;

    /**
     * 指令
     */
    public byte command;

    /**
     * 序列化算法
     */
    public byte serializerAlgorithm;

    /**
     * 数据长度
     */
    public Integer dataLength;

    /**
     * 数据
     */
    public byte[] data;

    //不常变动的数据部分
    {
        this.magicNum = 1;
        this.version = 1;
        this.serializerAlgorithm = SerializerAlgorithm.JSON;
    }

    public Packet() {

    }

    public Packet(byte command, byte[] data) {
        this.command = command;
        this.dataLength = data.length;
        this.data = data;
    }

    public Packet(byte ser, byte command, byte[] data) {
        this.serializerAlgorithm = ser;
        this.command = command;
        this.data = data;
    }


    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 3. 实际编码过程
        byteBuf.writeInt(this.getMagicNum());
        byteBuf.writeByte(this.getVersion());
        byteBuf.writeByte(this.getSerializerAlgorithm());
        byteBuf.writeByte(this.getCommand());
        byteBuf.writeInt(this.getDataLength());
        byteBuf.writeBytes(this.getData());

        return byteBuf;
    }

    /**
     * 唯一需要考虑到 packet中各部分长度的方法，所以如果需要对长度进行修改，那么此方法需要覆盖
     *
     * @param buf
     * @return
     */
    @Override
    public Packet decode(ByteBuf buf) {

        Integer magicNum = buf.readInt();
        this.setMagicNum(magicNum);

        byte version = buf.readByte();
        this.setVersion(version);

        byte serializerAlgorithm = buf.readByte();
        this.setSerializerAlgorithm(serializerAlgorithm);

        byte command = buf.readByte();
        this.setCommand(command);

        Integer dataLength = buf.readInt();
        this.setDataLength(dataLength);

        byte[] data = new byte[dataLength];
        buf.readBytes(data);
        this.setData(data);

        return this;
    }

    /**
     * 作为packet 与command 之间的对应逻辑方法
     *
     * @return Command 里的byte 值
     */
    @Override
    public byte getCommandOperation() {
        return this.command;
    }

    /**
     * 作为packet 与SerializerAlgorithm 的对应逻辑方法
     *
     * @return SerializerAlgotithm 里的byte 值
     */
    @Override
    public byte getSerializerAlgorithm() {
        return this.serializerAlgorithm;
    }

}