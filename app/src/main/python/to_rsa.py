import base64
from Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_v1_5 as Cipher_PKC



class HandleRSA():


    # Server使用Client的公钥对内容进行rsa 加密
    def encrypt(self, plaintext):
        """
        client 公钥进行加密
        plaintext:需要加密的明文文本，公钥加密，私钥解密
        """

        # 加载公钥
        pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRLEXca3sxwl+AitpTMt3f2Pf7\n" + \
        "qeONS+AdDT+eph/6n73y2bG2reQQJf6ymqxG61RizLA1+GaOObOyhkWMAk6Eo8yF\n" + \
        "cdbMVbblOq8mCIKIPkCSs31W/pI5NbFmNIwKAdvG1l0pCPOW4NIaL1FzrpNjxijh\n" + \
        "G3Tunam3FzTp27iXZQIDAQAB"
        rsa_key = RSA.import_key(open("/storage/emulated/0/1/server_public.pem").read())
        #rsa_key = RSA.import_key(pubkey.encode("utf-8"))

        # 加密
        cipher_rsa = Cipher_PKC.new(rsa_key)
        en_data = cipher_rsa.encrypt(plaintext.encode("utf-8")) # 加密

        # base64 进行编码
        base64_text = base64.b64encode(en_data)

        return base64_text.decode() # 返回字符串

    # Client使用自己的私钥对内容进行rsa 解密
    def decrypt(self, en_data):
        """
        en_data:加密过后的数据，传进来是一个字符串
        """
        # base64 解码
        base64_data = base64.b64decode(en_data.encode("utf-8"))

        # 读取私钥
        private_key = RSA.import_key(open("client_private.pem").read())

        # 解密
        cipher_rsa = Cipher_PKC.new(private_key)
        data = cipher_rsa.decrypt(base64_data,None)

        return data.decode()

    # Server使用自己的私钥对内容进行签名




def t_result(ori):

    mrsa = HandleRSA()
    #mrsa.create_rsa_key()
    e = mrsa.encrypt(ori)
    return e