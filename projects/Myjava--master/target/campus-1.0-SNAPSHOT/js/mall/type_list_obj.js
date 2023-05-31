var pingguo = createObject(1, '经济学理论');
var sanxing = createObject(2, '投资理财');
var xiaomi = createObject(3, '财经税收');
var huawei = createObject(4, '银行与货币');
var zhongxing = createObject(5, '证券股票');
var lianxiang = createObject(6, '保险');
var meizhu = createObject(7, '国际金融');
var qitas = createObject(8, '国际金融');
var erjis = createObject(9, '其他');
var shouji = new Object();
shouji.name = '经济学';
shouji.content = [pingguo, sanxing, xiaomi, huawei, zhongxing, lianxiang, meizhu, qitas, erjis];
var putongxiangji = createObject(10, '会计审计统计');
var danfan = createObject(11, '会计课本');
var qitax = createObject(12, '其他');
var content = [putongxiangji, danfan, qitax];
var xiangji = createType('会计', content);
var bijiben = createObject(13, '国际经济与贸易');
var pingban = createObject(14, '投资学');
var taishi = createObject(15, '财政学');
var xianshiqi = createObject(16, '金融学');
var shubiao = createObject(17, '国民经济管理');
var yingpan = createObject(18, '体育经济');
var upan = createObject(19, '保险');
var yidongyingpan = createObject(20, '金融工程');
var qitad = createObject(21, '其他');
var content1 = [bijiben, pingban, taishi, xianshiqi, shubiao,
    yingpan, upan, yidongyingpan, qitad];
var diannao = createType('工具书', content1);
var xiyiji = createObject(22, '教辅材料');
var yinshuiji = createObject(23, '家庭教育');
var chuifengji = createObject(24, '教师课程');
var qitaj = createObject(30, '其他');
var content2 = [xiyiji, yinshuiji, chuifengji, qitaj];
var jiadian = createType('教学理论', content2);

