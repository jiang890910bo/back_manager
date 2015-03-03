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
	$('.dialogs-delegate').delegate('.dialogx','click', function() {
		var el=$(this);
		var trigger=el.attr("data-toggle");
		$showdialogx(el);
	});
	removedialogx = function(container){
		$(".dialogx-win",container).remove();
		$(".dialogx-mask",container).remove();
	}
	$showdialogx=function(el){
		var trigger=el.attr("data-toggle");
		var getid=el.attr("data-target");
		var url=el.attr("data-url");
		var mask=el.attr("data-mask");
		var width=el.attr("data-width");
		var height=el.attr("data-height");
		var title=el.attr("data-title");		
		if(width==null||typeof width == 'undefined'){width="80%";}	
		if(width==null||typeof height == 'undefined'){height="500px";}	
		if(title==null||typeof title == 'undefined'){title="对话框标题";}	
		if(url==null||typeof url == 'undefined'){alert('该方法仅支持ifram调用');return;}
		var masklayout=$('<div class="dialog-mask dialogx-mask"></div>');
		if (mask=="1"){			
			$("body").append(masklayout);
		}
		
		var detail='';
		detail+='<div class="dialog-win dialogx-win" style="position:fixed;width:'+width+';z-index:11;">';
			detail+='<div id="mydialog">';
			detail+='<div class="dialog">';
				detail+='<div class="dialog-head">';
				detail+='<span class="close rotate-hover"></span>';
				detail+='<strong>'+title+'</strong>';
				detail+='</div>';
				detail+='<div>';
				detail+='<iframe name="mainFrame" id="mainFrame" src="'+url+'" frameborder="false" scrolling="auto" style="overflow-x:hidden;border:none;" width="100%" height="'+ height +'" allowtransparency="true"></iframe>';
				detail+='</div>';
			detail+='</div>';
			detail+='</div>';
		detail+='</div>';
		var $win=$(detail);
		$win.find(".dialog").addClass("open");
		$("body").append($win);
		
		var x=parseInt($(window).width()-$win.outerWidth())/2;		
		var y=parseInt($(window).height()-$win.outerHeight())/2;
		if(y<0) y=0;
		$win.css("left",x);
		$win.css("top",y);
		$win.find(".dialog-close,.close").bind('click',function(){
				$win.remove();
				$('.dialogx-mask').remove();
		})
//		win.find(".dialog-close,.close").each(function(){
//
//			console.info('==========win==========');
//			console.info(win);
//			$(this,win).click(function(){
//				console.info('==========win==========');
//				console.info(win);
//				win.remove();
//				$('.dialog-mask').remove();
//			});
//		});
		masklayout.click(function(){
			$win.remove();
			$(this).remove();
		});

	};

});
