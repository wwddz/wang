$(function () {
    calSum();

    // 计算总金额
    function calSum() {
        var sum = 0;
        $('.cart_content').each(function () {


            // console.log($(this));
            let arr_tr = $(this).find(".per_sum").children("span");
            let sum = 0;
            $.each(arr_tr, function (index, domEle) {
                console.log("index: domEle:", index, domEle.innerText);
                sum += parseFloat(domEle.innerText);
            });
            sum = returnFloat(sum);   //保留两位小数
            console.log("sum:", sum);
            // //给赋值
            $(this).find('.end_pay').children(".all_sum").children("span").text(sum);

        })


    }

//            保留两位小数
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


    //删除按钮
    $('#delete_order_btn').click(function () {
        var layer = layui.layer;
        var order_id = $(this).children("img").attr("value");
        var list = [];
        list.push(order_id);
        layer.confirm('确认删除?', {btn: ['确定', '取消'],icon: 3, title:'提示'},function () {
            $.ajax({
                url: '/deleteOrders.do',
                dataType: 'json',
                type: 'post',
                traditional: true,
                data: {
                    list: list
                },
                success: function (res) {
                    console.log(res);
                    window.location.href = "/getMyOrders.do";
                }
            });
        });
    });

});