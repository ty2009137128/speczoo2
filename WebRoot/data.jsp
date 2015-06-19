
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!-- Website template by freewebsitetemplates.com -->
<html>
  <head>
    <meta charset="UTF-8">
    <title>About - Data-mining Based Expert Platform for the Spectral Inspection</title>
    <link rel="stylesheet" href="resources/css/style.css" type="text/css">
  </head>
  <body>
    <jsp:include page="jsp/layout/header.jsp"/>
    <div id="contents">
      <div class="wrapper clearfix">
        <div id="sidebar">
          <ul>
            <li>
              <a href="#sec0">光谱参数星表</a>
            </li>
            <li>
              <a href="#sec1">1. LAMOST 总星表</a>
            </li>
            <li>
              <a href="#sec2">2. A型星星表</a>
            </li>
            <li>
              <a href="#sec3">3. A、F、G and K 型星星表</a>
            </li>
            <li>
              <a href="#sec4">4. M 型矮星星表</a>
            </li>
            <li>
              <a href="#sec5">5. 观测天区信息星表</a>
            </li>
          </ul>
        </div>
        <div class="main">
          <h2><span id="sec0">光谱参数星表</span></h2>

          <p>这里将介绍LAMOST的五个光谱参数星表，用户可以从<a href="http://data.lamost.org/dr1/catalogue">http://data.lamost.org/dr1/catalogue</a>网站上下载这些星表。这五个星表分别是LAMOST总星表、A型星星表、A、F、 G 、K型星星表、M型矮星星表和观测天区信息星表，它们包含了LAMOST先导巡天和正式巡天第一年获得的光谱数据。这里需要注意的是，A型星星表、A、 F、 G 和 K型星星表和M型矮星星表都是LAMOST总星表的子集。此外，除星系，类星体和光谱型不确定的天体外，绝大部分没有包含在这三个子星表中的目标是不满足A、F、 G 和K型星星表选星条件的F、G 和K型恒星，剩下少部分目标是O型星、B型星、白矮星、发射线型、碳星和光谱双星等。

          表4、表5、表6、表7和表8分别给出了前面提到的五个光谱星表中的所有字段，同时也给出了各个字段的详细说明。这些星表中大多数字段已经在2.2.1小节中解释过，下面五个小节3.1、3.2、3.3、3.4和3.5只介绍前面章节没有解释过的字段。</p>

          <h3><span id="sec1">1 LAMOST 总星表</span></h3>

          <p>本节，我们将介绍LAMOST总星表。这个星表中包括了先导巡天获取的717,469条光谱，其中有648,746条恒星光谱，2,723条星系光谱，621条类星体光谱和65,406条类型未知的天体光谱。此外，该星表中还包括了1,487,200条正式巡天获取的光谱，其中有1,295,583条恒星光谱，9,359条星系光谱，4396条类星体光谱以及177,862条类型未知的天体光谱。因此，该星表总共发布了2,204,696条光谱，其中包括1,944,329条恒星光谱，12,082条星系光谱，5,017条类星体光谱和243,268条类型未知的天体光谱。该星表中g波段信噪比大于10的光谱有1,186,132条，i波段信噪比大于10的光谱有1,680,794条，以及g波段和i波段信噪比都大于10的光谱共有1,746,202条。</p>

          <p>表4给出了该星表中的参数，大多数参数已经在2.2.1小节中介绍过了。这里我们应该注意的是，“designation”是2.1小节中提到的目标源名称，“specId”是每条光谱独一无二的编号，在LAMOST不同星表中进行交叉时可以用到这个字段，“plateId”是一个整数给出了每个天区的编号， “dataVersion”与2.2.1.8小节中解释过的“VERSPIPE”关键字一样，是数据的版本号，“Info”是选源者在SDSS、UCAC4、PANSTAR和其它星表中挑选的源在这些星表中的编号。对于星系和类星体，“z” 和“z_err”是它们的红移和误差，如果红移无法估计出来，它们则被设置为“-9999.00000000”。对于恒星，“RV”和“elodierv”是它们的日心视向速度，“elodierv_err”则是日心视向速度“elodierv”的误差。值得注意的是：“elodierv”是通过与ELODIE网络模板匹配得到的，而“RV”则有三种不同的计算方法。对于能够用ULYSS软件计算出大气参数的恒星，它们的“RV”来自于表6中的“RV”，对于M型恒星，它们的“RV”来自于表7中的“RV”，对于其它恒星，它们的“RV”则来自于表4中“elodierv”。此外，如果视向速度无法估计出来，则“RV”、 “elodierv”和“elodierv_err”被设置为“-9999.00000000”。还需要注意的是，对于类型未知的天体，它们的“z”和“RV”字段都被设置为“-9999.00000000”。此外，对于星系和类星体，它们的“RV”字段是空的，对于恒星，它们的“z”字段是空的。</p>

          <p>我们提供了两种格式的LAMOST 总星表，用户可以在http://data.lamost.org/dr1/catalogue网站下载这两个星表。其中一个是FITS格式的星表，另一个是CSV格式的星表，这两个星表的内容是相同的。</p>

          <h5>表4：LAMOST总星表</h5>

          <table class="table table-hover table-bordered">
            <thead>
              <tr>
                <th>字段（单位）  </th>
                <th> 数据类型  </th>
                <th> 说明</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>specId  </td>
                <td> 长整型 </td>
                <td> 光谱编号</td>
              </tr>
              <tr>
                <td>designation </td>
                <td> 变长字符型   </td>
                <td> 目标名称</td>
              </tr>
              <tr>
                <td>obsDate </td>
                <td> 字符型 </td>
                <td> 目标观测日期</td>
              </tr>
              <tr>
                <td>plateId </td>
                <td> 整形    </td>
                <td> 天区编号</td>
              </tr>
              <tr>
                <td>lmjd    </td>
                <td> 字符型 </td>
                <td> 本地修正儒略日</td>
              </tr>
              <tr>
                <td>planId  </td>
                <td> 字符型 </td>
                <td> 天区名称</td>
              </tr>
              <tr>
                <td>spId    </td>
                <td> 整型    </td>
                <td> 光谱仪号</td>
              </tr>
              <tr>
                <td>fiberId </td>
                <td> 整型    </td>
                <td> 光纤号</td>
              </tr>
              <tr>
                <td>RA (degree) </td>
                <td> 浮点型 </td>
                <td> 赤经</td>
              </tr>
              <tr>
                <td>Dec (degree)    </td>
                <td> 浮点型 赤纬</td>
                <td></td>
              </tr>
              <tr>
                <td>snru    </td>
                <td> 浮点型 </td>
                <td> u波段信噪比</td>
              </tr>
              <tr>
                <td>snrg    </td>
                <td> 浮点型 </td>
                <td> g波段信噪比</td>
              </tr>
              <tr>
                <td>snrr    </td>
                <td> 浮点型 </td>
                <td> r波段信噪比</td>
              </tr>
              <tr>
                <td>snri    </td>
                <td> 浮点型 </td>
                <td> i波段信噪比</td>
              </tr>
              <tr>
                <td>snrz    </td>
                <td> 浮点型 </td>
                <td> z波段信噪比</td>
              </tr>
              <tr>
                <td>objType </td>
                <td> 变长字符型   </td>
                <td> 目标类型</td>
              </tr>
              <tr>
                <td>class   </td>
                <td> 变长字符型   </td>
                <td> 光谱类型</td>
              </tr>
              <tr>
                <td>subClass    </td>
                <td> 变长字符型   </td>
                <td> 恒星子分类</td>
              </tr>
              <tr>
                <td>magType </td>
                <td> 变长字符型   </td>
                <td> 目标星等类型</td>
              </tr>
              <tr>
                <td>mag1 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等1</td>
              </tr>
              <tr>
                <td>mag2 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等2</td>
              </tr>
              <tr>
                <td>mag3 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等3</td>
              </tr>
              <tr>
                <td>mag4 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等4</td>
              </tr>
              <tr>
                <td>mag5 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等5</td>
              </tr>
              <tr>
                <td>mag6 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等6</td>
              </tr>
              <tr>
                <td>mag7 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等7</td>
              </tr>
              <tr>
                <td>tsource </td>
                <td> 变长字符型   </td>
                <td> 提交输入星表的组织或个人</td>
              </tr>
              <tr>
                <td>fiberType   </td>
                <td> 变长字符型   </td>
                <td> 光纤类型 [目标光纤，天光光纤, 流量标准星光纤, 未使用光纤，走位错误光纤，死光纤]</td>
              </tr>
              <tr>
                <td>tfrom   </td>
                <td> 变长字符型   </td>
                <td> 组织或个人提交的星表</td>
              </tr>
              <tr>
                <td>tInfo   </td>
                <td> 变长字符型   </td>
                <td> SDSS、UCAC4、PANSTAR和其它星表中的目标编号</td>
              </tr>
              <tr>
                <td>z   </td>
                <td> 浮点型 </td>
                <td> 红移</td>
              </tr>
              <tr>
                <td>RV (km/s)   </td>
                <td> 浮点型 </td>
                <td> 日心视向速度</td>
              </tr>
              <tr>
                <td>z_err   </td>
                <td> 浮点型 </td>
                <td> 红移不确定度</td>
              </tr>
              <tr>
                <td>elodierv    </td>
                <td> 浮点型 </td>
                <td> 与ELODIE网格模板匹配得到的日心视向速度</td>
              </tr>
              <tr>
                <td>elodierv_err    </td>
                <td> 浮点型 </td>
                <td> 日心视向速度的不确定度</td>
              </tr>
            </tbody>
          </table>

          <p></p>
          <h3><span id="sec2">2. A型星星表</span></h3>

          <p>这一小节，我们将介绍A型星星表。这个星表共发布了先导和正式巡天期间获得的<code>100,073</code>颗A型星，表5列出了它们的参数信息，
            其中光谱分类参数“<code>class</code>”字段给出了LAMOST分析软件提供的A型星的光谱型和光度型，几乎所有的A型
            星都有光度型。“<code>RV</code>”和“<code>RV_err</code>”分别是日心视向速度和不确定度，“<code>RV</code>”有两种来
            源。对于ULYSS软件能够测出大气参数的A型星，它们的日心视向速度“<code>RV</code>”来自表6中的“<code>RV</code>”，对于其它的A型星，它们的视
            向速度来自表4中的中的“<code>elodierv</code>”。此外， “<code>HalphaIndex</code>”、
            “<code>HbetaIndex</code>”、“<code>HgamaIndex</code>” 和“<code>HdeltaIndex</code>”是四个巴尔末
            线的线指数，“<code>HalphaD0.2</code>”、“<code>HbetaD0.2</code>”、“<code>HgamaD0.2</code>”和“<code>HdeltaD0.2</code>”是这四条谱线
            局部连续谱下20%处的谱线宽度。如果这八个值无法得到，则被设置为-9999.00000000。我们在<a href="http://data.lamost.org/dr1/catalogue">http://data.lamost.org/dr1/catalogue</a>网站上提供了两个格式A型星星表，一种是FITS格式星表，一种是CSV格式星表，用户可以从这个网站下载该星表。</p>

          <h5>表5：A型星星表</h5>

          <table class="table table-hover table-bordered">
            <thead>
              <tr>
                <th>字段（单位）  </th>
                <th> 数据类型  </th>
                <th> 说明</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>specId  </td>
                <td> 长整型 </td>
                <td> 光谱编号</td>
              </tr>
              <tr>
                <td>designation </td>
                <td> 变长字符型   </td>
                <td> 目标名称</td>
              </tr>
              <tr>
                <td>obsDate </td>
                <td> 字符型 </td>
                <td> 目标观测日期</td>
              </tr>
              <tr>
                <td>plateId </td>
                <td> 整形    </td>
                <td> 天区编号</td>
              </tr>
              <tr>
                <td>lmjd    </td>
                <td> 字符型 </td>
                <td> 本地修正儒略日</td>
              </tr>
              <tr>
                <td>planId  </td>
                <td> 字符型 </td>
                <td> 天区名称</td>
              </tr>
              <tr>
                <td>spId    </td>
                <td> 整型    </td>
                <td> 光谱仪号</td>
              </tr>
              <tr>
                <td>fiberId </td>
                <td> 整型    </td>
                <td> 光纤号</td>
              </tr>
              <tr>
                <td>RA (degree) </td>
                <td> 浮点型 </td>
                <td> 赤经</td>
              </tr>
              <tr>
                <td>Dec (degree)    </td>
                <td> 浮点型 赤纬</td>
                <td></td>
              </tr>
              <tr>
                <td>snru    </td>
                <td> 浮点型 </td>
                <td> u波段信噪比</td>
              </tr>
              <tr>
                <td>snrg    </td>
                <td> 浮点型 </td>
                <td> g波段信噪比</td>
              </tr>
              <tr>
                <td>snrr    </td>
                <td> 浮点型 </td>
                <td> r波段信噪比</td>
              </tr>
              <tr>
                <td>snri    </td>
                <td> 浮点型 </td>
                <td> i波段信噪比</td>
              </tr>
              <tr>
                <td>snrz    </td>
                <td> 浮点型 </td>
                <td> z波段信噪比</td>
              </tr>
              <tr>
                <td>objType </td>
                <td> 变长字符型   </td>
                <td> 目标类型</td>
              </tr>
              <tr>
                <td>class   </td>
                <td> 变长字符型   </td>
                <td> 光谱类型</td>
              </tr>
              <tr>
                <td>subClass    </td>
                <td> 变长字符型   </td>
                <td> 恒星子分类</td>
              </tr>
              <tr>
                <td>magType </td>
                <td> 变长字符型   </td>
                <td> 目标星等类型</td>
              </tr>
              <tr>
                <td>mag1 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等1</td>
              </tr>
              <tr>
                <td>mag2 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等2</td>
              </tr>
              <tr>
                <td>mag3 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等3</td>
              </tr>
              <tr>
                <td>mag4 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等4</td>
              </tr>
              <tr>
                <td>mag5 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等5</td>
              </tr>
              <tr>
                <td>mag6 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等6</td>
              </tr>
              <tr>
                <td>mag7 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等7</td>
              </tr>
              <tr>
                <td>tsource </td>
                <td> 变长字符型   </td>
                <td> 提交输入星表的组织或个人</td>
              </tr>
              <tr>
                <td>fiberType   </td>
                <td> 变长字符型   </td>
                <td> 光纤类型 [目标光纤，天光光纤, 流量标准星光纤, 未使用光纤，走位错误光纤，死光纤]</td>
              </tr>
              <tr>
                <td>tfrom   </td>
                <td> 变长字符型   </td>
                <td> 组织或个人提交的星表</td>
              </tr>
              <tr>
                <td>tInfo   </td>
                <td> 变长字符型   </td>
                <td> SDSS、UCAC4、PANSTAR和其它星表中的目标编号</td>
              </tr>
              <tr>
                <td>RV(km/s)    </td>
                <td> 浮点型 </td>
                <td> 日心视向速度</td>
              </tr>
              <tr>
                <td>RV_err (km/s)   </td>
                <td> 浮点型 </td>
                <td> 日心视向速度不确定度</td>
              </tr>
              <tr>
                <td>HalphaIndex (angstrom)  </td>
                <td> 浮点型  </td>
                <td> Halpha谱线指数</td>
              </tr>
              <tr>
                <td>HbetaIndex (angstrom)   </td>
                <td> 浮点型 </td>
                <td> Hbeta谱线指数</td>
              </tr>
              <tr>
                <td>HgamaIndex (angstrom)   </td>
                <td> 浮点型 </td>
                <td> Hgama谱线指数</td>
              </tr>
              <tr>
                <td>HdeltaIndex (angstrom)  </td>
                <td> 浮点型 </td>
                <td> Hdelta line</td>
              </tr>
              <tr>
                <td>HalphaD0.2 (angstrom)   </td>
                <td> 浮点型 </td>
                <td> Halpha线局部连续谱下20%处线宽</td>
              </tr>
              <tr>
                <td>HbetaD0.2 (angstrom)    </td>
                <td> 浮点型 </td>
                <td> Hbeta线局部连续谱下20%处线宽</td>
              </tr>
              <tr>
                <td>HgamaD0.2 (angstrom)    </td>
                <td> 浮点型 </td>
                <td> Hgama线局部连续谱下20%处线宽</td>
              </tr>
              <tr>
                <td>HdeltaD0.2 (angstrom)   </td>
                <td> 浮点型 </td>
                <td> Hdelta线局部连续谱下20%处线宽</td>
              </tr>
            </tbody>
          </table>


          <p></p>
          <h3><span id="sec3">3. A、F、G and K 型星星表</span></h3>

          <p>这一小节，我们将介绍A、F、G和K型星星表。这个星表中共发布了1,061,918颗恒星，其中包括38,190颗A型星、548,219颗F型星，253,279颗G型星，222,230颗K型星，
            这些星是按照如下标准挑选出来的：暗月夜g波段信噪比大于6，以及亮月夜g波段星等信噪比大于20。表6列出了这些星的参数，其中“teff”、 “logg”、 “FeH”、 “teff_err”、
            “log_err”、 “FeH_err”分别给出了由LAMOST恒星参数软件测量的有效温度、表面重力、金属丰度以及误差。此外，“RV”和“RV_err”分别是日心视向速度及其不确定度，“RV”是由ULYSS软件测量
            的。值得注意的是，这个星表中的A型星是A型星星表的子集且信噪比高， 而A型星星表中的其它A型星的信噪比低。<a href="http://data.lamost.org/dr1/catalogue">http://data.lamost.org/dr1/catalogue</a>网站上提供了两个格式A型星星表，一种是FITS格式星表，一种是CSV格式星表，用户可以从这个网站下载该星表。</p>

          <h5>表6：A、F、G 和K型星星表</h5>

          <table class="table table-hover table-bordered">
            <thead>
              <tr>
                <th>字段（单位）  </th>
                <th> 数据类型  </th>
                <th> 说明</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>specId  </td>
                <td> 长整型 </td>
                <td> 光谱编号</td>
              </tr>
              <tr>
                <td>designation </td>
                <td> 变长字符型   </td>
                <td> 目标名称</td>
              </tr>
              <tr>
                <td>obsDate </td>
                <td> 字符型 </td>
                <td> 目标观测日期</td>
              </tr>
              <tr>
                <td>plateId </td>
                <td> 整形    </td>
                <td> 天区编号</td>
              </tr>
              <tr>
                <td>lmjd    </td>
                <td> 字符型 </td>
                <td> 本地修正儒略日</td>
              </tr>
              <tr>
                <td>planId  </td>
                <td> 字符型 </td>
                <td> 天区名称</td>
              </tr>
              <tr>
                <td>spId    </td>
                <td> 整型    </td>
                <td> 光谱仪号</td>
              </tr>
              <tr>
                <td>fiberId </td>
                <td> 整型    </td>
                <td> 光纤号</td>
              </tr>
              <tr>
                <td>RA (degree) </td>
                <td> 浮点型 </td>
                <td> 赤经</td>
              </tr>
              <tr>
                <td>Dec (degree)    </td>
                <td> 浮点型 赤纬</td>
                <td></td>
              </tr>
              <tr>
                <td>snru    </td>
                <td> 浮点型 </td>
                <td> u波段信噪比</td>
              </tr>
              <tr>
                <td>snrg    </td>
                <td> 浮点型 </td>
                <td> g波段信噪比</td>
              </tr>
              <tr>
                <td>snrr    </td>
                <td> 浮点型 </td>
                <td> r波段信噪比</td>
              </tr>
              <tr>
                <td>snri    </td>
                <td> 浮点型 </td>
                <td> i波段信噪比</td>
              </tr>
              <tr>
                <td>snrz    </td>
                <td> 浮点型 </td>
                <td> z波段信噪比</td>
              </tr>
              <tr>
                <td>objType </td>
                <td> 变长字符型   </td>
                <td> 目标类型</td>
              </tr>
              <tr>
                <td>class   </td>
                <td> 变长字符型   </td>
                <td> 光谱类型</td>
              </tr>
              <tr>
                <td>subClass    </td>
                <td> 变长字符型   </td>
                <td> 恒星子分类</td>
              </tr>
              <tr>
                <td>magType </td>
                <td> 变长字符型   </td>
                <td> 目标星等类型</td>
              </tr>
              <tr>
                <td>mag1 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等1</td>
              </tr>
              <tr>
                <td>mag2 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等2</td>
              </tr>
              <tr>
                <td>mag3 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等3</td>
              </tr>
              <tr>
                <td>mag4 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等4</td>
              </tr>
              <tr>
                <td>mag5 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等5</td>
              </tr>
              <tr>
                <td>mag6 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等6</td>
              </tr>
              <tr>
                <td>mag7 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等7</td>
              </tr>
              <tr>
                <td>tsource </td>
                <td> 变长字符型   </td>
                <td> 提交输入星表的组织或个人</td>
              </tr>
              <tr>
                <td>fiberType   </td>
                <td> 变长字符型   </td>
                <td> 光纤类型 [目标光纤，天光光纤, 流量标准星光纤, 未使用光纤，走位错误光纤，死光纤]</td>
              </tr>
              <tr>
                <td>tfrom   </td>
                <td> 变长字符型   </td>
                <td> 组织或个人提交的星表</td>
              </tr>
              <tr>
                <td>tInfo   </td>
                <td> 变长字符型   </td>
                <td> SDSS、UCAC4、PANSTAR和其它星表中的目标编号</td>
              </tr>
              <tr>
                <td>RV(km/s)    </td>
                <td> 浮点型 </td>
                <td> 日心视向速度</td>
              </tr>
              <tr>
                <td>RV_err (km/s)   </td>
                <td> 浮点型 </td>
                <td> 日心视向速度不确定度</td>
              </tr>
              <tr>
                <td>logg (dex)  </td>
                <td> 浮点型 </td>
                <td> ULYSS测量的表面重力</td>
              </tr>
              <tr>
                <td>logg_err (dex)  </td>
                <td> 浮点型 </td>
                <td> 表面重力不确定度</td>
              </tr>
              <tr>
                <td>teff (K)    </td>
                <td> 浮点型 </td>
                <td> ULYSS测量的有效温度</td>
              </tr>
              <tr>
                <td>teff _err (K)   </td>
                <td> 浮点型 </td>
                <td> 有效温度不确定度</td>
              </tr>
              <tr>
                <td>FeH (dex)   </td>
                <td> 浮点型 </td>
                <td> ULYSS测量的金属丰度</td>
              </tr>
              <tr>
                <td>FeH_err (dex)   </td>
                <td> 浮点型 </td>
                <td> 金属丰度不确定度</td>
              </tr>
            </tbody>
          </table>


          <p></p>
          <h3><span id="sec4">4. M 型矮星星表</span></h3>

          <p>这一小节，我们将介绍M型矮星星表。此次共发布了121,522颗M型矮星，表7列出了它们的参数。除了前面三个表中都有的参数外，我们还额外提
            供了磁活动标记“magact”。“magact”标记有三个取值，分别是：0,1，-9999，其中“1”表示一颗M型矮星存在磁活动，“0”表示不存在磁活动，“-9999”表示因信噪比
            过低无法确定是否存在磁活动。此外，“RV”和“RV_err”分别是日心视向速度及其不确定度，“RV”是通过模板匹配方法得到的。 <a href="http://data.lamost.org/dr1/catalogue">http://data.lamost.org/dr1/catalogue</a>网站上提供了两个格式A型星星表，一种是FITS格式星表，一种是CSV格式星表，用户可以从这个网站下载该星表。</p>

          <h5>表7：M型矮星星表</h5>

          <table class="table table-hover table-bordered">
            <thead>
              <tr>
                <th>字段（单位）  </th>
                <th> 数据类型  </th>
                <th> 说明</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>specId  </td>
                <td> 长整型 </td>
                <td> 光谱编号</td>
              </tr>
              <tr>
                <td>designation </td>
                <td> 变长字符型   </td>
                <td> 目标名称</td>
              </tr>
              <tr>
                <td>obsDate </td>
                <td> 字符型 </td>
                <td> 目标观测日期</td>
              </tr>
              <tr>
                <td>plateId </td>
                <td> 整形    </td>
                <td> 天区编号</td>
              </tr>
              <tr>
                <td>lmjd    </td>
                <td> 字符型 </td>
                <td> 本地修正儒略日</td>
              </tr>
              <tr>
                <td>planId  </td>
                <td> 字符型 </td>
                <td> 天区名称</td>
              </tr>
              <tr>
                <td>spId    </td>
                <td> 整型    </td>
                <td> 光谱仪号</td>
              </tr>
              <tr>
                <td>fiberId </td>
                <td> 整型    </td>
                <td> 光纤号</td>
              </tr>
              <tr>
                <td>RA (degree) </td>
                <td> 浮点型 </td>
                <td> 赤经</td>
              </tr>
              <tr>
                <td>Dec (degree)    </td>
                <td> 浮点型 赤纬</td>
                <td></td>
              </tr>
              <tr>
                <td>snru    </td>
                <td> 浮点型 </td>
                <td> u波段信噪比</td>
              </tr>
              <tr>
                <td>snrg    </td>
                <td> 浮点型 </td>
                <td> g波段信噪比</td>
              </tr>
              <tr>
                <td>snrr    </td>
                <td> 浮点型 </td>
                <td> r波段信噪比</td>
              </tr>
              <tr>
                <td>snri    </td>
                <td> 浮点型 </td>
                <td> i波段信噪比</td>
              </tr>
              <tr>
                <td>snrz    </td>
                <td> 浮点型 </td>
                <td> z波段信噪比</td>
              </tr>
              <tr>
                <td>objType </td>
                <td> 变长字符型   </td>
                <td> 目标类型</td>
              </tr>
              <tr>
                <td>class   </td>
                <td> 变长字符型   </td>
                <td> 光谱类型</td>
              </tr>
              <tr>
                <td>subClass    </td>
                <td> 变长字符型   </td>
                <td> 恒星子分类</td>
              </tr>
              <tr>
                <td>magType </td>
                <td> 变长字符型   </td>
                <td> 目标星等类型</td>
              </tr>
              <tr>
                <td>mag1 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等1</td>
              </tr>
              <tr>
                <td>mag2 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等2</td>
              </tr>
              <tr>
                <td>mag3 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等3</td>
              </tr>
              <tr>
                <td>mag4 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等4</td>
              </tr>
              <tr>
                <td>mag5 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等5</td>
              </tr>
              <tr>
                <td>mag6 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等6</td>
              </tr>
              <tr>
                <td>mag7 (mag)  </td>
                <td> 浮点型 </td>
                <td> 星等7</td>
              </tr>
              <tr>
                <td>tsource </td>
                <td> 变长字符型   </td>
                <td> 提交输入星表的组织或个人</td>
              </tr>
              <tr>
                <td>fiberType   </td>
                <td> 变长字符型   </td>
                <td> 光纤类型 [目标光纤，天光光纤, 流量标准星光纤, 未使用光纤，走位错误光纤，死光纤]</td>
              </tr>
              <tr>
                <td>tfrom   </td>
                <td> 变长字符型   </td>
                <td> 组织或个人提交的星表</td>
              </tr>
              <tr>
                <td>tInfo   </td>
                <td> 变长字符型   </td>
                <td> SDSS、UCAC4、PANSTAR和其它星表中的目标编</td>
              </tr>
              <tr>
                <td>RV(km/s)    </td>
                <td> 浮点型 </td>
                <td> 日心视向速度</td>
              </tr>
              <tr>
                <td>RV_err (km/s)   </td>
                <td> 浮点型 </td>
                <td> 日心视向速度不确定度</td>
              </tr>
              <tr>
                <td>magact  </td>
                <td> 浮点型 </td>
                <td> 磁活动</td>
              </tr>
            </tbody>
          </table>


          <p></p>
          <h3><span id="sec5">5. 观测天区信息星表</span></h3>

          <p>这一小节，我们将介绍观测天区信息星表。除了其它四个表中包含的“<code>plateId</code>”、 “<code>obsDate</code>”和“<code>planId</code>” 字段，我们还在这个星表中发布了如表8所示的1202个天区的5个基本信息。“<code>RA</code>”和“<code>Dec</code>”是每个天区中央星的赤经赤纬，“<code>Seeing</code>”是第一次曝光时圆顶内的视宁度，“expTime”是n次曝光的总曝光时间，“<code>LMJM</code>”是每个天区开始观测时刻的本地修正儒略分。
            <a href="http://data.lamost.org/dr1/catalogue">http://data.lamost.org/dr1/catalogue</a>网站上提供了两个格式A型星星表，一种是FITS格式星表，一种是CSV格式星表，用户可以从这个网站下载该星表。</p>

          <h5>表8：观测天区信息星表</h5>

          <table class="table table-hover table-bordered">
            <thead>
              <tr>
                <th>字段（单位）  </th>
                <th> 数据类型  </th>
                <th> 说明</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>plateId </td>
                <td> 整型    </td>
                <td> 天区编号</td>
              </tr>
              <tr>
                <td>obsDate </td>
                <td> 浮点型 </td>
                <td> 观测天区</td>
              </tr>
              <tr>
                <td>planId  </td>
                <td> 字符型 </td>
                <td> 天区名称</td>
              </tr>
              <tr>
                <td>RA (degree) </td>
                <td> 浮点型 </td>
                <td> 每个天区中央星赤经</td>
              </tr>
              <tr>
                <td>Dec (degree)    </td>
                <td> 浮点型 </td>
                <td> 每个天区中央星赤纬</td>
              </tr>
              <tr>
                <td>Seeing  </td>
                <td> 浮点型 </td>
                <td> 第一次曝光时视宁度</td>
              </tr>
              <tr>
                <td>expTime (second)    </td>
                <td> 浮点型 </td>
                <td> N次曝光总的曝光时间</td>
              </tr>
              <tr>
                <td>LMJM    </td>
                <td> 整型    </td>
                <td> 每个天区开始观测时本地修正儒略分</td>
              </tr>
            </tbody>
          </table>

          <p/>
        </div>
      </div>
    </div>

    <jsp:include page="jsp/layout/footer.jsp"/>   
  </body>
</html>