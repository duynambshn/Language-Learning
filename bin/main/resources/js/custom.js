/**
 * 
 */

$(function(){
  //なにかしらの処理
	$("#new-dict").hide();
	$('#new-on-off').on('change', function(){
		if($("#new-on-off").prop('checked') == true){
//			alert("checked");
			$("#new-dict").show();
			$("#exist-dict").hide();
			$("#on-off").text("on");
		}else{
//			alert("unchecked..");
			$("#new-dict").hide();
			$("#exist-dict").show();
			$("#on-off").text("off");
		}
	});
	
	$(document).ready(function() {
		/*Data Table Show/Hide*/
	    $('#tbl-contents').DataTable();
	    $('#tbl-contents_length').hide();
	    $('#tbl-contents_filter').hide();
	    $('#no-sorting').removeClass("sorting");
	    $('.tbl-header').on('click', function(){
			if($('#no-sorting').hasClass("sorting")){
				$('#no-sorting').removeClass("sorting");
			}
		});
	    
	    /*formReg Show/Hide*/
	    var lastNumber = 0;
	    $('#btn_plus').on('click', function(){
	    	var inputNumber = $('#txt_number').val();
	    	var startNumber = (lastNumber == 0)? 0: lastNumber;
	    	var finishNumber = (lastNumber == 0)? inputNumber : (+lastNumber + +inputNumber);
	    	if(inputNumber){
	    		for(var i=startNumber; i< finishNumber; i++){
		    		$('.static_reg').html($('.static_reg').html()
		    				+'<div class="topic_border">'
		        				+'<label class="lbl_topic col-12 control-label font-weight-bold">辞典'+(i+2)+'</label>'
		            		+'</div>'
		            		+'<div class="ml-2 form-group row">'
		    	            	+'<label class="col-3 control-label">暗記語文（English）</label>'
		    	            	+'<input type="text" name="txtEng'+(i+2)+'" class="col-8 form-control"/>'
		    				+'</div>'
		    				+'<div class="ml-2 form-group row">'
		    	            	+'<label class="col-3 control-label">翻訳語文（Japanese）</label>'
		    	            	+'<input type="text" name="txtJpn'+(i+2)+'" class="col-8 form-control"/>'
		    				+'</div>'
		    				+'<div class="ml-2 form-group row">'
								+'<label class="col-3 control-label">録音</label>'
								+'<input type="button" name="btnRecord'+(i+2)+'" value="録音" class="col-2 form-control"/>'
								+'<input type="button" name="btnReRecord'+(i+2)+'" value="再録音" class="ml-2 col-2 form-control"/>'
								+'<input type="button" name="btnPlay'+(i+2)+'" value="再生" class="ml-2 col-2 form-control"/>'
							+'</div>'
		    			);
		    	}
	    		lastNumber = i;
//	    		alert(lastNumber);
	    	}
//	    	else{
//	    		$('.static_btn').html($('.static_btn').html()
//	    				+'<label class="control-label text-warning ml-2">追加数の番号を入れて下さい！</label>'
//	    		);
//	    	}
	    });
	});
});