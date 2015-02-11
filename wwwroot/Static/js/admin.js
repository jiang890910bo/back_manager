//页面加载完毕后执行
$(function(){
	//一级菜单激活
	$(".admin-nav>li>a").click(function(event) {		
		$(this).parent().addClass('active');
		$(this).parent().siblings().removeClass('active');
		childrenBox = $('.admin-nav-ii',$(this).parent());
		children = $('a',childrenBox[0]);
		if (children[0]) children[0].click();
	});
	//二级菜单激活
	$(".admin-nav-ii li").click(function(){
		$(this).addClass('active');
		$(this).siblings().removeClass('active');
		Link1 = $("a:first",$(this).parent().parent());
		Link2 = $("a",this);
		$(".admin-bread .bread").empty();
		$('<li>').append(Link1.clone()).appendTo(".admin-bread .bread");
		$('<li>').append(Link2.clone()).appendTo(".admin-bread .bread");
	});
	$(".admin_left_btn").click(function(){
			$('body').toggleClass("fullscreen");
			$(this).toggleClass("admin_left_btn_open");
			$(this).toggleClass("icon-caret-right");
	});

});
