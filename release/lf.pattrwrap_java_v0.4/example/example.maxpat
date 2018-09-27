{
	"patcher" : 	{
		"fileversion" : 1,
		"rect" : [ 25.0, 69.0, 1027.0, 452.0 ],
		"bglocked" : 0,
		"defrect" : [ 25.0, 69.0, 1027.0, 452.0 ],
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
					"id" : "obj-11",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 253.0, 415.0, 100.0, 20.0 ],
					"text" : "print"
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
					"patching_rect" : [ 225.0, 302.0, 50.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "A" ],
					"id" : "obj-10",
					"maxclass" : "bpatcher",
					"name" : "abs_ex.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 330.0, 51.0, 212.0, 226.0 ],
					"varname" : "A[2]"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "C" ],
					"id" : "obj-9",
					"maxclass" : "bpatcher",
					"name" : "abs_ex.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 793.0, 51.0, 212.0, 226.0 ],
					"varname" : "C"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "B" ],
					"id" : "obj-8",
					"maxclass" : "bpatcher",
					"name" : "abs_ex.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 568.0, 51.0, 212.0, 226.0 ],
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
					"patching_rect" : [ 130.0, 328.0, 20.0, 20.0 ]
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
					"patching_rect" : [ 130.0, 355.0, 64.0, 18.0 ],
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
					"patching_rect" : [ 190.0, 309.0, 19.0, 20.0 ],
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
					"items" : [ "(example.json)", ",", 1, "-", "A1_A2_B1_C1", ",", 2, "-", "A3_A3_B2_C2" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 304.0, 324.0, 137.0, 20.0 ],
					"types" : [  ]
				}

			}
, 			{
				"box" : 				{
					"autorestore" : "example.json",
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-32",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 206.0, 383.0, 204.0, 20.0 ],
					"saved_object_attributes" : 					{
						"parameter_enable" : 0,
						"client_rect" : [ 4, 44, 358, 172 ],
						"paraminitmode" : 0,
						"storage_rect" : [ 365, 44, 816, 172 ]
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
					"patching_rect" : [ 206.0, 350.0, 166.0, 20.0 ],
					"text" : "mxj lf.pattrwrap @autowrite 1"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "A" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "abs_ex.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 77.0, 51.0, 212.0, 226.0 ],
					"varname" : "A[1]"
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 0 ],
					"hidden" : 0,
					"midpoints" : [ 199.5, 345.0, 215.5, 345.0 ],
					"source" : [ "obj-23", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 3 ],
					"hidden" : 0,
					"midpoints" : [ 372.5, 345.0, 362.5, 345.0 ],
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
					"destination" : [ "obj-11", 0 ],
					"hidden" : 0,
					"midpoints" : [  ],
					"source" : [ "obj-32", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-34", 1 ],
					"hidden" : 0,
					"midpoints" : [ 215.5, 412.0, 240.0, 412.0, 240.0, 340.0, 264.5, 340.0 ],
					"source" : [ "obj-32", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-25", 0 ],
					"hidden" : 0,
					"midpoints" : [ 289.0, 379.0, 301.25, 379.0, 301.25, 314.0, 313.5, 314.0 ],
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
 ]
	}

}
