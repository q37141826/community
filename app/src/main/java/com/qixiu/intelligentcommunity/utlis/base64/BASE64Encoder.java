package com.qixiu.intelligentcommunity.utlis.base64;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BASE64Encoder extends CharacterEncoder {
	private static final char[] pem_array = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };

	protected int bytesPerAtom() {
		return 3;
	}

	protected int bytesPerLine() {
		return 57;
	}

	protected void encodeAtom(OutputStream paramOutputStream,
			byte[] paramArrayOfByte, int paramInt1, int paramInt2)
			throws IOException {
		int i;
		int j;
		int k;
		if (paramInt2 == 1) {
			i = paramArrayOfByte[paramInt1];
			j = 0;
			k = 0;
			paramOutputStream.write(pem_array[(i >>> 2 & 0x3F)]);
			paramOutputStream
					.write(pem_array[((i << 4 & 0x30) + (j >>> 4 & 0xF))]);
			paramOutputStream.write(61);
			paramOutputStream.write(61);
		} else if (paramInt2 == 2) {
			i = paramArrayOfByte[paramInt1];
			j = paramArrayOfByte[(paramInt1 + 1)];
			k = 0;
			paramOutputStream.write(pem_array[(i >>> 2 & 0x3F)]);
			paramOutputStream
					.write(pem_array[((i << 4 & 0x30) + (j >>> 4 & 0xF))]);
			paramOutputStream
					.write(pem_array[((j << 2 & 0x3C) + (k >>> 6 & 0x3))]);
			paramOutputStream.write(61);
		} else {
			i = paramArrayOfByte[paramInt1];
			j = paramArrayOfByte[(paramInt1 + 1)];
			k = paramArrayOfByte[(paramInt1 + 2)];
			paramOutputStream.write(pem_array[(i >>> 2 & 0x3F)]);
			paramOutputStream
					.write(pem_array[((i << 4 & 0x30) + (j >>> 4 & 0xF))]);
			paramOutputStream
					.write(pem_array[((j << 2 & 0x3C) + (k >>> 6 & 0x3))]);
			paramOutputStream.write(pem_array[(k & 0x3F)]);
		}
	}

	/**
	 * 将bitmap转换成base64字符串
	 * 
	 * @param bitmap
	 * @return base64 字符串
	 */
	public static String bitmapToBase64(Bitmap bitmap) {
		String result = "";
		ByteArrayOutputStream bos = null;
		try {
			if (null != bitmap) {
				bos = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.JPEG, 90, bos);// 将bitmap放入字节数组流中

				bos.flush();// 将bos流缓存在内存中的数据全部输出，清空缓存
				bos.close();

				byte[] bitmapByte = bos.toByteArray();
				result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public String encodeToString(int rowBytes) {
		// TODO Auto-generated method stub
		return null;
	}
}