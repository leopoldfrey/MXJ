{
	"patcher" : 	{
		"fileversion" : 1,
		"rect" : [ 178.0, 101.0, 1021.0, 698.0 ],
		"bglocked" : 0,
		"defrect" : [ 178.0, 101.0, 1021.0, 698.0 ],
		"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
		"openinpresentation" : 0,
		"default_fontsize" : 12.0,
		"default_fontface" : 0,
		"default_fontname" : "Arial",
		"gridonopen" : 0,
		"gridsize" : [ 10.0, 10.0 ],
		"gridsnaponopen" : 0,
		"toolbarvisible" : 1,
		"boxanimatetime" : 0,
		"imprint" : 0,
		"metadata" : [  ],
		"boxes" : [ 			{
				"box" : 				{
					"maxclass" : "toggle",
					"patching_rect" : [ 625.0, 368.0, 20.0, 20.0 ],
					"id" : "obj-122",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "int" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "toggle",
					"patching_rect" : [ 613.0, 291.0, 20.0, 20.0 ],
					"id" : "obj-120",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "int" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "metro 16n",
					"fontname" : "Arial",
					"patching_rect" : [ 613.0, 317.0, 65.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-118",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "gate",
					"fontname" : "Arial",
					"patching_rect" : [ 625.0, 393.0, 34.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-113",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "transport",
					"fontname" : "Arial",
					"patching_rect" : [ 613.0, 343.0, 127.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-110",
					"numinlets" : 2,
					"numoutlets" : 9,
					"outlettype" : [ "int", "int", "float", "float", "float", "", "int", "float", "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "button",
					"patching_rect" : [ 625.0, 419.0, 20.0, 20.0 ],
					"id" : "obj-109",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "textbutton",
					"fontname" : "Arial",
					"presentation_rect" : [ 318.0, 426.0, 57.0, 20.0 ],
					"patching_rect" : [ 318.0, 426.0, 57.0, 20.0 ],
					"texton" : "Bypass",
					"fontsize" : 12.0,
					"presentation" : 1,
					"mode" : 1,
					"id" : "obj-107",
					"numinlets" : 1,
					"rounded" : 5.0,
					"text" : "Bypass",
					"numoutlets" : 3,
					"outlettype" : [ "", "", "int" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "textbutton",
					"fontname" : "Arial",
					"presentation_rect" : [ 318.0, 402.0, 57.0, 20.0 ],
					"patching_rect" : [ 318.0, 402.0, 57.0, 20.0 ],
					"texton" : "Freeze",
					"fontsize" : 12.0,
					"presentation" : 1,
					"mode" : 1,
					"id" : "obj-106",
					"numinlets" : 1,
					"rounded" : 5.0,
					"text" : "Freeze",
					"numoutlets" : 3,
					"outlettype" : [ "", "", "int" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "--------------------------- reverb -----------------------------",
					"fontname" : "Arial",
					"presentation_rect" : [ 106.0, 376.0, 275.0, 20.0 ],
					"patching_rect" : [ 106.0, 376.0, 275.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-105",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "--------------- amp_mod ----------------",
					"fontname" : "Arial",
					"presentation_rect" : [ 122.0, 261.0, 194.0, 20.0 ],
					"patching_rect" : [ 122.0, 261.0, 194.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-104",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "spread",
					"fontname" : "Arial",
					"presentation_rect" : [ 724.0, 43.0, 47.0, 20.0 ],
					"patching_rect" : [ 724.0, 43.0, 47.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-102",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 724.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 724.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-103",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 275.0, 408.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 275.0, 408.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-84",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 232.0, 408.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 232.0, 408.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-46",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 189.0, 408.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 189.0, 408.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-47",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 146.0, 408.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 146.0, 408.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-43",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 103.0, 408.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 103.0, 408.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-27",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s autopan_freq",
					"fontname" : "Arial",
					"patching_rect" : [ 749.0, 593.5, 91.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-1",
					"numinlets" : 1,
					"numoutlets" : 0,
					"color" : [ 1.0, 0.901961, 0.0, 0.8 ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "* 100.",
					"fontname" : "Arial",
					"patching_rect" : [ 749.0, 567.5, 42.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-10",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "!/ 1000.",
					"fontname" : "Arial",
					"patching_rect" : [ 749.0, 543.0, 51.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-13",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "umenu",
					"fontname" : "Arial",
					"items" : [ "1nd", ",", "1n", ",", "1nt", ",", "2nd", ",", "2n", ",", "2nt", ",", "4nd", ",", "4n", ",", "4nt", ",", "8nd", ",", "8n", ",", "8nt", ",", "16nd", ",", "16n", ",", "16nt", ",", "32nd", ",", "32n", ",", "32nt", ",", "64nd", ",", "64n", ",", "128n" ],
					"labelclick" : 1,
					"patching_rect" : [ 723.0, 448.0, 71.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-25",
					"numinlets" : 1,
					"types" : [  ],
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 788.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-83",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "gain~",
					"presentation_rect" : [ 65.0, 490.0, 22.0, 122.0 ],
					"patching_rect" : [ 65.0, 490.0, 22.0, 122.0 ],
					"presentation" : 1,
					"id" : "obj-65",
					"numinlets" : 2,
					"orientation" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "signal", "int" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "bargraf~",
					"fontname" : "Arial",
					"presentation_rect" : [ 86.0, 490.0, 24.0, 122.0 ],
					"patching_rect" : [ 86.0, 490.0, 24.0, 122.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-70",
					"numinlets" : 2,
					"border" : 0,
					"numoutlets" : 3,
					"frame" : 0,
					"outlettype" : [ "float", "float", "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "p reverb",
					"fontname" : "Arial",
					"patching_rect" : [ 17.0, 454.0, 363.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-64",
					"numinlets" : 9,
					"numoutlets" : 2,
					"outlettype" : [ "signal", "signal" ],
					"patcher" : 					{
						"fileversion" : 1,
						"rect" : [ 25.0, 69.0, 642.0, 198.0 ],
						"bglocked" : 0,
						"defrect" : [ 25.0, 69.0, 642.0, 198.0 ],
						"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
						"openinpresentation" : 0,
						"default_fontsize" : 12.0,
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"gridonopen" : 0,
						"gridsize" : [ 10.0, 10.0 ],
						"gridsnaponopen" : 0,
						"toolbarvisible" : 1,
						"boxanimatetime" : 200,
						"imprint" : 0,
						"metadata" : [  ],
						"boxes" : [ 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 558.0, 21.0, 25.0, 25.0 ],
									"id" : "obj-17",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 482.0, 21.0, 25.0, 25.0 ],
									"id" : "obj-16",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 406.0, 21.0, 25.0, 25.0 ],
									"id" : "obj-15",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 330.0, 21.0, 25.0, 25.0 ],
									"id" : "obj-14",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 254.0, 21.0, 25.0, 25.0 ],
									"id" : "obj-12",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 178.0, 21.0, 25.0, 25.0 ],
									"id" : "obj-11",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 102.0, 21.0, 25.0, 25.0 ],
									"id" : "obj-9",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"minimum" : 0.0,
									"fontname" : "Arial",
									"triscale" : 0.9,
									"maximum" : 1.0,
									"hbgcolor" : [ 0.0, 0.0, 0.0, 1.0 ],
									"htextcolor" : [ 0.870588, 0.870588, 0.870588, 1.0 ],
									"patching_rect" : [ 254.0, 54.0, 40.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-7",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "width $1",
									"fontname" : "Arial",
									"patching_rect" : [ 254.0, 83.0, 55.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-8",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 65.0, 153.0, 25.0, 25.0 ],
									"id" : "obj-5",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 22.0, 153.0, 25.0, 25.0 ],
									"id" : "obj-4",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 65.0, 71.0, 25.0, 25.0 ],
									"id" : "obj-3",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 22.0, 71.0, 25.0, 25.0 ],
									"id" : "obj-2",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"patching_rect" : [ 482.0, 54.0, 20.0, 20.0 ],
									"id" : "obj-1",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"patching_rect" : [ 558.0, 54.0, 20.0, 20.0 ],
									"id" : "obj-10",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "bypass $1",
									"fontname" : "Arial",
									"patching_rect" : [ 558.0, 83.0, 65.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-13",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"minimum" : 0.0,
									"fontname" : "Arial",
									"triscale" : 0.9,
									"maximum" : 1.0,
									"hbgcolor" : [ 0.0, 0.0, 0.0, 1.0 ],
									"htextcolor" : [ 0.870588, 0.870588, 0.870588, 1.0 ],
									"patching_rect" : [ 178.0, 54.0, 40.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-24",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"minimum" : 0.0,
									"fontname" : "Arial",
									"triscale" : 0.9,
									"maximum" : 1.0,
									"hbgcolor" : [ 0.0, 0.0, 0.0, 1.0 ],
									"htextcolor" : [ 0.870588, 0.870588, 0.870588, 1.0 ],
									"patching_rect" : [ 102.0, 54.0, 40.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-25",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"minimum" : 0.0,
									"fontname" : "Arial",
									"triscale" : 0.9,
									"maximum" : 1.0,
									"hbgcolor" : [ 0.0, 0.0, 0.0, 1.0 ],
									"htextcolor" : [ 0.870588, 0.870588, 0.870588, 1.0 ],
									"patching_rect" : [ 330.0, 54.0, 40.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-26",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "freeze $1",
									"fontname" : "Arial",
									"patching_rect" : [ 482.0, 83.0, 61.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-27",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "dry $1",
									"fontname" : "Arial",
									"patching_rect" : [ 406.0, 83.0, 44.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-33",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "wet $1",
									"fontname" : "Arial",
									"patching_rect" : [ 330.0, 83.0, 46.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-34",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "damping $1",
									"fontname" : "Arial",
									"patching_rect" : [ 178.0, 83.0, 73.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-42",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "roomsize $1",
									"fontname" : "Arial",
									"patching_rect" : [ 102.0, 83.0, 76.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-43",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "freeverb~",
									"fontname" : "Arial",
									"patching_rect" : [ 22.0, 126.0, 62.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-46",
									"numinlets" : 2,
									"numoutlets" : 2,
									"color" : [ 0.870588, 0.870588, 0.870588, 1.0 ],
									"outlettype" : [ "signal", "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"minimum" : 0.0,
									"fontname" : "Arial",
									"triscale" : 0.9,
									"maximum" : 1.0,
									"hbgcolor" : [ 0.0, 0.0, 0.0, 1.0 ],
									"htextcolor" : [ 0.870588, 0.870588, 0.870588, 1.0 ],
									"patching_rect" : [ 406.0, 54.0, 40.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-47",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
 ],
						"lines" : [ 							{
								"patchline" : 								{
									"source" : [ "obj-17", 0 ],
									"destination" : [ "obj-10", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-16", 0 ],
									"destination" : [ "obj-1", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-15", 0 ],
									"destination" : [ "obj-47", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-14", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-12", 0 ],
									"destination" : [ "obj-7", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-11", 0 ],
									"destination" : [ "obj-24", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-9", 0 ],
									"destination" : [ "obj-25", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-8", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [ 263.5, 113.0, 31.5, 113.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-7", 0 ],
									"destination" : [ "obj-8", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-13", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [ 567.5, 113.0, 31.5, 113.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-27", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [ 491.5, 113.0, 31.5, 113.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-33", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [ 415.5, 113.0, 31.5, 113.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-34", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [ 339.5, 113.0, 31.5, 113.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-42", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [ 187.5, 113.0, 31.5, 113.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-43", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [ 111.5, 113.0, 31.5, 113.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-26", 0 ],
									"destination" : [ "obj-34", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-1", 0 ],
									"destination" : [ "obj-27", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-47", 0 ],
									"destination" : [ "obj-33", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-24", 0 ],
									"destination" : [ "obj-42", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-25", 0 ],
									"destination" : [ "obj-43", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-10", 0 ],
									"destination" : [ "obj-13", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-2", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-3", 0 ],
									"destination" : [ "obj-46", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-46", 0 ],
									"destination" : [ "obj-4", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-46", 1 ],
									"destination" : [ "obj-5", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
 ]
					}
,
					"saved_object_attributes" : 					{
						"fontname" : "Arial",
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"fontface" : 0,
						"fontsize" : 12.0,
						"default_fontsize" : 12.0,
						"globalpatchername" : ""
					}

				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r autopan_freq",
					"fontname" : "Arial",
					"hidden" : 1,
					"patching_rect" : [ 788.0, 26.0, 89.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-53",
					"numinlets" : 0,
					"numoutlets" : 1,
					"color" : [ 1.0, 0.901961, 0.0, 0.8 ],
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 788.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 788.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-54",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 2500.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "freq",
					"fontname" : "Arial",
					"presentation_rect" : [ 788.0, 44.0, 31.0, 20.0 ],
					"patching_rect" : [ 788.0, 44.0, 31.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-57",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "stability",
					"fontname" : "Arial",
					"presentation_rect" : [ 853.0, 44.0, 51.0, 20.0 ],
					"patching_rect" : [ 853.0, 44.0, 51.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-60",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "depth",
					"fontname" : "Arial",
					"presentation_rect" : [ 917.0, 44.0, 41.0, 20.0 ],
					"patching_rect" : [ 917.0, 44.0, 41.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-61",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 853.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 853.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-62",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 50.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 917.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 917.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-63",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 660.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-55",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "portamento",
					"fontname" : "Arial",
					"presentation_rect" : [ 651.0, 44.0, 71.0, 20.0 ],
					"patching_rect" : [ 651.0, 44.0, 71.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-48",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 660.0, 61.0, 40.0, 40.0 ],
					"patching_rect" : [ 660.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-52",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 2000.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "p overdrive",
					"fontname" : "Arial",
					"patching_rect" : [ 17.0, 247.0, 80.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-44",
					"numinlets" : 3,
					"numoutlets" : 2,
					"outlettype" : [ "signal", "signal" ],
					"patcher" : 					{
						"fileversion" : 1,
						"rect" : [ 760.0, 144.0, 399.0, 264.0 ],
						"bglocked" : 0,
						"defrect" : [ 760.0, 144.0, 399.0, 264.0 ],
						"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
						"openinpresentation" : 0,
						"default_fontsize" : 12.0,
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"gridonopen" : 0,
						"gridsize" : [ 10.0, 10.0 ],
						"gridsnaponopen" : 0,
						"toolbarvisible" : 1,
						"boxanimatetime" : 200,
						"imprint" : 0,
						"metadata" : [  ],
						"boxes" : [ 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "*~",
									"fontname" : "Arial",
									"patching_rect" : [ 158.0, 180.0, 123.5, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-5",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "overdrive~",
									"fontname" : "Arial",
									"patching_rect" : [ 159.0, 152.0, 67.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-6",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 158.0, 20.0, 25.0, 25.0 ],
									"id" : "obj-7",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 158.0, 210.5, 25.0, 25.0 ],
									"id" : "obj-8",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "scale 1. 50. 1. 0.4",
									"fontname" : "Arial",
									"patching_rect" : [ 263.0, 61.0, 105.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-4",
									"numinlets" : 6,
									"numoutlets" : 1,
									"outlettype" : [ "float" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "$1 50",
									"fontname" : "Arial",
									"patching_rect" : [ 263.0, 89.0, 41.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-2",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "line~ 1.",
									"fontname" : "Arial",
									"patching_rect" : [ 263.0, 113.0, 50.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-3",
									"numinlets" : 2,
									"numoutlets" : 2,
									"outlettype" : [ "signal", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "*~",
									"fontname" : "Arial",
									"patching_rect" : [ 23.0, 180.0, 123.5, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-1",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "$1 50",
									"fontname" : "Arial",
									"patching_rect" : [ 207.0, 65.0, 41.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-33",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "line~ 1",
									"fontname" : "Arial",
									"patching_rect" : [ 207.0, 91.0, 46.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-26",
									"numinlets" : 2,
									"numoutlets" : 2,
									"outlettype" : [ "signal", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "overdrive~",
									"fontname" : "Arial",
									"patching_rect" : [ 23.0, 152.0, 67.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-28",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 23.0, 20.0, 25.0, 25.0 ],
									"id" : "obj-34",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 207.0, 20.0, 25.0, 25.0 ],
									"id" : "obj-42",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 23.0, 210.5, 25.0, 25.0 ],
									"id" : "obj-43",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
 ],
						"lines" : [ 							{
								"patchline" : 								{
									"source" : [ "obj-28", 0 ],
									"destination" : [ "obj-1", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-34", 0 ],
									"destination" : [ "obj-28", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-26", 0 ],
									"destination" : [ "obj-28", 1 ],
									"hidden" : 0,
									"midpoints" : [ 216.5, 134.0, 80.5, 134.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-7", 0 ],
									"destination" : [ "obj-6", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-6", 0 ],
									"destination" : [ "obj-5", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-26", 0 ],
									"destination" : [ "obj-6", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-33", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-4", 0 ],
									"destination" : [ "obj-2", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-42", 0 ],
									"destination" : [ "obj-4", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-3", 0 ],
									"destination" : [ "obj-1", 1 ],
									"hidden" : 0,
									"midpoints" : [ 272.5, 145.0, 137.0, 145.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-2", 0 ],
									"destination" : [ "obj-3", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-42", 0 ],
									"destination" : [ "obj-33", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-1", 0 ],
									"destination" : [ "obj-43", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-5", 0 ],
									"destination" : [ "obj-8", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-3", 0 ],
									"destination" : [ "obj-5", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
 ]
					}
,
					"saved_object_attributes" : 					{
						"fontname" : "Arial",
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"fontface" : 0,
						"fontsize" : 12.0,
						"default_fontsize" : 12.0,
						"globalpatchername" : ""
					}

				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 532.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-17",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 210.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-11",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 274.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-5",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "button",
					"patching_rect" : [ 40.0, 98.0, 20.0, 20.0 ],
					"id" : "obj-88",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "p init",
					"fontname" : "Arial",
					"patching_rect" : [ 64.0, 98.0, 36.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-85",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patcher" : 					{
						"fileversion" : 1,
						"rect" : [ 25.0, 69.0, 640.0, 480.0 ],
						"bglocked" : 0,
						"defrect" : [ 25.0, 69.0, 640.0, 480.0 ],
						"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
						"openinpresentation" : 0,
						"default_fontsize" : 12.0,
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"gridonopen" : 0,
						"gridsize" : [ 10.0, 10.0 ],
						"gridsnaponopen" : 0,
						"toolbarvisible" : 1,
						"boxanimatetime" : 200,
						"imprint" : 0,
						"metadata" : [  ],
						"boxes" : [ 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 291.0, 28.0, 25.0, 25.0 ],
									"id" : "obj-9",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "1",
									"fontname" : "Arial",
									"patching_rect" : [ 363.0, 126.0, 32.5, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-8",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "250",
									"fontname" : "Arial",
									"patching_rect" : [ 405.0, 121.0, 32.5, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-6",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"patching_rect" : [ 50.0, 140.0, 20.0, 20.0 ],
									"id" : "obj-48",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "metro 50",
									"fontname" : "Arial",
									"patching_rect" : [ 50.0, 169.0, 58.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-46",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "busymap 1",
									"fontname" : "Arial",
									"patching_rect" : [ 50.0, 197.0, 69.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-43",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "target 0",
									"fontname" : "Arial",
									"patching_rect" : [ 233.0, 192.0, 48.0, 18.0 ],
									"fontsize" : 11.595187,
									"id" : "obj-33",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "loadbang",
									"fontname" : "Arial",
									"patching_rect" : [ 291.0, 60.0, 56.0, 20.0 ],
									"fontsize" : 11.595187,
									"id" : "obj-34",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"fontname" : "Arial",
									"patching_rect" : [ 517.0, 167.0, 50.0, 20.0 ],
									"fontsize" : 11.595187,
									"id" : "obj-1",
									"numinlets" : 1,
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "maxsustain $1",
									"fontname" : "Arial",
									"patching_rect" : [ 517.0, 190.0, 83.0, 18.0 ],
									"fontsize" : 11.595187,
									"id" : "obj-13",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"fontname" : "Arial",
									"patching_rect" : [ 443.0, 167.0, 50.0, 20.0 ],
									"fontsize" : 11.595187,
									"id" : "obj-24",
									"numinlets" : 1,
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "retrigger $1",
									"fontname" : "Arial",
									"patching_rect" : [ 443.0, 190.0, 68.0, 18.0 ],
									"fontsize" : 11.595187,
									"id" : "obj-25",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"patching_rect" : [ 380.0, 167.0, 20.0, 20.0 ],
									"id" : "obj-26",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "legato $1",
									"fontname" : "Arial",
									"patching_rect" : [ 380.0, 190.0, 58.0, 18.0 ],
									"fontsize" : 11.595187,
									"id" : "obj-27",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "allnotesoff",
									"fontname" : "Arial",
									"patching_rect" : [ 128.0, 193.5, 63.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-10",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 137.0, 275.0, 25.0, 25.0 ],
									"id" : "obj-83",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 446.0, 275.0, 25.0, 25.0 ],
									"id" : "obj-84",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
 ],
						"lines" : [ 							{
								"patchline" : 								{
									"source" : [ "obj-9", 0 ],
									"destination" : [ "obj-34", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-34", 0 ],
									"destination" : [ "obj-6", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-34", 0 ],
									"destination" : [ "obj-8", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-8", 0 ],
									"destination" : [ "obj-48", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-8", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-6", 0 ],
									"destination" : [ "obj-24", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-1", 0 ],
									"destination" : [ "obj-13", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-24", 0 ],
									"destination" : [ "obj-25", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-26", 0 ],
									"destination" : [ "obj-27", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-34", 0 ],
									"destination" : [ "obj-33", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-46", 0 ],
									"destination" : [ "obj-43", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-48", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-43", 0 ],
									"destination" : [ "obj-83", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-10", 0 ],
									"destination" : [ "obj-83", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-33", 0 ],
									"destination" : [ "obj-83", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-27", 0 ],
									"destination" : [ "obj-84", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-25", 0 ],
									"destination" : [ "obj-84", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-13", 0 ],
									"destination" : [ "obj-84", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
 ]
					}
,
					"saved_object_attributes" : 					{
						"fontname" : "Arial",
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"fontface" : 0,
						"fontsize" : 12.0,
						"default_fontsize" : 12.0,
						"globalpatchername" : ""
					}

				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "stability",
					"fontname" : "Arial",
					"presentation_rect" : [ 468.0, 44.0, 51.0, 20.0 ],
					"patching_rect" : [ 468.0, 44.0, 51.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-81",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 467.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.0005,
					"patching_rect" : [ 467.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-82",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r vib_freq",
					"fontname" : "Arial",
					"hidden" : 1,
					"patching_rect" : [ 403.0, 25.0, 61.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-79",
					"numinlets" : 0,
					"numoutlets" : 1,
					"color" : [ 1.0, 0.901961, 0.0, 0.8 ],
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s vib_freq",
					"fontname" : "Arial",
					"patching_rect" : [ 651.0, 593.5, 63.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-76",
					"numinlets" : 1,
					"numoutlets" : 0,
					"color" : [ 1.0, 0.901961, 0.0, 0.8 ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "* 10.",
					"fontname" : "Arial",
					"patching_rect" : [ 651.0, 567.5, 35.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-75",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "overdrive",
					"fontname" : "Arial",
					"presentation_rect" : [ 77.0, 161.0, 60.0, 20.0 ],
					"patching_rect" : [ 77.0, 161.0, 60.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-74",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 78.0, 180.0, 40.0, 40.0 ],
					"mult" : 0.5,
					"patching_rect" : [ 78.0, 180.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-73",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r am_freq",
					"fontname" : "Arial",
					"hidden" : 1,
					"patching_rect" : [ 121.0, 257.0, 62.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-72",
					"numinlets" : 0,
					"numoutlets" : 1,
					"color" : [ 1.0, 0.901961, 0.0, 0.8 ],
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s am_freq",
					"fontname" : "Arial",
					"patching_rect" : [ 854.0, 593.5, 64.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-71",
					"numinlets" : 1,
					"numoutlets" : 0,
					"color" : [ 1.0, 0.901961, 0.0, 0.8 ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "* 100.",
					"fontname" : "Arial",
					"patching_rect" : [ 854.0, 567.5, 42.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-69",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 121.0, 296.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 121.0, 296.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-67",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 2500.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "freq",
					"fontname" : "Arial",
					"presentation_rect" : [ 121.0, 278.0, 31.0, 20.0 ],
					"patching_rect" : [ 121.0, 278.0, 31.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-68",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "p amp_mod",
					"fontname" : "Arial",
					"patching_rect" : [ 17.0, 341.0, 278.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-66",
					"numinlets" : 6,
					"numoutlets" : 2,
					"outlettype" : [ "signal", "signal" ],
					"patcher" : 					{
						"fileversion" : 1,
						"rect" : [ 25.0, 69.0, 244.0, 255.0 ],
						"bglocked" : 0,
						"defrect" : [ 25.0, 69.0, 244.0, 255.0 ],
						"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
						"openinpresentation" : 0,
						"default_fontsize" : 12.0,
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"gridonopen" : 0,
						"gridsize" : [ 10.0, 10.0 ],
						"gridsnaponopen" : 0,
						"toolbarvisible" : 1,
						"boxanimatetime" : 200,
						"imprint" : 0,
						"metadata" : [  ],
						"boxes" : [ 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "*~",
									"fontname" : "Arial",
									"patching_rect" : [ 44.0, 173.0, 32.5, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-3",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 44.0, 19.0, 25.0, 25.0 ],
									"id" : "obj-4",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 44.0, 201.0, 25.0, 25.0 ],
									"id" : "obj-5",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "*~",
									"fontname" : "Arial",
									"patching_rect" : [ 5.0, 173.0, 32.5, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-2",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 5.0, 19.0, 25.0, 25.0 ],
									"id" : "obj-1",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "cycle~",
									"fontname" : "Arial",
									"patching_rect" : [ 85.0, 76.0, 45.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-122",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "p freq_stab",
									"fontname" : "Arial",
									"patching_rect" : [ 85.0, 50.0, 71.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-107",
									"numinlets" : 3,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"patcher" : 									{
										"fileversion" : 1,
										"rect" : [ 25.0, 69.0, 255.0, 339.0 ],
										"bglocked" : 0,
										"defrect" : [ 25.0, 69.0, 255.0, 339.0 ],
										"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
										"openinpresentation" : 0,
										"default_fontsize" : 12.0,
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"gridonopen" : 0,
										"gridsize" : [ 10.0, 10.0 ],
										"gridsnaponopen" : 0,
										"toolbarvisible" : 1,
										"boxanimatetime" : 200,
										"imprint" : 0,
										"metadata" : [  ],
										"boxes" : [ 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 78.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-2",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "sprintf \\$1 %d",
													"fontname" : "Arial",
													"patching_rect" : [ 78.0, 72.0, 84.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-1",
													"numinlets" : 1,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "*~ 0.",
													"fontname" : "Arial",
													"patching_rect" : [ 78.0, 183.0, 112.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-85",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "*~",
													"fontname" : "Arial",
													"patching_rect" : [ 64.0, 209.0, 32.5, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-67",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "rand~ 10",
													"fontname" : "Arial",
													"patching_rect" : [ 64.0, 158.0, 58.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-69",
													"numinlets" : 1,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "+~",
													"fontname" : "Arial",
													"patching_rect" : [ 50.0, 238.0, 32.5, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-70",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "message",
													"text" : "$1 500",
													"fontname" : "Arial",
													"patching_rect" : [ 50.0, 100.0, 47.0, 18.0 ],
													"fontsize" : 12.0,
													"id" : "obj-72",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "line~",
													"fontname" : "Arial",
													"patching_rect" : [ 50.0, 124.0, 36.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-73",
													"numinlets" : 2,
													"numoutlets" : 2,
													"outlettype" : [ "signal", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 50.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-104",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "float" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 171.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-105",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "float" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "outlet",
													"patching_rect" : [ 50.0, 263.0, 25.0, 25.0 ],
													"id" : "obj-106",
													"numinlets" : 1,
													"numoutlets" : 0,
													"comment" : ""
												}

											}
 ],
										"lines" : [ 											{
												"patchline" : 												{
													"source" : [ "obj-72", 0 ],
													"destination" : [ "obj-73", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-73", 0 ],
													"destination" : [ "obj-70", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-104", 0 ],
													"destination" : [ "obj-72", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-70", 0 ],
													"destination" : [ "obj-106", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-69", 0 ],
													"destination" : [ "obj-67", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-67", 0 ],
													"destination" : [ "obj-70", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-85", 0 ],
													"destination" : [ "obj-67", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-105", 0 ],
													"destination" : [ "obj-85", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-73", 0 ],
													"destination" : [ "obj-85", 0 ],
													"hidden" : 0,
													"midpoints" : [ 59.5, 150.0, 87.5, 150.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-1", 0 ],
													"destination" : [ "obj-72", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-2", 0 ],
													"destination" : [ "obj-1", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
 ]
									}
,
									"saved_object_attributes" : 									{
										"fontname" : "Arial",
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"fontface" : 0,
										"fontsize" : 12.0,
										"default_fontsize" : 12.0,
										"globalpatchername" : ""
									}

								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "overdrive~ 1",
									"fontname" : "Arial",
									"patching_rect" : [ 85.0, 101.0, 97.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-98",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "p depth",
									"fontname" : "Arial",
									"patching_rect" : [ 85.0, 127.0, 123.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-79",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "signal" ],
									"patcher" : 									{
										"fileversion" : 1,
										"rect" : [ 25.0, 69.0, 202.0, 288.0 ],
										"bglocked" : 0,
										"defrect" : [ 25.0, 69.0, 202.0, 288.0 ],
										"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
										"openinpresentation" : 0,
										"default_fontsize" : 12.0,
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"gridonopen" : 0,
										"gridsize" : [ 10.0, 10.0 ],
										"gridsnaponopen" : 0,
										"toolbarvisible" : 1,
										"boxanimatetime" : 200,
										"imprint" : 0,
										"metadata" : [  ],
										"boxes" : [ 											{
												"box" : 												{
													"maxclass" : "message",
													"text" : "$1 10",
													"fontname" : "Arial",
													"patching_rect" : [ 101.0, 56.0, 41.0, 18.0 ],
													"fontsize" : 12.0,
													"id" : "obj-21",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "line~",
													"fontname" : "Arial",
													"patching_rect" : [ 101.0, 83.0, 36.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-19",
													"numinlets" : 2,
													"numoutlets" : 2,
													"outlettype" : [ "signal", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "!-~ 1.",
													"fontname" : "Arial",
													"patching_rect" : [ 101.0, 164.0, 38.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-17",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "/~ 2.",
													"fontname" : "Arial",
													"patching_rect" : [ 101.0, 110.0, 34.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-7",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "+~ 0.5",
													"fontname" : "Arial",
													"patching_rect" : [ 62.0, 191.0, 45.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-5",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "*~ 0.5",
													"fontname" : "Arial",
													"patching_rect" : [ 62.0, 139.0, 42.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-4",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 101.0, 24.0, 25.0, 25.0 ],
													"id" : "obj-3",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "float" ],
													"comment" : "depth"
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "outlet",
													"patching_rect" : [ 62.0, 228.0, 25.0, 25.0 ],
													"id" : "obj-2",
													"numinlets" : 1,
													"numoutlets" : 0,
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 62.0, 63.0, 25.0, 25.0 ],
													"id" : "obj-1",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "signal" ],
													"comment" : ""
												}

											}
 ],
										"lines" : [ 											{
												"patchline" : 												{
													"source" : [ "obj-4", 0 ],
													"destination" : [ "obj-5", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-5", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-1", 0 ],
													"destination" : [ "obj-4", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-17", 0 ],
													"destination" : [ "obj-5", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-7", 0 ],
													"destination" : [ "obj-17", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-7", 0 ],
													"destination" : [ "obj-4", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-21", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-21", 0 ],
													"destination" : [ "obj-19", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-19", 0 ],
													"destination" : [ "obj-7", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
 ]
									}
,
									"saved_object_attributes" : 									{
										"fontname" : "Arial",
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"fontface" : 0,
										"fontsize" : 12.0,
										"default_fontsize" : 12.0,
										"globalpatchername" : ""
									}

								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 85.0, 20.0, 25.0, 25.0 ],
									"id" : "obj-60",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 137.0, 20.0, 25.0, 25.0 ],
									"id" : "obj-62",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 189.0, 20.0, 25.0, 25.0 ],
									"id" : "obj-63",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "inlet",
									"patching_rect" : [ 163.0, 20.0, 25.0, 25.0 ],
									"id" : "obj-64",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "float" ],
									"comment" : ""
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "outlet",
									"patching_rect" : [ 5.0, 201.0, 25.0, 25.0 ],
									"id" : "obj-65",
									"numinlets" : 1,
									"numoutlets" : 0,
									"comment" : ""
								}

							}
 ],
						"lines" : [ 							{
								"patchline" : 								{
									"source" : [ "obj-79", 0 ],
									"destination" : [ "obj-2", 1 ],
									"hidden" : 0,
									"midpoints" : [ 94.5, 159.0, 28.0, 159.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-2", 0 ],
									"destination" : [ "obj-65", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-1", 0 ],
									"destination" : [ "obj-2", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-98", 0 ],
									"destination" : [ "obj-79", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-122", 0 ],
									"destination" : [ "obj-98", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-107", 0 ],
									"destination" : [ "obj-122", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-60", 0 ],
									"destination" : [ "obj-107", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-62", 0 ],
									"destination" : [ "obj-107", 2 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-63", 0 ],
									"destination" : [ "obj-79", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-64", 0 ],
									"destination" : [ "obj-98", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-4", 0 ],
									"destination" : [ "obj-3", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-3", 0 ],
									"destination" : [ "obj-5", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-79", 0 ],
									"destination" : [ "obj-3", 1 ],
									"hidden" : 0,
									"midpoints" : [ 94.5, 159.0, 67.0, 159.0 ]
								}

							}
 ]
					}
,
					"saved_object_attributes" : 					{
						"fontname" : "Arial",
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"fontface" : 0,
						"fontsize" : 12.0,
						"default_fontsize" : 12.0,
						"globalpatchername" : ""
					}

				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "sin/rect",
					"fontname" : "Arial",
					"presentation_rect" : [ 224.0, 278.0, 49.0, 20.0 ],
					"patching_rect" : [ 224.0, 278.0, 49.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-59",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 224.0, 296.0, 40.0, 40.0 ],
					"mult" : 0.5,
					"min" : 1.0,
					"patching_rect" : [ 224.0, 296.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-58",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 25.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 403.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-56",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "!/ 1000.",
					"fontname" : "Arial",
					"patching_rect" : [ 651.0, 543.0, 51.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-49",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "umenu",
					"fontname" : "Arial",
					"items" : [ "1nd", ",", "1n", ",", "1nt", ",", "2nd", ",", "2n", ",", "2nt", ",", "4nd", ",", "4n", ",", "4nt", ",", "8nd", ",", "8n", ",", "8nt", ",", "16nd", ",", "16n", ",", "16nt", ",", "32nd", ",", "32n", ",", "32nt", ",", "64nd", ",", "64n", ",", "128n" ],
					"labelclick" : 1,
					"patching_rect" : [ 625.0, 448.0, 71.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-51",
					"numinlets" : 1,
					"types" : [  ],
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 532.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.1,
					"patching_rect" : [ 532.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-41",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 250.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "depth",
					"fontname" : "Arial",
					"presentation_rect" : [ 530.0, 44.0, 41.0, 20.0 ],
					"patching_rect" : [ 530.0, 44.0, 41.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-39",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "freq",
					"fontname" : "Arial",
					"presentation_rect" : [ 403.0, 44.0, 31.0, 20.0 ],
					"patching_rect" : [ 403.0, 44.0, 31.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-30",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "--------------- vibrato ----------------",
					"fontname" : "Arial",
					"presentation_rect" : [ 403.0, 26.5, 177.0, 20.0 ],
					"patching_rect" : [ 403.0, 26.5, 177.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-20",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 403.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.1,
					"patching_rect" : [ 403.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-29",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 339.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-19",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"fontname" : "Arial",
					"patching_rect" : [ 146.0, 107.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-12",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "umenu",
					"fontname" : "Arial",
					"items" : [ "1nd", ",", "1n", ",", "1nt", ",", "2nd", ",", "2n", ",", "2nt", ",", "4nd", ",", "4n", ",", "4nt", ",", "8nd", ",", "8n", ",", "8nt", ",", "16nd", ",", "16n", ",", "16nt", ",", "32nd", ",", "32n", ",", "32nt", ",", "64nd", ",", "64n", ",", "128n" ],
					"labelclick" : 1,
					"patching_rect" : [ 461.0, 448.0, 71.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-8",
					"numinlets" : 1,
					"types" : [  ],
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s WaveGen_Interp",
					"fontname" : "Arial",
					"patching_rect" : [ 487.0, 572.0, 111.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-4",
					"numinlets" : 1,
					"numoutlets" : 0,
					"color" : [ 1.0, 0.901961, 0.0, 0.8 ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "!/ 1000.",
					"fontname" : "Arial",
					"patching_rect" : [ 854.0, 543.0, 51.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-80",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "umenu",
					"fontname" : "Arial",
					"items" : [ "1nd", ",", "1n", ",", "1nt", ",", "2nd", ",", "2n", ",", "2nt", ",", "4nd", ",", "4n", ",", "4nt", ",", "8nd", ",", "8n", ",", "8nt", ",", "16nd", ",", "16n", ",", "16nt", ",", "32nd", ",", "32n", ",", "32nt", ",", "64nd", ",", "64n", ",", "128n" ],
					"labelclick" : 1,
					"patching_rect" : [ 828.0, 448.0, 71.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-78",
					"numinlets" : 1,
					"types" : [  ],
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "stability",
					"fontname" : "Arial",
					"presentation_rect" : [ 172.0, 278.0, 51.0, 20.0 ],
					"patching_rect" : [ 172.0, 278.0, 51.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-97",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "depth",
					"fontname" : "Arial",
					"presentation_rect" : [ 276.0, 278.0, 41.0, 20.0 ],
					"patching_rect" : [ 276.0, 278.0, 41.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-96",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 172.0, 296.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 172.0, 296.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-86",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 50.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 276.0, 296.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 276.0, 296.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-9",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "0 0 0 0 0 0 0 0",
					"fontname" : "Arial",
					"patching_rect" : [ 839.0, 160.0, 97.0, 18.0 ],
					"fontsize" : 12.0,
					"id" : "obj-45",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "prepend midinote",
					"fontname" : "Arial",
					"patching_rect" : [ 17.0, 69.0, 103.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-40",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "pack 0 0",
					"fontname" : "Arial",
					"patching_rect" : [ 17.0, 45.0, 56.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-38",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "R",
					"fontname" : "Arial",
					"presentation_rect" : [ 339.0, 44.0, 19.0, 20.0 ],
					"patching_rect" : [ 339.0, 44.0, 19.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 11.595187,
					"presentation" : 1,
					"id" : "obj-14",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "S",
					"fontname" : "Arial",
					"presentation_rect" : [ 274.0, 44.0, 19.0, 20.0 ],
					"patching_rect" : [ 274.0, 44.0, 19.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 11.595187,
					"presentation" : 1,
					"id" : "obj-15",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "D",
					"fontname" : "Arial",
					"presentation_rect" : [ 210.0, 44.0, 19.0, 20.0 ],
					"patching_rect" : [ 210.0, 44.0, 19.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 11.595187,
					"presentation" : 1,
					"id" : "obj-16",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 274.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.01,
					"patching_rect" : [ 274.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-18",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 339.0, 61.0, 40.0, 40.0 ],
					"mult" : 10.0,
					"patching_rect" : [ 339.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-21",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 2000.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 210.0, 61.0, 40.0, 40.0 ],
					"mult" : 10.0,
					"patching_rect" : [ 210.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-22",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 146.0, 61.0, 40.0, 40.0 ],
					"mult" : 10.0,
					"patching_rect" : [ 146.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-23",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 1000.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "A",
					"fontname" : "Arial",
					"presentation_rect" : [ 146.0, 44.0, 19.0, 20.0 ],
					"patching_rect" : [ 146.0, 44.0, 19.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 11.595187,
					"presentation" : 1,
					"id" : "obj-36",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "fade dur (ms)",
					"fontname" : "Arial",
					"patching_rect" : [ 543.0, 543.0, 82.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"id" : "obj-2",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "number",
					"minimum" : 10,
					"fontname" : "Arial",
					"maximum" : 10000,
					"patching_rect" : [ 487.0, 543.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-128",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "int", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "stability",
					"fontname" : "Arial",
					"presentation_rect" : [ 597.0, 44.0, 51.0, 20.0 ],
					"patching_rect" : [ 597.0, 44.0, 51.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-129",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "dial",
					"presentation_rect" : [ 596.0, 61.0, 40.0, 40.0 ],
					"mult" : 0.0005,
					"patching_rect" : [ 596.0, 61.0, 40.0, 40.0 ],
					"presentation" : 1,
					"id" : "obj-130",
					"numinlets" : 1,
					"floatoutput" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "float" ],
					"size" : 100.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "poly~ WaveGenPoly 8",
					"fontname" : "Arial",
					"patching_rect" : [ 17.0, 132.5, 919.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-7",
					"numinlets" : 15,
					"numoutlets" : 3,
					"outlettype" : [ "signal", "signal", "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "p WaveGen",
					"fontname" : "Arial",
					"presentation_rect" : [ 336.0, 170.0, 106.0, 27.0 ],
					"patching_rect" : [ 336.0, 170.0, 106.0, 27.0 ],
					"fontsize" : 18.0,
					"presentation" : 1,
					"id" : "obj-3",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patcher" : 					{
						"fileversion" : 1,
						"rect" : [ 67.0, 44.0, 1148.0, 750.0 ],
						"bglocked" : 0,
						"defrect" : [ 67.0, 44.0, 1148.0, 750.0 ],
						"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
						"openinpresentation" : 0,
						"default_fontsize" : 12.0,
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"gridonopen" : 0,
						"gridsize" : [ 10.0, 10.0 ],
						"gridsnaponopen" : 0,
						"toolbarvisible" : 1,
						"boxanimatetime" : 200,
						"imprint" : 0,
						"metadata" : [  ],
						"boxes" : [ 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "sine, calc",
									"fontname" : "Arial",
									"patching_rect" : [ 424.0, 587.0, 61.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-21",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "loadbang",
									"fontname" : "Arial",
									"patching_rect" : [ 424.0, 560.0, 60.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-16",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "autocalculate",
									"fontname" : "Arial",
									"presentation_rect" : [ 120.0, 308.0, 81.0, 20.0 ],
									"patching_rect" : [ 77.0, 565.0, 81.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-9",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "base unit function",
									"fontname" : "Arial",
									"presentation_rect" : [ 473.0, 307.0, 105.0, 20.0 ],
									"patching_rect" : [ 477.0, 469.0, 105.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-2",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "loadmess 10",
									"fontname" : "Arial",
									"patching_rect" : [ 958.0, 158.0, 79.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-1",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "button",
									"patching_rect" : [ 319.0, 673.0, 20.0, 20.0 ],
									"id" : "obj-89",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "umenu",
									"fontname" : "Arial",
									"presentation_rect" : [ 646.0, 70.0, 46.0, 20.0 ],
									"items" : [ "off", ",", "rand1", ",", "randall" ],
									"patching_rect" : [ 641.0, 66.0, 48.0, 20.0 ],
									"arrow" : 0,
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-85",
									"numinlets" : 1,
									"types" : [  ],
									"numoutlets" : 3,
									"outlettype" : [ "int", "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "gate 2 0",
									"fontname" : "Arial",
									"patching_rect" : [ 661.0, 93.0, 54.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-86",
									"numinlets" : 2,
									"numoutlets" : 2,
									"outlettype" : [ "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "r recalc",
									"fontname" : "Arial",
									"patching_rect" : [ 696.0, 67.0, 50.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-87",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "umenu",
									"fontname" : "Arial",
									"presentation_rect" : [ 364.0, 70.0, 46.0, 20.0 ],
									"items" : [ "off", ",", "rand1", ",", "randall" ],
									"patching_rect" : [ 359.0, 67.0, 48.0, 20.0 ],
									"arrow" : 0,
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-82",
									"numinlets" : 1,
									"types" : [  ],
									"numoutlets" : 3,
									"outlettype" : [ "int", "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "gate 2 0",
									"fontname" : "Arial",
									"patching_rect" : [ 379.0, 94.0, 54.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-83",
									"numinlets" : 2,
									"numoutlets" : 2,
									"outlettype" : [ "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "r recalc",
									"fontname" : "Arial",
									"patching_rect" : [ 414.0, 68.0, 50.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-84",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "umenu",
									"fontname" : "Arial",
									"presentation_rect" : [ 63.0, 70.0, 46.0, 20.0 ],
									"items" : [ "off", ",", "rand1", ",", "randall" ],
									"patching_rect" : [ 58.0, 69.0, 48.0, 20.0 ],
									"arrow" : 0,
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-79",
									"numinlets" : 1,
									"types" : [  ],
									"numoutlets" : 3,
									"outlettype" : [ "int", "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "gate 2 0",
									"fontname" : "Arial",
									"patching_rect" : [ 78.0, 96.0, 54.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-74",
									"numinlets" : 2,
									"numoutlets" : 2,
									"outlettype" : [ "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "r recalc",
									"fontname" : "Arial",
									"patching_rect" : [ 113.0, 70.0, 50.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-72",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "s recalc",
									"fontname" : "Arial",
									"patching_rect" : [ 205.0, 656.0, 52.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-69",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "button",
									"presentation_rect" : [ 716.0, 70.0, 20.0, 20.0 ],
									"patching_rect" : [ 682.0, 121.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-39",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "button",
									"presentation_rect" : [ 698.0, 70.0, 20.0, 20.0 ],
									"patching_rect" : [ 664.0, 121.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-43",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "p ctrl",
									"fontname" : "Arial",
									"patching_rect" : [ 664.0, 148.0, 37.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-46",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ],
									"patcher" : 									{
										"fileversion" : 1,
										"rect" : [ 12.0, 387.0, 523.0, 234.0 ],
										"bglocked" : 0,
										"defrect" : [ 12.0, 387.0, 523.0, 234.0 ],
										"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
										"openinpresentation" : 0,
										"default_fontsize" : 12.0,
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"gridonopen" : 0,
										"gridsize" : [ 10.0, 10.0 ],
										"gridsnaponopen" : 0,
										"toolbarvisible" : 1,
										"boxanimatetime" : 200,
										"imprint" : 0,
										"metadata" : [  ],
										"boxes" : [ 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "pak setminmax -1. 1.",
													"fontname" : "Arial",
													"patching_rect" : [ 282.0, 82.0, 122.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-72",
													"numinlets" : 3,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "flonum",
													"fontname" : "Arial",
													"patching_rect" : [ 333.5, 54.0, 50.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-78",
													"numinlets" : 1,
													"numoutlets" : 2,
													"outlettype" : [ "float", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "flonum",
													"fontname" : "Arial",
													"patching_rect" : [ 384.5, 54.0, 50.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-79",
													"numinlets" : 1,
													"numoutlets" : 2,
													"outlettype" : [ "float", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 146.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-10",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "bang" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "xrandlist -1 1",
													"fontname" : "Arial",
													"patching_rect" : [ 146.0, 82.0, 79.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-19",
													"numinlets" : 4,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "p rand1",
													"fontname" : "Arial",
													"patching_rect" : [ 84.0, 82.0, 51.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-167",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ],
													"patcher" : 													{
														"fileversion" : 1,
														"rect" : [ 25.0, 69.0, 640.0, 480.0 ],
														"bglocked" : 0,
														"defrect" : [ 25.0, 69.0, 640.0, 480.0 ],
														"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
														"openinpresentation" : 0,
														"default_fontsize" : 12.0,
														"default_fontface" : 0,
														"default_fontname" : "Arial",
														"gridonopen" : 0,
														"gridsize" : [ 10.0, 10.0 ],
														"gridsnaponopen" : 0,
														"toolbarvisible" : 1,
														"boxanimatetime" : 200,
														"imprint" : 0,
														"metadata" : [  ],
														"boxes" : [ 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "+ 1",
																	"fontname" : "Arial",
																	"patching_rect" : [ 279.0, 217.0, 32.5, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-13",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "int" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "message",
																	"text" : "clear, bang",
																	"fontname" : "Arial",
																	"patching_rect" : [ 302.0, 150.0, 70.0, 18.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-12",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "pack 0 0.",
																	"fontname" : "Arial",
																	"patching_rect" : [ 257.0, 242.0, 59.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-9",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "inlet",
																	"patching_rect" : [ 336.0, 50.0, 25.0, 25.0 ],
																	"id" : "obj-8",
																	"numinlets" : 0,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ],
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "inlet",
																	"patching_rect" : [ 256.0, 40.0, 25.0, 25.0 ],
																	"id" : "obj-7",
																	"numinlets" : 0,
																	"numoutlets" : 1,
																	"outlettype" : [ "bang" ],
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "urn",
																	"fontname" : "Arial",
																	"patching_rect" : [ 262.0, 146.0, 32.5, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-6",
																	"numinlets" : 2,
																	"numoutlets" : 2,
																	"outlettype" : [ "int", "bang" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "outlet",
																	"patching_rect" : [ 237.0, 389.0, 25.0, 25.0 ],
																	"id" : "obj-5",
																	"numinlets" : 1,
																	"numoutlets" : 0,
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "prepend set",
																	"fontname" : "Arial",
																	"patching_rect" : [ 260.0, 297.0, 74.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-3",
																	"numinlets" : 1,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "t b b b",
																	"fontname" : "Arial",
																	"patching_rect" : [ 255.0, 85.0, 46.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-2",
																	"numinlets" : 1,
																	"numoutlets" : 3,
																	"outlettype" : [ "bang", "bang", "bang" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "xrand -1 1",
																	"fontname" : "Arial",
																	"patching_rect" : [ 294.0, 108.0, 65.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-1",
																	"numinlets" : 4,
																	"numoutlets" : 2,
																	"outlettype" : [ "float", "bang" ]
																}

															}
 ],
														"lines" : [ 															{
																"patchline" : 																{
																	"source" : [ "obj-3", 0 ],
																	"destination" : [ "obj-5", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-9", 0 ],
																	"destination" : [ "obj-3", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-1", 0 ],
																	"destination" : [ "obj-9", 1 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-7", 0 ],
																	"destination" : [ "obj-2", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 2 ],
																	"destination" : [ "obj-1", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 0 ],
																	"destination" : [ "obj-5", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 1 ],
																	"destination" : [ "obj-6", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-8", 0 ],
																	"destination" : [ "obj-6", 1 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-12", 0 ],
																	"destination" : [ "obj-6", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-6", 1 ],
																	"destination" : [ "obj-12", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-6", 0 ],
																	"destination" : [ "obj-13", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-13", 0 ],
																	"destination" : [ "obj-9", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
 ]
													}
,
													"saved_object_attributes" : 													{
														"fontname" : "Arial",
														"default_fontface" : 0,
														"default_fontname" : "Arial",
														"fontface" : 0,
														"fontsize" : 12.0,
														"default_fontsize" : 12.0,
														"globalpatchername" : ""
													}

												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 84.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-6",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "bang" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "message",
													"text" : "size $1",
													"fontname" : "Arial",
													"patching_rect" : [ 229.0, 82.0, 49.0, 18.0 ],
													"fontsize" : 12.0,
													"id" : "obj-5",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "r size",
													"fontname" : "Arial",
													"patching_rect" : [ 229.0, 40.0, 39.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-3",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "outlet",
													"patching_rect" : [ 84.0, 143.0, 25.0, 25.0 ],
													"id" : "obj-2",
													"numinlets" : 1,
													"numoutlets" : 0,
													"comment" : ""
												}

											}
 ],
										"lines" : [ 											{
												"patchline" : 												{
													"source" : [ "obj-72", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 291.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-78", 0 ],
													"destination" : [ "obj-72", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-79", 0 ],
													"destination" : [ "obj-72", 2 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-19", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 155.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-167", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-6", 0 ],
													"destination" : [ "obj-167", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-167", 1 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 68.0, 125.5, 68.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-19", 1 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 71.0, 175.5, 71.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-10", 0 ],
													"destination" : [ "obj-19", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-5", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-5", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
 ]
									}
,
									"saved_object_attributes" : 									{
										"fontname" : "Arial",
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"fontface" : 0,
										"fontsize" : 12.0,
										"default_fontsize" : 12.0,
										"globalpatchername" : ""
									}

								}

							}
, 							{
								"box" : 								{
									"maxclass" : "button",
									"presentation_rect" : [ 432.0, 70.0, 20.0, 20.0 ],
									"patching_rect" : [ 395.0, 121.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-33",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "button",
									"presentation_rect" : [ 414.0, 70.0, 20.0, 20.0 ],
									"patching_rect" : [ 377.0, 121.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-35",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "p ctrl",
									"fontname" : "Arial",
									"patching_rect" : [ 377.0, 148.0, 37.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-37",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ],
									"patcher" : 									{
										"fileversion" : 1,
										"rect" : [ 12.0, 387.0, 523.0, 234.0 ],
										"bglocked" : 0,
										"defrect" : [ 12.0, 387.0, 523.0, 234.0 ],
										"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
										"openinpresentation" : 0,
										"default_fontsize" : 12.0,
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"gridonopen" : 0,
										"gridsize" : [ 10.0, 10.0 ],
										"gridsnaponopen" : 0,
										"toolbarvisible" : 1,
										"boxanimatetime" : 200,
										"imprint" : 0,
										"metadata" : [  ],
										"boxes" : [ 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "pak setminmax -1. 1.",
													"fontname" : "Arial",
													"patching_rect" : [ 282.0, 82.0, 122.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-72",
													"numinlets" : 3,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "flonum",
													"fontname" : "Arial",
													"patching_rect" : [ 333.5, 54.0, 50.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-78",
													"numinlets" : 1,
													"numoutlets" : 2,
													"outlettype" : [ "float", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "flonum",
													"fontname" : "Arial",
													"patching_rect" : [ 384.5, 54.0, 50.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-79",
													"numinlets" : 1,
													"numoutlets" : 2,
													"outlettype" : [ "float", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 146.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-10",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "bang" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "xrandlist -1 1",
													"fontname" : "Arial",
													"patching_rect" : [ 146.0, 82.0, 79.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-19",
													"numinlets" : 4,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "p rand1",
													"fontname" : "Arial",
													"patching_rect" : [ 84.0, 82.0, 51.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-167",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "bang" ],
													"patcher" : 													{
														"fileversion" : 1,
														"rect" : [ 25.0, 69.0, 640.0, 480.0 ],
														"bglocked" : 0,
														"defrect" : [ 25.0, 69.0, 640.0, 480.0 ],
														"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
														"openinpresentation" : 0,
														"default_fontsize" : 12.0,
														"default_fontface" : 0,
														"default_fontname" : "Arial",
														"gridonopen" : 0,
														"gridsize" : [ 10.0, 10.0 ],
														"gridsnaponopen" : 0,
														"toolbarvisible" : 1,
														"boxanimatetime" : 200,
														"imprint" : 0,
														"metadata" : [  ],
														"boxes" : [ 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "+ 1",
																	"fontname" : "Arial",
																	"patching_rect" : [ 279.0, 217.0, 32.5, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-13",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "int" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "message",
																	"text" : "clear, bang",
																	"fontname" : "Arial",
																	"patching_rect" : [ 302.0, 150.0, 70.0, 18.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-12",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "pack 0 0.",
																	"fontname" : "Arial",
																	"patching_rect" : [ 257.0, 242.0, 59.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-9",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "inlet",
																	"patching_rect" : [ 336.0, 50.0, 25.0, 25.0 ],
																	"id" : "obj-8",
																	"numinlets" : 0,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ],
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "inlet",
																	"patching_rect" : [ 256.0, 40.0, 25.0, 25.0 ],
																	"id" : "obj-7",
																	"numinlets" : 0,
																	"numoutlets" : 1,
																	"outlettype" : [ "bang" ],
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "urn",
																	"fontname" : "Arial",
																	"patching_rect" : [ 262.0, 146.0, 32.5, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-6",
																	"numinlets" : 2,
																	"numoutlets" : 2,
																	"outlettype" : [ "int", "bang" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "outlet",
																	"patching_rect" : [ 237.0, 389.0, 25.0, 25.0 ],
																	"id" : "obj-5",
																	"numinlets" : 1,
																	"numoutlets" : 0,
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "prepend set",
																	"fontname" : "Arial",
																	"patching_rect" : [ 260.0, 297.0, 74.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-3",
																	"numinlets" : 1,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "t b b b",
																	"fontname" : "Arial",
																	"patching_rect" : [ 255.0, 85.0, 46.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-2",
																	"numinlets" : 1,
																	"numoutlets" : 3,
																	"outlettype" : [ "bang", "bang", "bang" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "xrand -1 1",
																	"fontname" : "Arial",
																	"patching_rect" : [ 294.0, 108.0, 65.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-1",
																	"numinlets" : 4,
																	"numoutlets" : 2,
																	"outlettype" : [ "float", "bang" ]
																}

															}
 ],
														"lines" : [ 															{
																"patchline" : 																{
																	"source" : [ "obj-13", 0 ],
																	"destination" : [ "obj-9", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-6", 0 ],
																	"destination" : [ "obj-13", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-6", 1 ],
																	"destination" : [ "obj-12", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-12", 0 ],
																	"destination" : [ "obj-6", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-8", 0 ],
																	"destination" : [ "obj-6", 1 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 1 ],
																	"destination" : [ "obj-6", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 0 ],
																	"destination" : [ "obj-5", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 2 ],
																	"destination" : [ "obj-1", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-7", 0 ],
																	"destination" : [ "obj-2", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-1", 0 ],
																	"destination" : [ "obj-9", 1 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-9", 0 ],
																	"destination" : [ "obj-3", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-3", 0 ],
																	"destination" : [ "obj-5", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
 ]
													}
,
													"saved_object_attributes" : 													{
														"fontname" : "Arial",
														"default_fontface" : 0,
														"default_fontname" : "Arial",
														"fontface" : 0,
														"fontsize" : 12.0,
														"default_fontsize" : 12.0,
														"globalpatchername" : ""
													}

												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 84.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-6",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "bang" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "message",
													"text" : "size $1",
													"fontname" : "Arial",
													"patching_rect" : [ 229.0, 82.0, 49.0, 18.0 ],
													"fontsize" : 12.0,
													"id" : "obj-5",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "r size",
													"fontname" : "Arial",
													"patching_rect" : [ 229.0, 40.0, 39.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-3",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "outlet",
													"patching_rect" : [ 84.0, 143.0, 25.0, 25.0 ],
													"id" : "obj-2",
													"numinlets" : 1,
													"numoutlets" : 0,
													"comment" : ""
												}

											}
 ],
										"lines" : [ 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-5", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-5", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-10", 0 ],
													"destination" : [ "obj-19", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-19", 1 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 71.0, 175.5, 71.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-167", 1 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 68.0, 125.5, 68.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-6", 0 ],
													"destination" : [ "obj-167", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-167", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-19", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 155.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-79", 0 ],
													"destination" : [ "obj-72", 2 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-78", 0 ],
													"destination" : [ "obj-72", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-72", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 291.5, 119.0, 93.5, 119.0 ]
												}

											}
 ]
									}
,
									"saved_object_attributes" : 									{
										"fontname" : "Arial",
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"fontface" : 0,
										"fontsize" : 12.0,
										"default_fontsize" : 12.0,
										"globalpatchername" : ""
									}

								}

							}
, 							{
								"box" : 								{
									"maxclass" : "button",
									"presentation_rect" : [ 133.0, 70.0, 20.0, 20.0 ],
									"patching_rect" : [ 96.0, 123.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-32",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "button",
									"presentation_rect" : [ 115.0, 70.0, 20.0, 20.0 ],
									"patching_rect" : [ 78.0, 123.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-31",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "p ctrl",
									"fontname" : "Arial",
									"patching_rect" : [ 78.0, 150.0, 37.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-29",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ],
									"patcher" : 									{
										"fileversion" : 1,
										"rect" : [ 12.0, 387.0, 523.0, 234.0 ],
										"bglocked" : 0,
										"defrect" : [ 12.0, 387.0, 523.0, 234.0 ],
										"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
										"openinpresentation" : 0,
										"default_fontsize" : 12.0,
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"gridonopen" : 0,
										"gridsize" : [ 10.0, 10.0 ],
										"gridsnaponopen" : 0,
										"toolbarvisible" : 1,
										"boxanimatetime" : 200,
										"imprint" : 0,
										"metadata" : [  ],
										"boxes" : [ 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "pak setminmax -1. 1.",
													"fontname" : "Arial",
													"patching_rect" : [ 282.0, 82.0, 122.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-72",
													"numinlets" : 3,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "flonum",
													"fontname" : "Arial",
													"patching_rect" : [ 333.5, 54.0, 50.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-78",
													"numinlets" : 1,
													"numoutlets" : 2,
													"outlettype" : [ "float", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "flonum",
													"fontname" : "Arial",
													"patching_rect" : [ 384.5, 54.0, 50.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-79",
													"numinlets" : 1,
													"numoutlets" : 2,
													"outlettype" : [ "float", "bang" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 146.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-10",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "bang" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "xrandlist -1 1",
													"fontname" : "Arial",
													"patching_rect" : [ 146.0, 82.0, 79.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-19",
													"numinlets" : 4,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "p rand1",
													"fontname" : "Arial",
													"patching_rect" : [ 84.0, 82.0, 51.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-167",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ],
													"patcher" : 													{
														"fileversion" : 1,
														"rect" : [ 25.0, 69.0, 640.0, 480.0 ],
														"bglocked" : 0,
														"defrect" : [ 25.0, 69.0, 640.0, 480.0 ],
														"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
														"openinpresentation" : 0,
														"default_fontsize" : 12.0,
														"default_fontface" : 0,
														"default_fontname" : "Arial",
														"gridonopen" : 0,
														"gridsize" : [ 10.0, 10.0 ],
														"gridsnaponopen" : 0,
														"toolbarvisible" : 1,
														"boxanimatetime" : 200,
														"imprint" : 0,
														"metadata" : [  ],
														"boxes" : [ 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "+ 1",
																	"fontname" : "Arial",
																	"patching_rect" : [ 279.0, 217.0, 32.5, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-13",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "int" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "message",
																	"text" : "clear, bang",
																	"fontname" : "Arial",
																	"patching_rect" : [ 302.0, 150.0, 70.0, 18.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-12",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "pack 0 0.",
																	"fontname" : "Arial",
																	"patching_rect" : [ 257.0, 242.0, 59.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-9",
																	"numinlets" : 2,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "inlet",
																	"patching_rect" : [ 336.0, 50.0, 25.0, 25.0 ],
																	"id" : "obj-8",
																	"numinlets" : 0,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ],
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "inlet",
																	"patching_rect" : [ 256.0, 40.0, 25.0, 25.0 ],
																	"id" : "obj-7",
																	"numinlets" : 0,
																	"numoutlets" : 1,
																	"outlettype" : [ "bang" ],
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "urn",
																	"fontname" : "Arial",
																	"patching_rect" : [ 262.0, 146.0, 32.5, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-6",
																	"numinlets" : 2,
																	"numoutlets" : 2,
																	"outlettype" : [ "int", "bang" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "outlet",
																	"patching_rect" : [ 237.0, 389.0, 25.0, 25.0 ],
																	"id" : "obj-5",
																	"numinlets" : 1,
																	"numoutlets" : 0,
																	"comment" : ""
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "prepend set",
																	"fontname" : "Arial",
																	"patching_rect" : [ 260.0, 297.0, 74.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-3",
																	"numinlets" : 1,
																	"numoutlets" : 1,
																	"outlettype" : [ "" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "t b b b",
																	"fontname" : "Arial",
																	"patching_rect" : [ 255.0, 85.0, 46.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-2",
																	"numinlets" : 1,
																	"numoutlets" : 3,
																	"outlettype" : [ "bang", "bang", "bang" ]
																}

															}
, 															{
																"box" : 																{
																	"maxclass" : "newobj",
																	"text" : "xrand -1 1",
																	"fontname" : "Arial",
																	"patching_rect" : [ 294.0, 108.0, 65.0, 20.0 ],
																	"fontsize" : 12.0,
																	"id" : "obj-1",
																	"numinlets" : 4,
																	"numoutlets" : 2,
																	"outlettype" : [ "float", "bang" ]
																}

															}
 ],
														"lines" : [ 															{
																"patchline" : 																{
																	"source" : [ "obj-3", 0 ],
																	"destination" : [ "obj-5", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-9", 0 ],
																	"destination" : [ "obj-3", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-1", 0 ],
																	"destination" : [ "obj-9", 1 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-7", 0 ],
																	"destination" : [ "obj-2", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 2 ],
																	"destination" : [ "obj-1", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 0 ],
																	"destination" : [ "obj-5", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-2", 1 ],
																	"destination" : [ "obj-6", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-8", 0 ],
																	"destination" : [ "obj-6", 1 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-12", 0 ],
																	"destination" : [ "obj-6", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-6", 1 ],
																	"destination" : [ "obj-12", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-6", 0 ],
																	"destination" : [ "obj-13", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
, 															{
																"patchline" : 																{
																	"source" : [ "obj-13", 0 ],
																	"destination" : [ "obj-9", 0 ],
																	"hidden" : 0,
																	"midpoints" : [  ]
																}

															}
 ]
													}
,
													"saved_object_attributes" : 													{
														"fontname" : "Arial",
														"default_fontface" : 0,
														"default_fontname" : "Arial",
														"fontface" : 0,
														"fontsize" : 12.0,
														"default_fontsize" : 12.0,
														"globalpatchername" : ""
													}

												}

											}
, 											{
												"box" : 												{
													"maxclass" : "inlet",
													"patching_rect" : [ 84.0, 40.0, 25.0, 25.0 ],
													"id" : "obj-6",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "bang" ],
													"comment" : ""
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "message",
													"text" : "size $1",
													"fontname" : "Arial",
													"patching_rect" : [ 229.0, 82.0, 49.0, 18.0 ],
													"fontsize" : 12.0,
													"id" : "obj-5",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "r size",
													"fontname" : "Arial",
													"patching_rect" : [ 229.0, 40.0, 39.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-3",
													"numinlets" : 0,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "outlet",
													"patching_rect" : [ 84.0, 143.0, 25.0, 25.0 ],
													"id" : "obj-2",
													"numinlets" : 1,
													"numoutlets" : 0,
													"comment" : ""
												}

											}
 ],
										"lines" : [ 											{
												"patchline" : 												{
													"source" : [ "obj-72", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 291.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-78", 0 ],
													"destination" : [ "obj-72", 1 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-79", 0 ],
													"destination" : [ "obj-72", 2 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-19", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 155.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-167", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-6", 0 ],
													"destination" : [ "obj-167", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-167", 1 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 68.0, 125.5, 68.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-19", 1 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 71.0, 175.5, 71.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-10", 0 ],
													"destination" : [ "obj-19", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-5", 0 ],
													"destination" : [ "obj-2", 0 ],
													"hidden" : 0,
													"midpoints" : [ 238.5, 119.0, 93.5, 119.0 ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-3", 0 ],
													"destination" : [ "obj-5", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
 ]
									}
,
									"saved_object_attributes" : 									{
										"fontname" : "Arial",
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"fontface" : 0,
										"fontsize" : 12.0,
										"default_fontsize" : 12.0,
										"globalpatchername" : ""
									}

								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "s size",
									"fontname" : "Arial",
									"patching_rect" : [ 958.0, 216.0, 41.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-28",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"presentation_rect" : [ 209.0, 309.0, 20.0, 20.0 ],
									"patching_rect" : [ 166.0, 566.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-17",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "t calc b",
									"fontname" : "Arial",
									"patching_rect" : [ 175.0, 627.0, 49.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-15",
									"numinlets" : 1,
									"numoutlets" : 2,
									"outlettype" : [ "calc", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "r WaveGen_Interp",
									"fontname" : "Arial",
									"patching_rect" : [ 228.0, 549.0, 109.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-13",
									"numinlets" : 0,
									"numoutlets" : 1,
									"color" : [ 1.0, 0.901961, 0.0, 0.8 ],
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "gate",
									"fontname" : "Arial",
									"patching_rect" : [ 175.0, 601.0, 34.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-12",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "pipe 500",
									"fontname" : "Arial",
									"patching_rect" : [ 190.0, 574.0, 57.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-3",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "p wave-switch",
									"fontname" : "Arial",
									"patching_rect" : [ 715.0, 590.0, 86.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-91",
									"numinlets" : 0,
									"numoutlets" : 1,
									"outlettype" : [ "" ],
									"patcher" : 									{
										"fileversion" : 1,
										"rect" : [ 25.0, 69.0, 640.0, 480.0 ],
										"bglocked" : 0,
										"defrect" : [ 25.0, 69.0, 640.0, 480.0 ],
										"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
										"openinpresentation" : 0,
										"default_fontsize" : 12.0,
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"gridonopen" : 0,
										"gridsize" : [ 10.0, 10.0 ],
										"gridsnaponopen" : 0,
										"toolbarvisible" : 1,
										"boxanimatetime" : 200,
										"imprint" : 0,
										"metadata" : [  ],
										"boxes" : [ 											{
												"box" : 												{
													"maxclass" : "message",
													"text" : "set waveform2",
													"fontname" : "Arial",
													"patching_rect" : [ 114.0, 174.0, 89.0, 18.0 ],
													"fontsize" : 12.0,
													"id" : "obj-89",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "message",
													"text" : "set waveform",
													"fontname" : "Arial",
													"patching_rect" : [ 83.0, 160.0, 82.0, 18.0 ],
													"fontsize" : 12.0,
													"id" : "obj-88",
													"numinlets" : 2,
													"numoutlets" : 1,
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "sel 1 2",
													"fontname" : "Arial",
													"patching_rect" : [ 104.0, 128.0, 46.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-84",
													"numinlets" : 1,
													"numoutlets" : 3,
													"outlettype" : [ "bang", "bang", "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "newobj",
													"text" : "r WaveGen_lastbuf",
													"fontname" : "Arial",
													"patching_rect" : [ 50.0, 100.0, 113.0, 20.0 ],
													"fontsize" : 12.0,
													"id" : "obj-82",
													"numinlets" : 0,
													"numoutlets" : 1,
													"color" : [ 1.0, 0.901961, 0.0, 0.8 ],
													"outlettype" : [ "" ]
												}

											}
, 											{
												"box" : 												{
													"maxclass" : "outlet",
													"patching_rect" : [ 98.0, 252.0, 25.0, 25.0 ],
													"id" : "obj-90",
													"numinlets" : 1,
													"numoutlets" : 0,
													"comment" : ""
												}

											}
 ],
										"lines" : [ 											{
												"patchline" : 												{
													"source" : [ "obj-89", 0 ],
													"destination" : [ "obj-90", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-88", 0 ],
													"destination" : [ "obj-90", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-82", 0 ],
													"destination" : [ "obj-84", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-84", 0 ],
													"destination" : [ "obj-88", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
, 											{
												"patchline" : 												{
													"source" : [ "obj-84", 1 ],
													"destination" : [ "obj-89", 0 ],
													"hidden" : 0,
													"midpoints" : [  ]
												}

											}
 ]
									}
,
									"saved_object_attributes" : 									{
										"fontname" : "Arial",
										"default_fontface" : 0,
										"default_fontname" : "Arial",
										"fontface" : 0,
										"fontsize" : 12.0,
										"default_fontsize" : 12.0,
										"globalpatchername" : ""
									}

								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "buffer~ waveform2 92.88",
									"fontname" : "Arial",
									"patching_rect" : [ 957.0, 614.0, 144.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-107",
									"numinlets" : 1,
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "buffer~ waveform 92.88",
									"fontname" : "Arial",
									"patching_rect" : [ 956.0, 590.0, 137.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-34",
									"numinlets" : 1,
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "waveform~",
									"waveformcolor" : [ 0.129412, 0.0, 0.0, 1.0 ],
									"presentation_rect" : [ 358.0, 374.0, 231.0, 73.0 ],
									"buffername" : "waveform",
									"labels" : 0,
									"labelbgcolor" : [ 0.745098, 0.537255, 1.0, 1.0 ],
									"patching_rect" : [ 715.0, 618.0, 231.0, 73.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-80",
									"numinlets" : 5,
									"setmode" : 3,
									"ruler" : 0,
									"bgcolor" : [ 0.235294, 0.698039, 0.678431, 1.0 ],
									"numoutlets" : 6,
									"tickmarkcolor" : [ 0.392157, 0.392157, 0.392157, 1.0 ],
									"selectioncolor" : [ 0.0, 0.372549, 1.0, 0.5 ],
									"textcolor" : [  ],
									"outlettype" : [ "float", "float", "float", "float", "list", "" ],
									"vticks" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "umenu",
									"fontname" : "Arial",
									"presentation_rect" : [ 367.0, 305.0, 100.0, 20.0 ],
									"items" : [ "sine", ",", "saw", ",", "sine^2", ",", "sine^3", ",", "sine^4", ",", "tri", ",", "square" ],
									"patching_rect" : [ 370.0, 468.0, 100.0, 20.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-10",
									"numinlets" : 1,
									"types" : [  ],
									"numoutlets" : 3,
									"outlettype" : [ "int", "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "function $1",
									"fontname" : "Arial",
									"patching_rect" : [ 370.0, 492.0, 69.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-4",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "umenu",
									"fontname" : "Arial",
									"presentation_rect" : [ 402.0, 274.0, 141.0, 20.0 ],
									"items" : [ "linear", "interpolation", ",", "zero", "interpolation", ",", "median", "interpolation" ],
									"patching_rect" : [ 840.0, 461.0, 141.0, 20.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-105",
									"numinlets" : 1,
									"types" : [  ],
									"numoutlets" : 3,
									"outlettype" : [ "int", "", "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "wrapmode $1",
									"fontname" : "Arial",
									"patching_rect" : [ 840.0, 486.0, 83.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-102",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "number of partials",
									"fontname" : "Arial",
									"presentation_rect" : [ 134.0, 26.0, 107.0, 20.0 ],
									"patching_rect" : [ 1015.0, 187.0, 107.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-96",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "interpolate waveform (to remove clics introduced by speed deviation & wow)",
									"linecount" : 3,
									"presentation_linecount" : 3,
									"fontname" : "Arial",
									"presentation_rect" : [ 411.0, 224.0, 178.0, 48.0 ],
									"patching_rect" : [ 653.0, 459.0, 178.0, 48.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-68",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "adds a percent of random noise to the result (0-1)",
									"linecount" : 2,
									"presentation_linecount" : 2,
									"fontname" : "Arial",
									"presentation_rect" : [ 133.0, 266.0, 149.0, 34.0 ],
									"patching_rect" : [ 119.0, 419.0, 152.0, 34.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-66",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "maximum speed deviation if wow is enabled",
									"linecount" : 3,
									"presentation_linecount" : 2,
									"fontname" : "Arial",
									"presentation_rect" : [ 695.0, 284.0, 161.0, 34.0 ],
									"patching_rect" : [ 899.0, 359.0, 101.0, 48.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-56",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "add a different random speed change to each partials on each calculation",
									"linecount" : 3,
									"presentation_linecount" : 3,
									"fontname" : "Arial",
									"presentation_rect" : [ 696.0, 229.0, 194.0, 48.0 ],
									"patching_rect" : [ 645.0, 359.0, 194.0, 48.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-55",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "calculate waveform",
									"fontname" : "Arial",
									"presentation_rect" : [ 138.0, 232.0, 127.0, 20.0 ],
									"patching_rect" : [ 189.0, 377.0, 127.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-50",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "default sine waveform",
									"fontname" : "Arial",
									"patching_rect" : [ 171.0, 350.0, 127.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"id" : "obj-48",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "waveform is calculated each time any of the parameters changes",
									"linecount" : 2,
									"presentation_linecount" : 2,
									"fontname" : "Arial",
									"presentation_rect" : [ 87.0, 186.0, 213.0, 34.0 ],
									"patching_rect" : [ 80.0, 307.0, 213.0, 34.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-47",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "generate random phases each calculation (disables phases)",
									"linecount" : 2,
									"presentation_linecount" : 2,
									"fontname" : "Arial",
									"presentation_rect" : [ 404.0, 184.0, 178.0, 34.0 ],
									"patching_rect" : [ 396.0, 306.0, 178.0, 34.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-45",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "partials are integer multiple of the fondamental frequency (disables speed)",
									"linecount" : 2,
									"presentation_linecount" : 2,
									"fontname" : "Arial",
									"presentation_rect" : [ 660.0, 184.0, 261.0, 34.0 ],
									"patching_rect" : [ 616.0, 296.0, 261.0, 34.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-44",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "speed deviation (inharmonicity)",
									"fontname" : "Arial",
									"presentation_rect" : [ 750.0, 75.0, 177.0, 20.0 ],
									"patching_rect" : [ 762.0, 155.0, 177.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-42",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "phases",
									"fontname" : "Arial",
									"presentation_rect" : [ 573.0, 76.0, 49.0, 20.0 ],
									"patching_rect" : [ 585.0, 156.0, 49.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-41",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "partials",
									"fontname" : "Arial",
									"presentation_rect" : [ 286.0, 76.0, 49.0, 20.0 ],
									"patching_rect" : [ 298.0, 156.0, 49.0, 20.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-40",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"presentation_rect" : [ 65.0, 195.0, 20.0, 20.0 ],
									"patching_rect" : [ 58.0, 316.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-38",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "autocalc $1",
									"fontname" : "Arial",
									"patching_rect" : [ 58.0, 351.0, 72.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-36",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "s WaveGen",
									"fontname" : "Arial",
									"patching_rect" : [ 352.0, 534.0, 73.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-26",
									"numinlets" : 1,
									"numoutlets" : 0,
									"color" : [ 1.0, 0.901961, 0.0, 0.8 ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "comment",
									"text" : "args buf1 buf2 length_in_samps",
									"linecount" : 2,
									"fontname" : "Arial",
									"patching_rect" : [ 554.0, 626.0, 101.0, 34.0 ],
									"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
									"fontsize" : 12.0,
									"id" : "obj-11",
									"numinlets" : 1,
									"numoutlets" : 0
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "s WaveGen_lastbuf",
									"fontname" : "Arial",
									"patching_rect" : [ 341.0, 700.0, 115.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-8",
									"numinlets" : 1,
									"numoutlets" : 0,
									"color" : [ 1.0, 0.901961, 0.0, 0.8 ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "r WaveGen",
									"fontname" : "Arial",
									"patching_rect" : [ 341.0, 582.0, 71.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-7",
									"numinlets" : 0,
									"numoutlets" : 1,
									"color" : [ 1.0, 0.901961, 0.0, 0.8 ],
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "number",
									"fontname" : "Arial",
									"patching_rect" : [ 341.0, 674.0, 50.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-5",
									"numinlets" : 1,
									"numoutlets" : 2,
									"outlettype" : [ "int", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "number",
									"minimum" : 0,
									"fontname" : "Arial",
									"presentation_rect" : [ 357.0, 227.0, 50.0, 20.0 ],
									"patching_rect" : [ 599.0, 462.0, 50.0, 20.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-106",
									"numinlets" : 1,
									"numoutlets" : 2,
									"outlettype" : [ "int", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "wrap $1",
									"fontname" : "Arial",
									"patching_rect" : [ 599.0, 490.0, 53.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-104",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "0",
									"fontname" : "Arial",
									"presentation_rect" : [ 38.0, 71.0, 18.0, 18.0 ],
									"patching_rect" : [ 50.0, 151.0, 18.5, 18.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-27",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "0",
									"fontname" : "Arial",
									"presentation_rect" : [ 627.0, 71.0, 18.0, 18.0 ],
									"patching_rect" : [ 639.0, 151.0, 18.5, 18.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-49",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "prepend speed",
									"fontname" : "Arial",
									"patching_rect" : [ 639.0, 262.0, 91.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-51",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "multislider",
									"candicane6" : [ 0.733333, 0.035294, 0.788235, 1.0 ],
									"candicane5" : [ 0.584314, 0.827451, 0.431373, 1.0 ],
									"presentation_rect" : [ 627.0, 98.0, 298.0, 79.0 ],
									"candycane" : 2,
									"candicane2" : [ 0.145098, 0.203922, 0.356863, 1.0 ],
									"contdata" : 1,
									"candicane4" : [ 0.439216, 0.619608, 0.070588, 1.0 ],
									"patching_rect" : [ 639.0, 178.0, 298.0, 79.0 ],
									"candicane7" : [ 0.878431, 0.243137, 0.145098, 1.0 ],
									"candicane3" : [ 0.290196, 0.411765, 0.713726, 1.0 ],
									"presentation" : 1,
									"id" : "obj-52",
									"numinlets" : 1,
									"signed" : 1,
									"ghostbar" : 20,
									"numoutlets" : 2,
									"peakcolor" : [ 0.498039, 0.498039, 0.498039, 1.0 ],
									"candicane8" : [ 0.027451, 0.447059, 0.501961, 1.0 ],
									"outlettype" : [ "", "" ],
									"size" : 10
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"presentation_rect" : [ 656.0, 230.0, 20.0, 20.0 ],
									"patching_rect" : [ 621.0, 360.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-53",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "wowenabled $1",
									"fontname" : "Arial",
									"patching_rect" : [ 621.0, 406.0, 94.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-54",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "wow $1",
									"fontname" : "Arial",
									"patching_rect" : [ 840.0, 386.0, 51.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-57",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"fontname" : "Arial",
									"triscale" : 0.9,
									"presentation_rect" : [ 648.0, 286.0, 40.0, 20.0 ],
									"htextcolor" : [ 0.243137, 0.0, 0.0, 1.0 ],
									"patching_rect" : [ 840.0, 361.0, 40.0, 20.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-58",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"presentation_rect" : [ 637.0, 184.0, 20.0, 20.0 ],
									"patching_rect" : [ 593.0, 296.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-59",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "harmony $1",
									"fontname" : "Arial",
									"patching_rect" : [ 593.0, 334.0, 74.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-60",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "0",
									"fontname" : "Arial",
									"presentation_rect" : [ 340.0, 71.0, 18.0, 18.0 ],
									"patching_rect" : [ 352.0, 151.0, 18.5, 18.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-61",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "toggle",
									"presentation_rect" : [ 378.0, 184.0, 20.0, 20.0 ],
									"patching_rect" : [ 370.0, 306.0, 20.0, 20.0 ],
									"presentation" : 1,
									"id" : "obj-62",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "int" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "randphase $1",
									"fontname" : "Arial",
									"patching_rect" : [ 370.0, 344.0, 84.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-63",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "prepend phase",
									"fontname" : "Arial",
									"patching_rect" : [ 352.0, 262.0, 91.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-64",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "multislider",
									"candicane6" : [ 0.733333, 0.035294, 0.788235, 1.0 ],
									"candicane5" : [ 0.584314, 0.827451, 0.431373, 1.0 ],
									"presentation_rect" : [ 340.0, 98.0, 284.0, 79.0 ],
									"candycane" : 2,
									"candicane2" : [ 0.145098, 0.203922, 0.356863, 1.0 ],
									"contdata" : 1,
									"candicane4" : [ 0.439216, 0.619608, 0.070588, 1.0 ],
									"patching_rect" : [ 352.0, 178.0, 284.0, 79.0 ],
									"candicane7" : [ 0.878431, 0.243137, 0.145098, 1.0 ],
									"candicane3" : [ 0.290196, 0.411765, 0.713726, 1.0 ],
									"presentation" : 1,
									"id" : "obj-65",
									"numinlets" : 1,
									"signed" : 1,
									"ghostbar" : 20,
									"numoutlets" : 2,
									"peakcolor" : [ 0.498039, 0.498039, 0.498039, 1.0 ],
									"candicane8" : [ 0.027451, 0.447059, 0.501961, 1.0 ],
									"outlettype" : [ "", "" ],
									"size" : 10
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "calc",
									"fontname" : "Arial",
									"presentation_rect" : [ 102.0, 232.0, 32.5, 18.0 ],
									"bgcolor2" : [ 1.0, 0.0, 0.0, 1.0 ],
									"patching_rect" : [ 153.0, 377.0, 32.5, 18.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-67",
									"numinlets" : 2,
									"bgcolor" : [ 0.141176, 0.0, 0.0, 1.0 ],
									"numoutlets" : 1,
									"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "noisiness $1",
									"fontname" : "Arial",
									"patching_rect" : [ 58.0, 455.0, 77.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-70",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "flonum",
									"fontname" : "Arial",
									"triscale" : 0.9,
									"presentation_rect" : [ 72.0, 274.0, 54.0, 20.0 ],
									"htextcolor" : [ 0.243137, 0.0, 0.0, 1.0 ],
									"patching_rect" : [ 58.0, 427.0, 54.0, 20.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-71",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "float", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "number",
									"fontname" : "Arial",
									"triscale" : 0.9,
									"presentation_rect" : [ 77.0, 26.0, 51.0, 20.0 ],
									"htextcolor" : [ 0.870588, 0.870588, 0.870588, 1.0 ],
									"patching_rect" : [ 958.0, 187.0, 51.0, 20.0 ],
									"fontsize" : 12.0,
									"presentation" : 1,
									"id" : "obj-73",
									"numinlets" : 1,
									"bgcolor" : [ 0.866667, 0.866667, 0.866667, 1.0 ],
									"numoutlets" : 2,
									"outlettype" : [ "int", "bang" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "multislider",
									"candicane6" : [ 0.733333, 0.035294, 0.788235, 1.0 ],
									"candicane5" : [ 0.584314, 0.827451, 0.431373, 1.0 ],
									"presentation_rect" : [ 38.0, 98.0, 298.0, 79.0 ],
									"candycane" : 2,
									"candicane2" : [ 0.145098, 0.203922, 0.356863, 1.0 ],
									"contdata" : 1,
									"candicane4" : [ 0.439216, 0.619608, 0.070588, 1.0 ],
									"patching_rect" : [ 50.0, 178.0, 298.0, 79.0 ],
									"candicane7" : [ 0.878431, 0.243137, 0.145098, 1.0 ],
									"candicane3" : [ 0.290196, 0.411765, 0.713726, 1.0 ],
									"presentation" : 1,
									"id" : "obj-75",
									"numinlets" : 1,
									"signed" : 1,
									"ghostbar" : 20,
									"numoutlets" : 2,
									"peakcolor" : [ 0.498039, 0.498039, 0.498039, 1.0 ],
									"candicane8" : [ 0.027451, 0.447059, 0.501961, 1.0 ],
									"outlettype" : [ "", "" ],
									"size" : 10
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "prepend adsyn",
									"fontname" : "Arial",
									"patching_rect" : [ 50.0, 261.0, 90.0, 20.0 ],
									"fontsize" : 12.0,
									"id" : "obj-76",
									"numinlets" : 1,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "message",
									"text" : "sine",
									"fontname" : "Arial",
									"patching_rect" : [ 134.0, 350.0, 33.0, 18.0 ],
									"fontsize" : 12.0,
									"id" : "obj-77",
									"numinlets" : 2,
									"numoutlets" : 1,
									"outlettype" : [ "" ]
								}

							}
, 							{
								"box" : 								{
									"maxclass" : "newobj",
									"text" : "mxj WaveGen waveform waveform2 4096",
									"linecount" : 2,
									"fontname" : "Arial",
									"patching_rect" : [ 341.0, 619.0, 211.0, 48.0 ],
									"fontsize" : 18.0,
									"id" : "obj-81",
									"numinlets" : 1,
									"numoutlets" : 1,
									"color" : [ 0.415686, 0.239216, 0.109804, 1.0 ],
									"outlettype" : [ "" ]
								}

							}
 ],
						"lines" : [ 							{
								"patchline" : 								{
									"source" : [ "obj-21", 0 ],
									"destination" : [ "obj-81", 0 ],
									"hidden" : 0,
									"midpoints" : [ 433.5, 611.0, 350.5, 611.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-16", 0 ],
									"destination" : [ "obj-21", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-89", 0 ],
									"destination" : [ "obj-3", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-81", 0 ],
									"destination" : [ "obj-89", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-86", 1 ],
									"destination" : [ "obj-39", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-86", 0 ],
									"destination" : [ "obj-43", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-85", 0 ],
									"destination" : [ "obj-86", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-87", 0 ],
									"destination" : [ "obj-86", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-83", 1 ],
									"destination" : [ "obj-33", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-83", 0 ],
									"destination" : [ "obj-35", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-84", 0 ],
									"destination" : [ "obj-83", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-82", 0 ],
									"destination" : [ "obj-83", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-79", 0 ],
									"destination" : [ "obj-74", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-74", 0 ],
									"destination" : [ "obj-31", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-74", 1 ],
									"destination" : [ "obj-32", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-72", 0 ],
									"destination" : [ "obj-74", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-15", 1 ],
									"destination" : [ "obj-69", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-46", 0 ],
									"destination" : [ "obj-52", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-39", 0 ],
									"destination" : [ "obj-46", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-43", 0 ],
									"destination" : [ "obj-46", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-37", 0 ],
									"destination" : [ "obj-65", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-35", 0 ],
									"destination" : [ "obj-37", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-33", 0 ],
									"destination" : [ "obj-37", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-52", 0 ],
									"destination" : [ "obj-51", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-49", 0 ],
									"destination" : [ "obj-52", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-32", 0 ],
									"destination" : [ "obj-29", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-31", 0 ],
									"destination" : [ "obj-29", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-29", 0 ],
									"destination" : [ "obj-75", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-73", 0 ],
									"destination" : [ "obj-28", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-12", 0 ],
									"destination" : [ "obj-15", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-15", 0 ],
									"destination" : [ "obj-81", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-17", 0 ],
									"destination" : [ "obj-12", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-13", 0 ],
									"destination" : [ "obj-3", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-3", 0 ],
									"destination" : [ "obj-12", 1 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-81", 0 ],
									"destination" : [ "obj-5", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-7", 0 ],
									"destination" : [ "obj-81", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-91", 0 ],
									"destination" : [ "obj-80", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-10", 0 ],
									"destination" : [ "obj-4", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-61", 0 ],
									"destination" : [ "obj-65", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-27", 0 ],
									"destination" : [ "obj-75", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-62", 0 ],
									"destination" : [ "obj-63", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-59", 0 ],
									"destination" : [ "obj-60", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-53", 0 ],
									"destination" : [ "obj-54", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-58", 0 ],
									"destination" : [ "obj-57", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-106", 0 ],
									"destination" : [ "obj-104", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-71", 0 ],
									"destination" : [ "obj-70", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-65", 0 ],
									"destination" : [ "obj-64", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-75", 0 ],
									"destination" : [ "obj-76", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-5", 0 ],
									"destination" : [ "obj-8", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-38", 0 ],
									"destination" : [ "obj-36", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-105", 0 ],
									"destination" : [ "obj-102", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-102", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 849.5, 523.0, 361.5, 523.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-104", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 608.5, 523.0, 361.5, 523.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-70", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 67.5, 484.0, 361.5, 484.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-36", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 67.5, 407.0, 361.5, 407.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-77", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 143.5, 407.0, 361.5, 407.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-67", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 162.5, 407.0, 361.5, 407.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-57", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 849.5, 449.0, 361.5, 449.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-54", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 630.5, 448.0, 361.5, 448.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-63", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 379.5, 448.0, 361.5, 448.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-60", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 602.5, 448.0, 361.5, 448.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-76", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 59.5, 287.0, 361.5, 287.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-51", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 648.5, 287.0, 361.5, 287.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-64", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-4", 0 ],
									"destination" : [ "obj-26", 0 ],
									"hidden" : 0,
									"midpoints" : [ 379.5, 523.0, 361.5, 523.0 ]
								}

							}
, 							{
								"patchline" : 								{
									"source" : [ "obj-1", 0 ],
									"destination" : [ "obj-73", 0 ],
									"hidden" : 0,
									"midpoints" : [  ]
								}

							}
 ]
					}
,
					"saved_object_attributes" : 					{
						"fontname" : "Arial",
						"default_fontface" : 0,
						"default_fontname" : "Arial",
						"fontface" : 0,
						"fontsize" : 12.0,
						"default_fontsize" : 12.0,
						"globalpatchername" : ""
					}

				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "notein",
					"fontname" : "Arial",
					"patching_rect" : [ 17.0, 21.0, 93.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-37",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "int", "int" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"minimum" : 1.0,
					"fontname" : "Arial",
					"maximum" : 50.0,
					"patching_rect" : [ 78.0, 223.0, 50.0, 20.0 ],
					"fontsize" : 12.0,
					"id" : "obj-35",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "spectroscope~",
					"presentation_rect" : [ 118.0, 490.0, 199.0, 122.0 ],
					"patching_rect" : [ 118.0, 490.0, 199.0, 122.0 ],
					"presentation" : 1,
					"id" : "obj-158",
					"numinlets" : 2,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "ezdac~",
					"presentation_rect" : [ 17.0, 624.0, 45.0, 45.0 ],
					"patching_rect" : [ 17.0, 624.0, 45.0, 45.0 ],
					"presentation" : 1,
					"id" : "obj-101",
					"numinlets" : 2,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "gain~",
					"presentation_rect" : [ 17.0, 490.0, 22.0, 122.0 ],
					"patching_rect" : [ 17.0, 490.0, 22.0, 122.0 ],
					"presentation" : 1,
					"id" : "obj-31",
					"numinlets" : 2,
					"orientation" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "signal", "int" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "bargraf~",
					"fontname" : "Arial",
					"presentation_rect" : [ 38.0, 490.0, 24.0, 122.0 ],
					"patching_rect" : [ 38.0, 490.0, 24.0, 122.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-32",
					"numinlets" : 2,
					"border" : 0,
					"numoutlets" : 3,
					"frame" : 0,
					"outlettype" : [ "float", "float", "float" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "dry",
					"fontname" : "Arial",
					"presentation_rect" : [ 279.0, 390.0, 29.0, 20.0 ],
					"patching_rect" : [ 279.0, 390.0, 29.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-93",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "wet",
					"fontname" : "Arial",
					"presentation_rect" : [ 236.0, 390.0, 29.0, 20.0 ],
					"patching_rect" : [ 236.0, 390.0, 29.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-92",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "width",
					"fontname" : "Arial",
					"presentation_rect" : [ 193.0, 390.0, 39.0, 20.0 ],
					"patching_rect" : [ 193.0, 390.0, 39.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-91",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "damp",
					"fontname" : "Arial",
					"presentation_rect" : [ 150.0, 390.0, 41.0, 20.0 ],
					"patching_rect" : [ 150.0, 390.0, 41.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-90",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "room",
					"fontname" : "Arial",
					"presentation_rect" : [ 107.0, 390.0, 38.0, 20.0 ],
					"patching_rect" : [ 107.0, 390.0, 38.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-89",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "--------------- autopan --------------",
					"fontname" : "Arial",
					"presentation_rect" : [ 785.0, 26.5, 176.0, 20.0 ],
					"patching_rect" : [ 785.0, 26.5, 176.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"fontsize" : 12.0,
					"presentation" : 1,
					"id" : "obj-26",
					"numinlets" : 1,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "translate @in notevalues @out ms",
					"linecount" : 4,
					"fontname" : "Arial",
					"patching_rect" : [ 749.0, 474.0, 69.0, 62.0 ],
					"fontsize" : 12.0,
					"id" : "obj-24",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "translate @in notevalues @out ms",
					"linecount" : 4,
					"fontname" : "Arial",
					"patching_rect" : [ 651.0, 474.0, 69.0, 62.0 ],
					"fontsize" : 12.0,
					"id" : "obj-50",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "translate @in notevalues @out ms",
					"linecount" : 4,
					"fontname" : "Arial",
					"patching_rect" : [ 487.0, 474.0, 69.0, 62.0 ],
					"fontsize" : 12.0,
					"id" : "obj-6",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "translate @in notevalues @out ms",
					"linecount" : 4,
					"fontname" : "Arial",
					"patching_rect" : [ 854.0, 474.0, 69.0, 62.0 ],
					"fontsize" : 12.0,
					"id" : "obj-77",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"source" : [ "obj-77", 0 ],
					"destination" : [ "obj-80", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-78", 1 ],
					"destination" : [ "obj-77", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-8", 1 ],
					"destination" : [ "obj-6", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-6", 0 ],
					"destination" : [ "obj-128", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-51", 1 ],
					"destination" : [ "obj-50", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-50", 0 ],
					"destination" : [ "obj-49", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-24", 0 ],
					"destination" : [ "obj-13", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-25", 1 ],
					"destination" : [ "obj-24", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-109", 0 ],
					"destination" : [ "obj-8", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-122", 0 ],
					"destination" : [ "obj-113", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-113", 0 ],
					"destination" : [ "obj-109", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-120", 0 ],
					"destination" : [ "obj-118", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-118", 0 ],
					"destination" : [ "obj-110", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-110", 2 ],
					"destination" : [ "obj-113", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-109", 0 ],
					"destination" : [ "obj-78", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-109", 0 ],
					"destination" : [ "obj-25", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-109", 0 ],
					"destination" : [ "obj-51", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-107", 0 ],
					"destination" : [ "obj-64", 8 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-106", 0 ],
					"destination" : [ "obj-64", 7 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-103", 0 ],
					"destination" : [ "obj-7", 11 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-63", 0 ],
					"destination" : [ "obj-7", 14 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-62", 0 ],
					"destination" : [ "obj-7", 13 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-83", 0 ],
					"destination" : [ "obj-7", 12 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-7", 1 ],
					"destination" : [ "obj-44", 1 ],
					"hidden" : 0,
					"midpoints" : [ 476.5, 159.0, 57.0, 159.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-7", 2 ],
					"destination" : [ "obj-45", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-85", 0 ],
					"destination" : [ "obj-7", 0 ],
					"hidden" : 0,
					"midpoints" : [ 73.5, 127.0, 26.5, 127.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-85", 1 ],
					"destination" : [ "obj-7", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-130", 0 ],
					"destination" : [ "obj-7", 9 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-82", 0 ],
					"destination" : [ "obj-7", 7 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-56", 0 ],
					"destination" : [ "obj-7", 6 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-40", 0 ],
					"destination" : [ "obj-7", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-12", 0 ],
					"destination" : [ "obj-7", 2 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-19", 0 ],
					"destination" : [ "obj-7", 5 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-5", 0 ],
					"destination" : [ "obj-7", 4 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-11", 0 ],
					"destination" : [ "obj-7", 3 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-17", 0 ],
					"destination" : [ "obj-7", 8 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-7", 0 ],
					"destination" : [ "obj-44", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-55", 0 ],
					"destination" : [ "obj-7", 10 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-13", 0 ],
					"destination" : [ "obj-10", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-10", 0 ],
					"destination" : [ "obj-1", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-75", 0 ],
					"destination" : [ "obj-76", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-49", 0 ],
					"destination" : [ "obj-75", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-84", 0 ],
					"destination" : [ "obj-64", 6 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-46", 0 ],
					"destination" : [ "obj-64", 5 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-47", 0 ],
					"destination" : [ "obj-64", 4 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-43", 0 ],
					"destination" : [ "obj-64", 3 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-27", 0 ],
					"destination" : [ "obj-64", 2 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-65", 0 ],
					"destination" : [ "obj-101", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-31", 0 ],
					"destination" : [ "obj-32", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-88", 0 ],
					"destination" : [ "obj-85", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-79", 0 ],
					"destination" : [ "obj-29", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-73", 0 ],
					"destination" : [ "obj-35", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-72", 0 ],
					"destination" : [ "obj-67", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-69", 0 ],
					"destination" : [ "obj-71", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-80", 0 ],
					"destination" : [ "obj-69", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-67", 0 ],
					"destination" : [ "obj-66", 2 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-58", 0 ],
					"destination" : [ "obj-66", 4 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-86", 0 ],
					"destination" : [ "obj-66", 3 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-9", 0 ],
					"destination" : [ "obj-66", 5 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-128", 0 ],
					"destination" : [ "obj-4", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-29", 0 ],
					"destination" : [ "obj-56", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-21", 0 ],
					"destination" : [ "obj-19", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-23", 0 ],
					"destination" : [ "obj-12", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-37", 1 ],
					"destination" : [ "obj-38", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-37", 0 ],
					"destination" : [ "obj-38", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-38", 0 ],
					"destination" : [ "obj-40", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-31", 0 ],
					"destination" : [ "obj-101", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-31", 0 ],
					"destination" : [ "obj-158", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-18", 0 ],
					"destination" : [ "obj-5", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-22", 0 ],
					"destination" : [ "obj-11", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-41", 0 ],
					"destination" : [ "obj-17", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-44", 0 ],
					"destination" : [ "obj-66", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-35", 0 ],
					"destination" : [ "obj-44", 2 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-52", 0 ],
					"destination" : [ "obj-55", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-53", 0 ],
					"destination" : [ "obj-54", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-44", 1 ],
					"destination" : [ "obj-66", 1 ],
					"hidden" : 0,
					"midpoints" : [ 87.5, 326.0, 78.300003, 326.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-66", 0 ],
					"destination" : [ "obj-64", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-66", 1 ],
					"destination" : [ "obj-64", 1 ],
					"hidden" : 0,
					"midpoints" : [ 285.5, 369.0, 69.5, 369.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-65", 0 ],
					"destination" : [ "obj-70", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-64", 0 ],
					"destination" : [ "obj-31", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-31", 1 ],
					"destination" : [ "obj-65", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-64", 1 ],
					"destination" : [ "obj-65", 0 ],
					"hidden" : 0,
					"midpoints" : [ 370.5, 480.0, 74.0, 480.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-54", 0 ],
					"destination" : [ "obj-83", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
 ]
	}

}
