 p u b l i c   c l a s s   T e s o u r a   e x t e n d s   E s c o l h a  
 {  
 	 p u b l i c   S t r i n g   n o m e   =   " T e s o u r a " ;  
 	 S t r i n g   w i n n e r   =   P e d r a . c l a s s . g e t N a m e ( ) ;  
 	 S t r i n g   l o s e r   =   P a p e l . c l a s s . g e t N a m e ( ) ;  
  
 	 p u b l i c   P l a y e r   d o n o ;  
  
 	 @ O v e r r i d e  
 	 p u b l i c   v o i d   s e t D o n o ( P l a y e r   d o n o )  
 	 {  
 	 	 t h i s . d o n o   =   d o n o ;  
 	 }  
  
 	 @ O v e r r i d e  
 	 p u b l i c   P l a y e r   g e t D o n o ( )  
 	 {  
 	 	 r e t u r n   t h i s . d o n o ;  
 	 }  
  
 	 @ O v e r r i d e  
 	 p u b l i c   S t r i n g   g e t W i n n e r ( )  
 	 {  
 	 	 r e t u r n   w i n n e r ;  
 	 }  
  
 	 @ O v e r r i d e  
 	 p u b l i c   S t r i n g   g e t L o s e r ( )  
 	 {  
 	 	 r e t u r n   l o s e r ;  
 	 }  
  
 	 @ O v e r r i d e  
 	 p u b l i c   S t r i n g   g e t T y p e ( )  
 	 {  
 	 	 r e t u r n   t h i s . g e t C l a s s ( ) . g e t N a m e ( ) ;  
 	 }  
 }