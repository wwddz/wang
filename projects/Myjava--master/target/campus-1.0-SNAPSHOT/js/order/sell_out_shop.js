$(function () {

});

function delivery(id) {
    layer.confirm('确定发货?', {btn: ['确定', '取消'], icon: 3, title: '提示'}, function () {
        var sid = $(this).parent().siblings(".show_img").children().attr("value");
        // alert(sid);
        $.ajax({
            url: 'requireConfirmGoods.do?id=' + id,
            dataType: 'JSON',
            type: 'get',
            data: {id: id},
            success: function (data) {
                //var result = data.result;
                window.location.reload();
            }
        });
    });
}