var erjiy = createObject(31, '研究生考试');
var yinxiang = createObject(32, '资格职称考试');
var gongfang = createObject(33, '公务员考试');
var diyingpao = createObject(34, '教师资格考试');
var maikefeng = createObject(35, '其他');
var content3 = [erjiy, yinxiang, gongfang, diyingpao, maikefeng];
var yingyin = createType('考试书籍', content3);
var shangyi = createObject(36, '中国近代史');
var kuzi = createObject(37, '中国现代史');
var qunzi = createObject(38, '中国古代史');
var qitan = createObject(39, '其他');
var content4 = [shangyi, kuzi, qunzi, qitan];
var nvzhuang = createType('中国史', content4);
var shangyin = createObject(40, '欧洲美洲史');
var kuzin = createObject(41, '非洲亚洲史');
var qitanan = createObject(42, '其他');
var content5 = [shangyin, kuzin, qitanan];
var nanzhuang = createType('世界史', content5);
var yundongxie = createObject(43, '美术考古学');
var pixie = createObject(44, '宗教考古学');
var fanbuxie = createObject(45, '古钱学');
var qiuxie = createObject(46, '古文字学');
var banxie = createObject(47, '铭刻学');
var qitaxie = createObject(48, '其他');
var content6 = [yundongxie, pixie, fanbuxie, qiuxie, banxie, qitaxie];
var nvxie = createType('文物考古', content6);
var yundongxienan = createObject(49, '雪域文化');
var pixienan = createObject(50, '草原文化');
var fanbuxienan = createObject(51, '东北文化');
var qiuxienan = createObject(52, '中原文化');
var banxienan = createObject(53, '江南水乡文化');
var qitaxienan = createObject(54, '其他');
var content7 = [yundongxienan, pixienan, fanbuxienan, qiuxienan, banxienan, qitaxienan];
var nanxie = createType('地域文化', content7);
var beibao = createObject(55, '史料学');
var lvxingxiang = createObject(56, '年代学');
var qitaxiang = createObject(57, '其他');
var content8 = [beibao, lvxingxiang, qitaxiang];
var xiangbao = createType('史学理论', content8);
var jixiebiao = createObject(58, '子');
var shiyingbiao = createObject(59, '经');
var dianzibiao = createObject(60, '史');
var qitabiao = createObject(61, '集');
var content9 = [jixiebiao, shiyingbiao, dianzibiao, qitabiao];
var shoubiao = createType('国学古籍', content9);
var zuqiu = createObject(62, '计算机网络');
var yumaoqiupai = createObject(63, '编程语言');
var wangqiupai = createObject(64, '数据结构');
var lanqiu = createObject(65, '操作系统');
var lunhua = createObject(66, '嵌入式');
var bingbangqiupai = createObject(67, '单片机');
var huaban = createObject(68, 'linux');
var qitaqixie = createObject(69, '其他');
var content10 = [zuqiu, yumaoqiupai, wangqiupai, lanqiu, lunhua, bingbangqiupai, huaban, qitaqixie];
var qicai = createType('专业书籍', content10);
var zixingche = createObject(70, '计算机考研');
var diandongche = createObject(71, '计算机等级考试');
var qitadaibu = createObject(72, '其他');
var content11 = [zixingche, diandongche, qitadaibu];
var daibu = createType('专业习题', content11);
var wenxue = createObject(73, '颜料');
var manhua = createObject(74, '笔');
var xiaoshuo = createObject(75, '刷子');
var qitaxiuyang = createObject(76, '其他');
var content12 = [wenxue, manhua, xiaoshuo, qitaxiuyang];
var xiuyang = createType('彩绘文具', content12);
var shengwu = createObject(77, '信纸');
var huaxue = createObject(78, '稿纸');
var wuli = createObject(79, '笔记本');
var yuwen = createObject(80, '宣纸');
var waiyu = createObject(81, '打印纸');
var shuxue = createObject(82, '新闻纸');
var zhengzhi = createObject(83, '书皮纸');
var lishi = createObject(84, '凸版印刷纸');
var dili = createObject(85, '绘图用纸');
var jisuanji = createObject(86, '绘画用纸');
var jixie = createObject(87, '纸板');
var tumu = createObject(88, '加工纸');
var yishu = createObject(89, '艺术纸张');
var qitashu = createObject(90, '其他');
var content13 = [shengwu, huaxue, wuli, yuwen, waiyu, shuxue, zhengzhi, lishi, dili,
    jisuanji, jixie, tumu, yishu, qitashu];
var zhuanye = createType('纸本', content13);
var bi = createObject(91, '笔');
var qitawenju = createObject(92, '尺子');
var qitassss = createObject(93,'其他');
var content14 = [bi, qitawenju,qitassss];
var wenju = createType('文具', content14);
var qitaqita = createObject(94, '其他');
var content15 = [qitaqita];
var qitaA = createType('其他', content15);

var mzs = createObject(95,'毛泽东思想');
var dzs = createObject(96,'邓小平思想');
var lzs = createObject(97,'领导人思想');
var qzs = createObject(99,'其他');
var content16 = [mzs,dzs,lzs,qzs];
var six = createType('党的思想', content16);

var hzs = createObject(98,'贺龙作战思想');
var qitaxx = createObject(100,'其他');
var content17 = [hzs,qitaxx];
var junshi = createType('军事',content17);


var mks_qita = createObject(101,'其他');
var contemt18 = [mks_qita];
var SX = createType('其他',contemt18);
var type_list = [[shouji, xiangji, diannao], [jiadian, yingyin],
    [nvzhuang, nanzhuang, nvxie, nanxie, xiangbao, shoubiao], [qicai, daibu]
    , [xiuyang, zhuanye, wenju,qitaA], [six,junshi]];

function createObject(id, name) {
    var temp = new Object();
    temp.id = id;
    temp.name = name;
    return temp;
}

function createType(name, content) {
    var temp = new Object();
    temp.name = name;
    temp.content = content;
    return temp;
}

function getTypeList() {
    return type_list;
}