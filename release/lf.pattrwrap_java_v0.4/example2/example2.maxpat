{
	"patcher" : 	{
		"fileversion" : 1,
		"rect" : [ 25.0, 69.0, 1043.0, 479.0 ],
		"bglocked" : 0,
		"defrect" : [ 25.0, 69.0, 1043.0, 479.0 ],
		"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
		"openinpresentation" : 0,
		"default_fontsize" : 12.0,
		"default_fontface" : 0,
		"default_fontname" : "Arial",
		"gridonopen" : 0,
		"gridsize" : [ 15.0, 15.0 ],
		"gridsnaponopen" : 0,
		"toolbarvisible" : 1,
		"boxanimatetime" : 200,
		"imprint" : 0,
		"enablehscroll" : 1,
		"enablevscroll" : 1,
		"devicewidth" : 0.0,
		"boxes" : [ 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-13",
					"linecount" : 4,
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 101.0, 368.0, 242.0, 62.0 ],
					"text" : " les sous-pattrstorage sont exclus de ce pattrstorage et les menus de ce patch ont un scripting name et \"pattr store symbol\" ON"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-15",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 4,
					"outlettype" : [ "", "", "", "" ],
					"patching_rect" : [ 385.0, 386.0, 59.5, 20.0 ],
					"restore" : 					{
						"pst1" : [ "A1" ],
						"pst2" : [ "A1" ],
						"pst3" : [ "test" ],
						"pst4" : [ "test3" ]
					}
,
					"text" : "autopattr",
					"varname" : "u653000440"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-12",
					"items" : [ "(C_pst.json)", ",", "test3", ",", "test4" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 789.0, 284.0, 100.0, 20.0 ],
					"pattrmode" : 1,
					"types" : [  ],
					"varname" : "pst4"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-11",
					"items" : [ "(B_pst.json)", ",", "test", ",", "test2" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 552.0, 284.0, 100.0, 20.0 ],
					"pattrmode" : 1,
					"types" : [  ],
					"varname" : "pst3"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-4",
					"items" : [ "(A_pst.json)", ",", "A1", ",", "A2", ",", "A3" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 315.0, 284.0, 100.0, 20.0 ],
					"pattrmode" : 1,
					"types" : [  ],
					"varname" : "pst2"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-3",
					"items" : [ "(A_pst.json)", ",", "A1", ",", "A2", ",", "A3" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 77.0, 284.0, 100.0, 20.0 ],
					"pattrmode" : 1,
					"types" : [  ],
					"varname" : "pst1"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-6",
					"maxclass" : "number",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "int", "bang" ],
					"patching_rect" : [ 774.0, 333.0, 50.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "A" ],
					"border" : 1,
					"id" : "obj-10",
					"maxclass" : "bpatcher",
					"name" : "abs_ex2.maxpat",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 314.666656, 51.0, 212.0, 226.0 ],
					"varname" : "A[2]"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "C" ],
					"border" : 1,
					"id" : "obj-9",
					"maxclass" : "bpatcher",
					"name" : "abs_ex2.maxpat",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 788.666687, 51.0, 212.0, 226.0 ],
					"varname" : "C"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "B" ],
					"border" : 1,
					"id" : "obj-8",
					"maxclass" : "bpatcher",
					"name" : "abs_ex2.maxpat",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 551.666687, 51.0, 212.0, 226.0 ],
					"varname" : "B"
				}

			}
, 			{
				"box" : 				{
					"id" : "obj-7",
					"maxclass" : "toggle",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "int" ],
					"patching_rect" : [ 679.0, 359.0, 20.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-5",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 679.0, 386.0, 64.0, 18.0 ],
					"text" : "greedy $1"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"framecolor" : [ 1.0, 0.333333, 0.333333, 1.0 ],
					"id" : "obj-23",
					"items" : [ "preset", "new", ",", "preset", "update", ",", "preset", "rename", ",", "preset", "delete", ",", "preset", "up", ",", "preset", "down", ",", "()", ",", "library", "open", ",", "library", "reopen", ",", "library", "save", ",", "library", "saveas", ",", "library", "clear", ",", "()", ",", "temporary", "store", ",", "temporary", "recall", ",", "()", ",", "clientwindow", ",", "storagewindow" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 739.0, 340.0, 19.0, 20.0 ],
					"rounded" : 20,
					"textcolor" : [ 0.14902, 0.14902, 0.14902, 0.0 ],
					"types" : [  ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-25",
					"items" : [ "(example.json)", ",", 1, "-", "test1", ",", 2, "-", "test2" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 853.0, 355.0, 137.0, 20.0 ],
					"types" : [  ]
				}

			}
, 			{
				"box" : 				{
					"active" : 					{
						"A[1]" : 0,
						"A[1]::A_pst" : 0,
						"B" : 0,
						"B::B_pst" : 0,
						"C" : 0,
						"C::C_pst" : 0,
						"A[2]" : 0,
						"A[2]::A_pst" : 0
					}
,
					"autorestore" : "example.json",
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-32",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 755.0, 414.0, 204.0, 20.0 ],
					"saved_object_attributes" : 					{
						"paraminitmode" : 0,
						"storage_rect" : [ 365, 44, 816, 172 ],
						"parameter_enable" : 0,
						"client_rect" : [ 4, 44, 397, 313 ]
					}
,
					"text" : "pattrstorage example @savemode 2",
					"varname" : "example"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-34",
					"maxclass" : "newobj",
					"numinlets" : 4,
					"numoutlets" : 3,
					"outlettype" : [ "", "", "" ],
					"patching_rect" : [ 755.0, 381.0, 166.0, 20.0 ],
					"text" : "mxj lf.pattrwrap @autowrite 1"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "A" ],
					"border" : 1,
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "abs_ex2.maxpat",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 77.0, 51.0, 212.0, 226.0 ],
					"varname" : "A[1]"
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-3", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-1", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-4", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-10", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-8", 0 ],
					"hidden" : 0,
					"midpoints" : [ 602.0, 313.0, 539.583374, 313.0, 539.583374, 41.0, 561.166687, 41.0 ],
					"source" : [ "obj-11", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-9", 0 ],
					"hidden" : 0,
					"midpoints" : [ 839.0, 313.0, 774.583374, 313.0, 774.583374, 41.0, 798.166687, 41.0 ],
					"source" : [ "obj-12", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 0 ],
					"hidden" : 0,
					"midpoints" : [ 748.5, 376.0, 764.5, 376.0 ],
					"source" : [ "obj-23", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 3 ],
					"hidden" : 0,
					"midpoints" : [ 921.5, 376.0, 911.5, 376.0 ],
					"source" : [ "obj-25", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 2 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-25", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"hidden" : 0,
					"midpoints" : [ 127.0, 313.0, 64.75, 313.0, 64.75, 41.0, 86.5, 41.0 ],
					"source" : [ "obj-3", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 1 ],
					"hidden" : 0,
					"midpoints" : [ 764.5, 443.0, 789.0, 443.0, 789.0, 371.0, 813.5, 371.0 ],
					"source" : [ "obj-32", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-25", 0 ],
					"hidden" : 0,
					"midpoints" : [ 838.0, 410.0, 850.25, 410.0, 850.25, 345.0, 862.5, 345.0 ],
					"source" : [ "obj-34", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-32", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-34", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-10", 0 ],
					"hidden" : 0,
					"midpoints" : [ 365.0, 311.0, 303.583313, 311.0, 303.583313, 41.0, 324.166656, 41.0 ],
					"source" : [ "obj-4", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-32", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-5", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-6", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-5", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-7", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-11", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-8", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-9", 0 ]
				}

			}
 ]
	}

}
