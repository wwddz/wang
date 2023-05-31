$(function () {
    var sumNum;
    // 按照下面这个方式添加新的元素，如果想在开头位置添加，就用first  before
    // $('.table_content:last').after("<tr class='table_content'><td><span>11111</span></td></tr>");
    calSum();
    var which = 0;
    var address;
    $('.shipping_address').click(function () {
        var id = $(this).attr('id');
        $('.shipping_address').each(function () {
            if ($(this).attr('id') == id) {
                $(this).css({"border-color": "slateblue"});
                which = id;
                address = $(this).children(".address_info").text();
                console.log("address = " + address);
            } else {
                $(this).css({"border-color": "rgba(0,0,0,0.1)"});
            }
        });
    });

    // -号，不能低于 1
    $('span.minus').click(function () {
        var num = $(this).siblings(".number").html();
        var num_i= num;
        if (num >= 2) {
            num--;
        }
        if(num_i != num){
            var id = $(this).attr('value');
            var sid = $(this).parent().siblings(".show_img").children().attr("value");
            var type = 0; // 0  -
            if(num != 1){
                console.log(type);
                $.ajax({
                    url: '/updateShopCar.do',
                    dataType: 'JSON',
                    type: 'post',
                    data: {id: id, sid: sid, quantity: num,type:type},
                    success: function (data) {
                        var result = data.result;
                        console.log(result);
                        if (result != 1) {
                            //layer.msg('首', {icon: 1, time: 2000});
                            window.setTimeout(function () {
                                window.location.href = 'shopping_cart.do?result=false';
                            },2200);
                        }
                    }
                });
            }
            $(this).siblings(".number").html(num);
            var cost = $(this).parent().siblings(".cost").children("span").html();
            $(this).parent().siblings(".per_sum").children("span").html(returnFloat(cost * num));

            calSum();
        }
    });
    $('span.add').click(function () {
        var num = $(this).siblings(".number").html();
        var id = $(this).attr('value');
        var sid = $(this).parent().siblings(".show_img").children().attr("value");
        var type =  1; // 1 +
        $.ajax({
            url: '/updateShopCar.do',
            dataType: 'JSON',
            type: 'post',
            data: {id: id, sid: sid, quantity: num,type:type},
            success: function (data) {
                var result = data.result;
                console.log(result);
                if (result != 1) {
                    layer.msg('商品不足', {icon: 2, time: 2000});
                    window.setTimeout(function () {
                        window.location.href = 'shopping_cart.do?result=false';
                    },2200);
                }
            }
        });
        num++
        $(this).siblings(".number").html(num);
        var cost = $(this).parent().siblings(".cost").children("span").html();
        $(this).parent().siblings(".per_sum").children("span").html(returnFloat(cost * num));
        calSum();
    });

    $('.table_content td.input_checkbox input').change(function () {
        calSum();
    });
    $('#all').change(function () {
        if ($(this).is(":checked")) {
            $("input[name='checkbox']").attr("checked", "true");
        } else {
            $("input[name='checkbox']").removeAttr("checked");
        }
        calSum();
    });

    //  保留两位小数
    function returnFloat(value) {
        var value = Math.round(parseFloat(value) * 100) / 100;
        var xsd = value.toString().split(".");
        if (xsd.length == 1) {
            value = value.toString() + ".00";
            return value;
        }
        if (xsd.length > 1) {
            if (xsd[1].length < 2) {
                value = value.toString() + "0";
            }
            return value;
        }
    }

    // 计算总金额
    function calSum() {
        var sum = 0;
        $('.cart_content table tr.table_content').each(function () {
            var isCheck = $(this).children("td.input_checkbox").children("input").is(":checked");
            if (isCheck) {
                sum += parseFloat($(this).children(".per_sum").children("span").html());
            }
        });
        sum = returnFloat(sum);
        sumNum = returnFloat(sum);
        $('.end_pay').children(".all_sum").children("span").html(sum);
    }

    //删除按钮
    $('.deleteShopCar').click(function () {
        var layer = layui.layer;
        var id = $(this).attr('value');
        var quantity = $(this).parent().siblings(".count").children(".number").text();
        console.log(quantity);
        var sid = $(this).parent().siblings(".show_img").children().attr("value");
        layer.confirm('确认删除?', {btn: ['确定', '取消'], icon: 3, title: '提示'}, function () {
            $.ajax({
                url: '/deleteShopCar.do',
                dataType: 'JSON',
                type: 'post',
                data: {id: id, sid: sid, quantity: quantity},
                success: function (data) {
                    var result = data.result;
                    if (result == 2) {
                        layer.msg('您还没有登录，请先登录', {icon: 5, time: 2000});
                        window.setTimeout(function () {
                            window.location.herf='/';
                        },2200);
                    } else if (result == 1) {
                        layer.msg('删除成功', {icon: 1, time: 2000});
                        window.setTimeout(function () {
                            window.location.href = 'shopping_cart.do?result=删除成功';
                        },2200);
                    } else if (result == 0) {
                        layer.msg('删除失败，请检测网络', {icon: 2, time: 2000});
                    } else {
                        layer.msg('删除失败，请检测网络', {icon: 2, time: 2000});
                    }
                }
            });
        });
    });

    /*    //提交订单按钮
        $("#submit_btn").click(function () {
            //获取商品id,数量, 用户地址
            var data = {"address": address, "data_list": []};
            var shopId;
            var oid;
            var shopNum;
            // TODO default address
            data.address = $("#default_address").text();
            //遍历表格
            $('.cart_content table tr.table_content').each(function () {
                //获取选中项
                var isCheck = $(this).children("td.input_checkbox").children("input").is(":checked");
                if (isCheck) {
                    shopId = $(this).children("td.input_checkbox").children("input").val();
                    shopNum = $(this).children("td.count").children(".number").text();
                    oid = $(this).children(".delete_img").children('.deleteShopCar').attr("value");
                    data.data_list.push({"shopId": shopId, "shopNum": shopNum, "oid": oid});
                }
            });
            var datas = JSON.stringify(data);
            layer.confirm('确认提交?', {btn: ['确定', '取消'], icon: 3, title: '提示'}, function () {
                //提交ajax
                $.ajax({
                    url: '/insert_order.do',
                    contentType: ' Application/json',
                    dataType: 'JSON',
                    data: datas,
                    type: 'POST',
                    success: function (data) {
                        layui.layer.msg('提交订单成功', {icon: 1});
                        console.log(data);
                        window.location.href = "/getMyOrders.do";
                    },
                    error: function () {
                        layui.layer.msg('提交订单失败', {icon: 2});
                    }
                });
            });
        });*/

    $('.pay_button').click(function () {
        console.log("总金额" + sumNum);
        if (sumNum == 0.00) {
            layui.layer.msg('购物车为空！', {icon: 2});
        } else {
            layer.confirm('确认提交?', {btn: ['确定', '取消'], icon: 3, title: '提示'}, function () {
                var obj = document.getElementsByName("checkbox");
                var s = '';//如果这样定义var s;变量s中会默认被赋个null值/settlement.do
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中
                        s += obj[i].value + ',';   //如果选中，将value添加到变量s中
                }
                var id = $(this).attr('value');
                var sid = $(this).parent().siblings(".show_img").children().attr("value");
                console.log("id===" + s.toString());
                // TODO default address
                var address = $("#default_address").text();
                console.log(address)
                var param = {};
                param.ids = s;
                param.sum = sumNum;
                param.readdress = address;
                $.ajax({
                    url: '/settlement.do?ids=' + s + "&sum=" + sumNum,
                    dataType: 'JSON',
                    contentType: "application/json;charset=UTF-8",
                    type: 'post',
                    data: param,
                    success: function (data) {
                        var result = data.result;
                        console.log(JSON.stringify(data))
                        if (result === 3) {
                            layui.layer.msg('未登录请先登录', {icon: 2});
                            window.setTimeout(function () {
                                window.location.herf='/';
                            },2200)
                        } else if (result === 2) {
                            layui.layer.msg('该商品库存不足请联系卖家', {icon: 2});
                            window.setTimeout(function () {
                                window.location.herf='/shopping_cart.do';
                            },2200)

                        }   else {
                            window.location.href = '/purchase_shop.do';
                        }
                    },
                    error: function (e) {
                        layui.layer.msg('提交失败', {icon: 2});
                        window.location.reload();
                        console.log(JSON.stringify(e))
                    }
                });
            });
        }
    });
});