package snyakovlev.unote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import static org.apache.poi.ss.usermodel.CellType._NONE;
import static org.apache.poi.ss.usermodel.CellType.ERROR;


class PDFAdapter
    {
        PdfRenderer pdfRenderer;
        PdfRenderer.Page curPage;
        ParcelFileDescriptor descriptor;
        int count;
        String path;
        Context ctx;

        public PDFAdapter(Context ctx, String path) {
            this.path=path;
            this.ctx=ctx;
            openPdfRenderer();
        }

        int openPdfRenderer(){
            File file = new File(path);
            descriptor = null;
            pdfRenderer = null;
            try {
                descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
                pdfRenderer = new PdfRenderer(descriptor);
            } catch (Exception e) {
                Toast.makeText(ctx, "There's some error", Toast.LENGTH_LONG).show();
            }

           return pdfRenderer.getPageCount();

        }



        private void closePdfRenderer() throws IOException {
            if (curPage != null)
                curPage.close();
            if (pdfRenderer != null)
                pdfRenderer.close();
            if(descriptor !=null)
                descriptor.close();
        }

        Bitmap Pdf2Bitmap(int index){



            if(pdfRenderer.getPageCount() <= index)
                return null;
            //close the current page
           // if(curPage != null)
            //    curPage.close();
            //open the specified page
            curPage = pdfRenderer.openPage(index);
            //get page width in points(1/72")
            int pageWidth = curPage.getWidth();
            //get page height in points(1/72")
            int pageHeight = curPage.getHeight();
            //returns a mutable bitmap
            Bitmap bitmap = Bitmap.createBitmap(pageWidth, pageHeight, Bitmap.Config.ARGB_8888);
            //render the page on bitmap
            curPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            //display the bitmap
            try {
                closePdfRenderer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }




