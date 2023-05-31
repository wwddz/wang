$(function () {

});

function delivery(id) {
    console.log(JSON.stringify(id));
    layer.confirm('确定收货?', {btn: ['确定', '取消'], icon: 3, title: '提示'}, function () {
        var param = {}
        param.id = id;
        $.ajax({
            url: 'requireConfirm.do?id=' + id,
            dataType: 'JSON',
            type: 'get',
            contentType: "application/json;charset=UTF-8",
            data: {id: id},
            success: function (data) {
                var result = data.result;
                console.log(JSON.stringify(result))
                window.location.reload();
            }
        });
    });
}
