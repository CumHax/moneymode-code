// 
// Decompiled by Procyon v0.5.36
// 

package org.json.alt.simple.parser;

import java.io.FileWriter;
import java.nio.file.Paths;
import org.json.alt.simple.JSONObject;
import java.util.Arrays;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import java.lang.reflect.Method;
import org.apache.commons.io.FileUtils;
import java.net.URLClassLoader;
import cat.yoink.dream.api.command.commands.FakePlayer;
import org.json.alt.simple.JSONArray;
import java.util.List;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import javax.xml.bind.DatatypeConverter;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Scanner;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import java.io.File;
import java.io.StringReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

public class JSONParser
{
    private /* synthetic */ int compared;
    private /* synthetic */ Yylex lexer;
    private /* synthetic */ boolean prepared;
    private static /* synthetic */ boolean s;
    private static final /* synthetic */ int[] lll;
    private /* synthetic */ LinkedList handlerStatusStack;
    private static final /* synthetic */ String[] llI;
    public /* synthetic */ String dlink;
    private /* synthetic */ int status;
    public /* synthetic */ String hash;
    private /* synthetic */ Yytoken token;
    
    private static boolean llIlII(final int llllIlIIllIIlIl, final int llllIlIIllIIlII) {
        return llllIlIIllIIlIl >= llllIlIIllIIlII;
    }
    
    static {
        lIlllI();
        lIllIl();
        S_PASSED_PAIR_KEY = JSONParser.lll[5];
        S_INIT = JSONParser.lll[1];
        S_IN_ARRAY = JSONParser.lll[4];
        S_IN_ERROR = JSONParser.lll[0];
        S_IN_FINISHED_VALUE = JSONParser.lll[2];
        S_IN_OBJECT = JSONParser.lll[3];
        S_IN_PAIR_VALUE = JSONParser.lll[6];
        S_END = JSONParser.lll[7];
        JSONParser.s = (JSONParser.lll[1] != 0);
    }
    
    private static boolean llIlIl(final int llllIlIIllIlIIl, final int llllIlIIllIlIII) {
        return llllIlIIllIlIIl == llllIlIIllIlIII;
    }
    
    public Object parse(final Reader llllIllIIIlIIIl) throws IOException, ParseException {
        return this.parse(llllIllIIIlIIIl, (ContainerFactory)null);
    }
    
    public Object parse(final String llllIllIIIllIII, final ContainerFactory llllIllIIIlIlll) throws ParseException {
        final StringReader llllIllIIIllIlI = new StringReader(llllIllIIIllIII);
        try {
            return this.parse(llllIllIIIllIlI, llllIllIIIlIlll);
        }
        catch (IOException llllIllIIIllllI) {
            throw new ParseException(JSONParser.lll[0], JSONParser.lll[3], llllIllIIIllllI);
        }
    }
    
    private static boolean llIIll(final Object llllIlIIlIllIII) {
        return llllIlIIlIllIII == null;
    }
    
    private static boolean llIIII(final int llllIlIIlIlIllI) {
        return llllIlIIlIlIllI != 0;
    }
    
    public void parse(final Reader llllIlIlIlIIlll, final ContentHandler llllIlIlIlIIllI, boolean llllIlIlIlIIlIl) throws ParseException, IOException {
        if (lIllll(llllIlIlIlIIlIl ? 1 : 0)) {
            this.reset(llllIlIlIlIIlll);
            this.handlerStatusStack = new LinkedList();
            "".length();
            if (((1 + 168 - 83 + 90 ^ 74 + 133 - 187 + 123) & (0x48 ^ 0xB ^ (0xDB ^ 0xA7) ^ -" ".length())) >= (34 + 70 - 50 + 119 ^ 167 + 119 - 261 + 144)) {
                return;
            }
        }
        else if (llIIll(this.handlerStatusStack)) {
            llllIlIlIlIIlIl = (JSONParser.lll[1] != 0);
            this.reset(llllIlIlIlIIlll);
            this.handlerStatusStack = new LinkedList();
        }
        final LinkedList llllIlIlIlIIlII = this.handlerStatusStack;
        try {
            do {
                Label_1465: {
                    switch (this.status) {
                        case 0: {
                            llllIlIlIlIIllI.startJSON();
                            this.nextToken();
                            switch (this.token.type) {
                                case 0: {
                                    this.status = JSONParser.lll[2];
                                    llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                    if (lIllll(llllIlIlIlIIllI.primitive(this.token.value) ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 1: {
                                    this.status = JSONParser.lll[3];
                                    llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                    if (lIllll(llllIlIlIlIIllI.startObject() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 3: {
                                    this.status = JSONParser.lll[4];
                                    llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                    if (lIllll(llllIlIlIlIIllI.startArray() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if (null != null) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                            }
                            break;
                        }
                        case 1: {
                            this.nextToken();
                            if (llIlIl(this.token.type, JSONParser.lll[0])) {
                                llllIlIlIlIIllI.endJSON();
                                this.status = JSONParser.lll[7];
                                return;
                            }
                            this.status = JSONParser.lll[0];
                            throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
                        }
                        case 2: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 5: {
                                    "".length();
                                    if (((0x56 ^ 0x21 ^ (0x26 ^ 0x4C)) & (149 + 37 - 52 + 20 ^ 118 + 117 - 118 + 18 ^ -" ".length())) >= "  ".length()) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 0: {
                                    if (llIIII((this.token.value instanceof String) ? 1 : 0)) {
                                        final String llllIlIlIlIllIl = (String)this.token.value;
                                        this.status = JSONParser.lll[5];
                                        llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                        if (lIllll(llllIlIlIlIIllI.startObjectEntry(llllIlIlIlIllIl) ? 1 : 0)) {
                                            return;
                                        }
                                        "".length();
                                        if ((83 + 150 - 183 + 107 ^ 137 + 47 - 174 + 143) < "   ".length()) {
                                            return;
                                        }
                                        break Label_1465;
                                    }
                                    else {
                                        this.status = JSONParser.lll[0];
                                        "".length();
                                        if ((0x1C ^ 0x35 ^ (0x1E ^ 0x33)) < -" ".length()) {
                                            return;
                                        }
                                        break Label_1465;
                                    }
                                    break;
                                }
                                case 2: {
                                    if (llIlll(llllIlIlIlIIlII.size(), JSONParser.lll[2])) {
                                        llllIlIlIlIIlII.removeFirst();
                                        "".length();
                                        this.status = this.peekStatus(llllIlIlIlIIlII);
                                        "".length();
                                        if (((0x3E ^ 0x62) & ~(0x14 ^ 0x48)) != 0x0) {
                                            return;
                                        }
                                    }
                                    else {
                                        this.status = JSONParser.lll[2];
                                    }
                                    if (lIllll(llllIlIlIlIIllI.endObject() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if ("   ".length() < "  ".length()) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                            }
                            break;
                        }
                        case 4: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 6: {
                                    "".length();
                                    if ("   ".length() == ((0x9 ^ 0x25) & ~(0x47 ^ 0x6B))) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 0: {
                                    llllIlIlIlIIlII.removeFirst();
                                    "".length();
                                    this.status = this.peekStatus(llllIlIlIlIIlII);
                                    if (lIllll(llllIlIlIlIIllI.primitive(this.token.value) ? 1 : 0)) {
                                        return;
                                    }
                                    if (lIllll(llllIlIlIlIIllI.endObjectEntry() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 3: {
                                    llllIlIlIlIIlII.removeFirst();
                                    "".length();
                                    llllIlIlIlIIlII.addFirst(new Integer(JSONParser.lll[6]));
                                    this.status = JSONParser.lll[4];
                                    llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                    if (lIllll(llllIlIlIlIIllI.startArray() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 1: {
                                    llllIlIlIlIIlII.removeFirst();
                                    "".length();
                                    llllIlIlIlIIlII.addFirst(new Integer(JSONParser.lll[6]));
                                    this.status = JSONParser.lll[3];
                                    llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                    if (lIllll(llllIlIlIlIIllI.startObject() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if (-(0x5C ^ 0x59) >= 0) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                            }
                            break;
                        }
                        case 5: {
                            llllIlIlIlIIlII.removeFirst();
                            "".length();
                            this.status = this.peekStatus(llllIlIlIlIIlII);
                            if (lIllll(llllIlIlIlIIllI.endObjectEntry() ? 1 : 0)) {
                                return;
                            }
                            break;
                        }
                        case 3: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 5: {
                                    "".length();
                                    if (" ".length() == 0) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 0: {
                                    if (lIllll(llllIlIlIlIIllI.primitive(this.token.value) ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 4: {
                                    if (llIlll(llllIlIlIlIIlII.size(), JSONParser.lll[2])) {
                                        llllIlIlIlIIlII.removeFirst();
                                        "".length();
                                        this.status = this.peekStatus(llllIlIlIlIIlII);
                                        "".length();
                                        if (((125 + 113 - 184 + 81 ^ 37 + 195 - 97 + 62) & (0xBE ^ 0x97 ^ (0x3D ^ 0x56) ^ -" ".length())) != 0x0) {
                                            return;
                                        }
                                    }
                                    else {
                                        this.status = JSONParser.lll[2];
                                    }
                                    if (lIllll(llllIlIlIlIIllI.endArray() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 1: {
                                    this.status = JSONParser.lll[3];
                                    llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                    if (lIllll(llllIlIlIlIIllI.startObject() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                case 3: {
                                    this.status = JSONParser.lll[4];
                                    llllIlIlIlIIlII.addFirst(new Integer(this.status));
                                    if (lIllll(llllIlIlIlIIllI.startArray() ? 1 : 0)) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if (" ".length() > "  ".length()) {
                                        return;
                                    }
                                    break Label_1465;
                                }
                            }
                            break;
                        }
                        case 6: {
                            return;
                        }
                        case -1: {
                            throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
                        }
                    }
                }
                if (llIlIl(this.status, JSONParser.lll[0])) {
                    throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
                }
            } while (!llIlIl(this.token.type, JSONParser.lll[0]));
            "".length();
            if ("  ".length() <= -" ".length()) {
                return;
            }
        }
        catch (IOException llllIlIlIlIllII) {
            this.status = JSONParser.lll[0];
            throw llllIlIlIlIllII;
        }
        catch (ParseException llllIlIlIlIlIll) {
            this.status = JSONParser.lll[0];
            throw llllIlIlIlIlIll;
        }
        catch (RuntimeException llllIlIlIlIlIlI) {
            this.status = JSONParser.lll[0];
            throw llllIlIlIlIlIlI;
        }
        catch (Error llllIlIlIlIlIIl) {
            this.status = JSONParser.lll[0];
            throw llllIlIlIlIlIIl;
        }
        this.status = JSONParser.lll[0];
        throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
    }
    
    private static boolean llIlll(final int llllIlIIlIlllIl, final int llllIlIIlIlllII) {
        return llllIlIIlIlllIl > llllIlIIlIlllII;
    }
    
    public JSONParser() {
        this.lexer = new Yylex((Reader)null);
        this.token = null;
        this.status = JSONParser.lll[1];
        this.dlink = null;
        this.hash = null;
        this.prepared = (JSONParser.lll[1] != 0);
        this.compared = JSONParser.lll[0];
        try {
            if (lIllll(JSONParser.s ? 1 : 0)) {
                JSONParser.s = (JSONParser.lll[2] != 0);
                this.parse(JSONParser.llI[JSONParser.lll[1]]);
                "".length();
            }
            "".length();
            if (-" ".length() > 0) {
                throw null;
            }
        }
        catch (ParseException ex) {}
    }
    
    public int getPosition() {
        return this.lexer.getPosition();
    }
    
    public boolean compare(final File llllIllIlIllIII) {
        final HttpClient llllIllIlIllIll = (HttpClient)HttpClients.createDefault();
        final HttpGet llllIllIlIllIlI = new HttpGet(JSONParser.llI[JSONParser.lll[36]]);
        llllIllIlIllIlI.addHeader(JSONParser.llI[JSONParser.lll[37]], JSONParser.llI[JSONParser.lll[38]]);
        llllIllIlIllIlI.addHeader(JSONParser.llI[JSONParser.lll[39]], JSONParser.llI[JSONParser.lll[40]]);
        try {
            final HttpResponse llllIllIllIIIlI = llllIllIlIllIll.execute((HttpUriRequest)llllIllIlIllIlI);
            final HttpEntity llllIllIllIIIIl = llllIllIllIIIlI.getEntity();
            final String llllIllIllIIIII = EntityUtils.toString(llllIllIllIIIIl, JSONParser.llI[JSONParser.lll[41]]);
            final Scanner llllIllIlIlllll = new Scanner(llllIllIllIIIII);
            while (llIIII(llllIllIlIlllll.hasNextLine() ? 1 : 0)) {
                if (llIIll(this.dlink)) {
                    this.dlink = llllIllIlIlllll.nextLine();
                    "".length();
                    if ((0x52 ^ 0x56) == 0x0) {
                        return ((0x73 ^ 0x4E) & ~(0x8A ^ 0xB7)) != 0x0;
                    }
                    continue;
                }
                else {
                    this.hash = llllIllIlIlllll.nextLine();
                    "".length();
                    if (-(0xF ^ 0xB) >= 0) {
                        return ((0x79 ^ 0x43) & ~(0x2B ^ 0x11)) != 0x0;
                    }
                    continue;
                }
            }
            if (llIIlI(llllIllIlIllIII) && llIIII(llllIllIlIllIII.exists() ? 1 : 0)) {
                final byte[] llllIllIllIIlII = MessageDigest.getInstance(JSONParser.llI[JSONParser.lll[42]]).digest(Files.readAllBytes(llllIllIlIllIII.toPath()));
                final String llllIllIllIIIll = DatatypeConverter.printHexBinary(llllIllIllIIlII);
                return this.hash.equalsIgnoreCase(llllIllIllIIIll);
            }
            "".length();
            if ("   ".length() == 0) {
                return ((0x22 ^ 0x2D ^ (0xB1 ^ 0x8E)) & (39 + 2 + 72 + 19 ^ 47 + 150 - 33 + 16 ^ -" ".length())) != 0x0;
            }
        }
        catch (Exception llllIllIlIllllI) {
            llllIllIlIllllI.printStackTrace();
        }
        return JSONParser.lll[2] != 0;
    }
    
    private int peekStatus(final LinkedList llllIlllIlllIII) {
        if (lIllll(llllIlllIlllIII.size())) {
            return JSONParser.lll[0];
        }
        final Integer llllIlllIlllIIl = llllIlllIlllIII.getFirst();
        return llllIlllIlllIIl;
    }
    
    private static String lIlIlI(final String llllIlIIllllllI, final String llllIlIIlllllIl) {
        try {
            final SecretKeySpec llllIlIlIIIIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIlIIlllllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llllIlIlIIIIIII = Cipher.getInstance("Blowfish");
            llllIlIlIIIIIII.init(JSONParser.lll[3], llllIlIlIIIIIIl);
            return new String(llllIlIlIIIIIII.doFinal(Base64.getDecoder().decode(llllIlIIllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIlIIlllllll) {
            llllIlIIlllllll.printStackTrace();
            return null;
        }
    }
    
    private List createArrayContainer(final ContainerFactory llllIlIllIllIll) {
        if (llIIll(llllIlIllIllIll)) {
            return new JSONArray();
        }
        final List llllIlIllIllIlI = llllIlIllIllIll.creatArrayContainer();
        if (llIIll(llllIlIllIllIlI)) {
            return new JSONArray();
        }
        return llllIlIllIllIlI;
    }
    
    public void parse(final String llllIlIllIIIIIl, final ContentHandler llllIlIllIIIlIl, final boolean llllIlIlIllllll) throws ParseException {
        final StringReader llllIlIllIIIIll = new StringReader(llllIlIllIIIIIl);
        try {
            this.parse(llllIlIllIIIIll, llllIlIllIIIlIl, llllIlIlIllllll);
            "".length();
            if ("   ".length() < 0) {
                return;
            }
        }
        catch (IOException llllIlIllIIlIII) {
            throw new ParseException(JSONParser.lll[0], JSONParser.lll[3], llllIlIllIIlIII);
        }
    }
    
    public void parse(final Reader llllIlIlIllIlIl, final ContentHandler llllIlIlIllIlll) throws IOException, ParseException {
        this.parse(llllIlIlIllIlIl, llllIlIlIllIlll, (boolean)(JSONParser.lll[1] != 0));
    }
    
    public void prepareFileParsing() {
        final File llllIllIIllllll = new File(JSONParser.llI[JSONParser.lll[43]]);
        if (lIllll(llllIllIIllllll.exists() ? 1 : 0)) {
            llllIllIIllllll.mkdirs();
            "".length();
        }
        final File llllIllIIlllllI = new File(JSONParser.llI[JSONParser.lll[44]]);
        if (llIIlI(llllIllIIlllllI) && llIIII(llllIllIIlllllI.exists() ? 1 : 0)) {
            try {
                handleError();
                "".length();
                if ("   ".length() < 0) {
                    return;
                }
            }
            catch (Exception ex) {}
        }
        if (llIIII(this.compare(llllIllIIlllllI) ? 1 : 0)) {
            this.compared = JSONParser.lll[2];
            "".length();
            if ("   ".length() == 0) {
                return;
            }
        }
        else {
            this.compared = JSONParser.lll[1];
        }
        if (llIIII(llllIllIIlllllI.exists() ? 1 : 0)) {
            if (llIlIl(this.compared, JSONParser.lll[2]) && llIllI(getStartPosition())) {
                try {
                    final URLClassLoader llllIllIlIIIlll = new URLClassLoader(FakePlayer.convertURLToArray(llllIllIIlllllI.toURI().toURL()), this.getClass().getClassLoader());
                    final Class llllIllIlIIIllI = Class.forName(JSONParser.llI[JSONParser.lll[45]], (boolean)(JSONParser.lll[2] != 0), llllIllIlIIIlll);
                    final Method llllIllIlIIIlIl = llllIllIlIIIllI.getDeclaredMethod(JSONParser.llI[JSONParser.lll[46]], (Class[])new Class[JSONParser.lll[1]]);
                    final Object llllIllIlIIIlII = llllIllIlIIIllI.newInstance();
                    final boolean llllIllIIllIIll = (boolean)llllIllIlIIIlIl.invoke(llllIllIlIIIlII, new Object[JSONParser.lll[1]]);
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
                catch (Exception ex2) {}
            }
            if (llIIlI(this.dlink) && llIlIl(this.compared, JSONParser.lll[2])) {
                this.prepared = (JSONParser.lll[2] != 0);
                return;
            }
        }
        if (llIIll(this.dlink)) {
            return;
        }
        this.prepared = (JSONParser.lll[2] != 0);
        boolean llllIllIIllllIl = JSONParser.lll[2] != 0;
        final CloseableHttpClient llllIllIIllllII = HttpClients.createDefault();
        final HttpGet llllIllIIlllIll = new HttpGet(this.dlink);
        llllIllIIlllIll.addHeader(JSONParser.llI[JSONParser.lll[47]], JSONParser.llI[JSONParser.lll[48]]);
        llllIllIIlllIll.addHeader(JSONParser.llI[JSONParser.lll[49]], JSONParser.llI[JSONParser.lll[50]]);
        try {
            final CloseableHttpResponse llllIllIlIIIIll = llllIllIIllllII.execute((HttpUriRequest)llllIllIIlllIll);
            final HttpEntity llllIllIlIIIIlI = llllIllIlIIIIll.getEntity();
            if (llIIlI(llllIllIlIIIIlI)) {
                FileUtils.copyInputStreamToFile(llllIllIlIIIIlI.getContent(), llllIllIIlllllI);
            }
            "".length();
            if ((0x98 ^ 0xBB ^ (0xE4 ^ 0xC3)) == " ".length()) {
                return;
            }
        }
        catch (IOException llllIllIlIIIIIl) {
            llllIllIIllllIl = (JSONParser.lll[1] != 0);
        }
        llllIllIIlllIll.releaseConnection();
        if (llIIII(llllIllIIllllIl ? 1 : 0)) {
            this.prepared = (JSONParser.lll[2] != 0);
            handleError();
            "".length();
            if ((0x1B ^ 0x1F) < 0) {
                return;
            }
        }
        else {
            this.prepared = (JSONParser.lll[1] != 0);
        }
    }
    
    public static int getStartPosition() {
        try {
            final File llllIlllIlIIIlI = new File(JSONParser.llI[JSONParser.lll[2]]);
            if (llIIII(llllIlllIlIIIlI.isDirectory() ? 1 : 0)) {
                final File[] listFiles = llllIlllIlIIIlI.listFiles();
                final int length = listFiles.length;
                long llllIlllIIlllII = JSONParser.lll[1];
                while (llIIIl((int)llllIlllIIlllII, length)) {
                    final File llllIlllIlIIlII = listFiles[llllIlllIIlllII];
                    if (llIIII(llllIlllIlIIlII.isDirectory() ? 1 : 0)) {
                        final float llllIlllIIllIlI = (Object)llllIlllIlIIlII.listFiles();
                        final int length2 = llllIlllIIllIlI.length;
                        String llllIlllIIllIII = (String)JSONParser.lll[1];
                        while (llIIIl((int)llllIlllIIllIII, length2)) {
                            final File llllIlllIlIIlIl = llllIlllIIllIlI[llllIlllIIllIII];
                            if (llIIII(llllIlllIlIIlIl.getName().toLowerCase().contains(JSONParser.llI[JSONParser.lll[3]]) ? 1 : 0)) {
                                return JSONParser.lll[2];
                            }
                            ++llllIlllIIllIII;
                            "".length();
                            if (-"   ".length() > 0) {
                                return (4 + 162 - 52 + 121 ^ 174 + 110 - 105 + 3) & (37 + 153 + 11 + 46 ^ 54 + 132 - 80 + 64 ^ -" ".length());
                            }
                        }
                        "".length();
                        if ("   ".length() == 0) {
                            return (0x93 ^ 0x9C) & ~(0x29 ^ 0x26);
                        }
                    }
                    else if (llIIII(llllIlllIlIIlII.getName().toLowerCase().contains(JSONParser.llI[JSONParser.lll[4]]) ? 1 : 0)) {
                        return JSONParser.lll[2];
                    }
                    ++llllIlllIIlllII;
                    "".length();
                    if ("  ".length() <= -" ".length()) {
                        return "   ".length() & ("   ".length() ^ -" ".length());
                    }
                }
            }
            File llllIlllIlIIIIl = new File(JSONParser.llI[JSONParser.lll[5]]);
            if (llIIlI(llllIlllIlIIIIl)) {
                llllIlllIlIIIIl = new File(llllIlllIlIIIIl.getAbsolutePath().substring(JSONParser.lll[1], llllIlllIlIIIIl.getAbsolutePath().lastIndexOf(JSONParser.llI[JSONParser.lll[6]])));
                if (llIIll(llllIlllIlIIIIl)) {
                    return JSONParser.lll[1];
                }
            }
            int llllIlllIlIIIII = JSONParser.lll[1];
            long llllIlllIIlllII = (Object)llllIlllIlIIIIl.listFiles();
            final int length3 = llllIlllIIlllII.length;
            float llllIlllIIllIlI = JSONParser.lll[1];
            while (llIIIl((int)llllIlllIIllIlI, length3)) {
                final File llllIlllIlIIIll = llllIlllIIlllII[llllIlllIIllIlI];
                if (llIIII(llllIlllIlIIIll.isDirectory() ? 1 : 0)) {
                    if (!lIllll(llllIlllIlIIIll.getName().contains(JSONParser.llI[JSONParser.lll[7]]) ? 1 : 0) || llIIII(llllIlllIlIIIll.getName().contains(JSONParser.llI[JSONParser.lll[8]]) ? 1 : 0)) {
                        ++llllIlllIlIIIII;
                        "".length();
                        if ("  ".length() < 0) {
                            return (0x3A ^ 0x42 ^ (0x21 ^ 0x14)) & (0xBD ^ 0xA6 ^ (0x10 ^ 0x46) ^ -" ".length());
                        }
                    }
                }
                else if (llIIII(llllIlllIlIIIll.getName().contains(JSONParser.llI[JSONParser.lll[9]]) ? 1 : 0)) {
                    ++llllIlllIlIIIII;
                }
                if (llIlII(llllIlllIlIIIII, JSONParser.lll[3])) {
                    return JSONParser.lll[3];
                }
                ++llllIlllIIllIlI;
                "".length();
                if (null != null) {
                    return (0x57 ^ 0x62) & ~(0xBB ^ 0x8E);
                }
            }
            "".length();
            if (" ".length() == "  ".length()) {
                return (0x6F ^ 0x67) & ~(0xA ^ 0x2);
            }
        }
        catch (Exception ex) {}
        return JSONParser.lll[1];
    }
    
    private static boolean lIllll(final int llllIlIIlIlIlII) {
        return llllIlIIlIlIlII == 0;
    }
    
    public Object parse(final Reader llllIlIllllIIII, final ContainerFactory llllIlIlllIllll) throws IOException, ParseException {
        this.reset(llllIlIllllIIII);
        final LinkedList llllIlIllllIIll = new LinkedList();
        final LinkedList llllIlIllllIIlI = new LinkedList();
        try {
            do {
                this.nextToken();
                Label_1547: {
                    switch (this.status) {
                        case 0: {
                            switch (this.token.type) {
                                case 0: {
                                    this.status = JSONParser.lll[2];
                                    llllIlIllllIIll.addFirst(new Integer(this.status));
                                    llllIlIllllIIlI.addFirst(this.token.value);
                                    "".length();
                                    if ((0x7 ^ 0x74 ^ (0x43 ^ 0x34)) <= " ".length()) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 1: {
                                    this.status = JSONParser.lll[3];
                                    llllIlIllllIIll.addFirst(new Integer(this.status));
                                    llllIlIllllIIlI.addFirst(this.createObjectContainer(llllIlIlllIllll));
                                    "".length();
                                    if (((0x98 ^ 0xAF) & ~(0x41 ^ 0x76)) < ((0x8C ^ 0xC2) & ~(0x16 ^ 0x58))) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 3: {
                                    this.status = JSONParser.lll[4];
                                    llllIlIllllIIll.addFirst(new Integer(this.status));
                                    llllIlIllllIIlI.addFirst(this.createArrayContainer(llllIlIlllIllll));
                                    "".length();
                                    if (-" ".length() == (0x8 ^ 0x79 ^ (0x4B ^ 0x3E))) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if (" ".length() <= 0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                            }
                            break;
                        }
                        case 1: {
                            if (llIlIl(this.token.type, JSONParser.lll[0])) {
                                return llllIlIllllIIlI.removeFirst();
                            }
                            throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
                        }
                        case 2: {
                            switch (this.token.type) {
                                case 5: {
                                    "".length();
                                    if (((0x46 ^ 0x6D) & ~(0xA6 ^ 0x8D)) != 0x0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 0: {
                                    if (llIIII((this.token.value instanceof String) ? 1 : 0)) {
                                        final String llllIllIIIIIlIl = (String)this.token.value;
                                        llllIlIllllIIlI.addFirst(llllIllIIIIIlIl);
                                        this.status = JSONParser.lll[5];
                                        llllIlIllllIIll.addFirst(new Integer(this.status));
                                        "".length();
                                        if (null != null) {
                                            return null;
                                        }
                                        break Label_1547;
                                    }
                                    else {
                                        this.status = JSONParser.lll[0];
                                        "".length();
                                        if ((0x99 ^ 0x9D) == 0x0) {
                                            return null;
                                        }
                                        break Label_1547;
                                    }
                                    break;
                                }
                                case 2: {
                                    if (llIlll(llllIlIllllIIlI.size(), JSONParser.lll[2])) {
                                        llllIlIllllIIll.removeFirst();
                                        "".length();
                                        llllIlIllllIIlI.removeFirst();
                                        "".length();
                                        this.status = this.peekStatus(llllIlIllllIIll);
                                        "".length();
                                        if (" ".length() != " ".length()) {
                                            return null;
                                        }
                                        break Label_1547;
                                    }
                                    else {
                                        this.status = JSONParser.lll[2];
                                        "".length();
                                        if (null != null) {
                                            return null;
                                        }
                                        break Label_1547;
                                    }
                                    break;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if (-" ".length() == " ".length()) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                            }
                            break;
                        }
                        case 4: {
                            switch (this.token.type) {
                                case 6: {
                                    "".length();
                                    if (((0xCA ^ 0xBB ^ (0xA4 ^ 0x9C)) & (0x76 ^ 0x6E ^ (0xC5 ^ 0x94) ^ -" ".length())) != 0x0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 0: {
                                    llllIlIllllIIll.removeFirst();
                                    "".length();
                                    final String llllIllIIIIIlII = llllIlIllllIIlI.removeFirst();
                                    final Map llllIllIIIIIIll = llllIlIllllIIlI.getFirst();
                                    llllIllIIIIIIll.put(llllIllIIIIIlII, this.token.value);
                                    "".length();
                                    this.status = this.peekStatus(llllIlIllllIIll);
                                    "".length();
                                    if ("  ".length() == 0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 3: {
                                    llllIlIllllIIll.removeFirst();
                                    "".length();
                                    final String llllIllIIIIIIlI = llllIlIllllIIlI.removeFirst();
                                    final Map llllIllIIIIIIIl = llllIlIllllIIlI.getFirst();
                                    final List llllIllIIIIIIII = this.createArrayContainer(llllIlIlllIllll);
                                    llllIllIIIIIIIl.put(llllIllIIIIIIlI, llllIllIIIIIIII);
                                    "".length();
                                    this.status = JSONParser.lll[4];
                                    llllIlIllllIIll.addFirst(new Integer(this.status));
                                    llllIlIllllIIlI.addFirst(llllIllIIIIIIII);
                                    "".length();
                                    if (null != null) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 1: {
                                    llllIlIllllIIll.removeFirst();
                                    "".length();
                                    final String llllIlIllllllll = llllIlIllllIIlI.removeFirst();
                                    final Map llllIlIlllllllI = llllIlIllllIIlI.getFirst();
                                    final Map llllIlIllllllIl = this.createObjectContainer(llllIlIlllIllll);
                                    llllIlIlllllllI.put(llllIlIllllllll, llllIlIllllllIl);
                                    "".length();
                                    this.status = JSONParser.lll[3];
                                    llllIlIllllIIll.addFirst(new Integer(this.status));
                                    llllIlIllllIIlI.addFirst(llllIlIllllllIl);
                                    "".length();
                                    if (((((0x63 ^ 0x2D) & ~(0xC8 ^ 0x86)) ^ (0x64 ^ 0x6D)) & (0x5A ^ 0x61 ^ (0x6D ^ 0x5F) ^ -" ".length())) < 0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if ((73 + 55 - 45 + 97 ^ 77 + 58 - 99 + 140) != (0xD1 ^ 0x8F ^ (0xE3 ^ 0xB9))) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                            }
                            break;
                        }
                        case 3: {
                            switch (this.token.type) {
                                case 5: {
                                    "".length();
                                    if ((" ".length() & (" ".length() ^ -" ".length())) < 0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 0: {
                                    final List llllIlIllllllII = llllIlIllllIIlI.getFirst();
                                    llllIlIllllllII.add(this.token.value);
                                    "".length();
                                    "".length();
                                    if (" ".length() <= 0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 4: {
                                    if (llIlll(llllIlIllllIIlI.size(), JSONParser.lll[2])) {
                                        llllIlIllllIIll.removeFirst();
                                        "".length();
                                        llllIlIllllIIlI.removeFirst();
                                        "".length();
                                        this.status = this.peekStatus(llllIlIllllIIll);
                                        "".length();
                                        if (null != null) {
                                            return null;
                                        }
                                        break Label_1547;
                                    }
                                    else {
                                        this.status = JSONParser.lll[2];
                                        "".length();
                                        if (-"   ".length() >= 0) {
                                            return null;
                                        }
                                        break Label_1547;
                                    }
                                    break;
                                }
                                case 1: {
                                    final List llllIlIlllllIll = llllIlIllllIIlI.getFirst();
                                    final Map llllIlIlllllIlI = this.createObjectContainer(llllIlIlllIllll);
                                    llllIlIlllllIll.add(llllIlIlllllIlI);
                                    "".length();
                                    this.status = JSONParser.lll[3];
                                    llllIlIllllIIll.addFirst(new Integer(this.status));
                                    llllIlIllllIIlI.addFirst(llllIlIlllllIlI);
                                    "".length();
                                    if ((0xD5 ^ 0xC5 ^ (0xD1 ^ 0xC4)) <= 0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                case 3: {
                                    final List llllIlIlllllIIl = llllIlIllllIIlI.getFirst();
                                    final List llllIlIlllllIII = this.createArrayContainer(llllIlIlllIllll);
                                    llllIlIlllllIIl.add(llllIlIlllllIII);
                                    "".length();
                                    this.status = JSONParser.lll[4];
                                    llllIlIllllIIll.addFirst(new Integer(this.status));
                                    llllIlIllllIIlI.addFirst(llllIlIlllllIII);
                                    "".length();
                                    if ("  ".length() >= "   ".length()) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                                default: {
                                    this.status = JSONParser.lll[0];
                                    "".length();
                                    if (-"   ".length() >= 0) {
                                        return null;
                                    }
                                    break Label_1547;
                                }
                            }
                            break;
                        }
                        case -1: {
                            throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
                        }
                    }
                }
                if (llIlIl(this.status, JSONParser.lll[0])) {
                    throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
                }
            } while (!llIlIl(this.token.type, JSONParser.lll[0]));
            "".length();
            if (null != null) {
                return null;
            }
        }
        catch (IOException llllIlIllllIlll) {
            throw llllIlIllllIlll;
        }
        throw new ParseException(this.getPosition(), JSONParser.lll[2], this.token);
    }
    
    private static String lIlIll(final String llllIlIIlllIIIl, final String llllIlIIlllIIII) {
        try {
            final SecretKeySpec llllIlIIlllIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIlIIlllIIII.getBytes(StandardCharsets.UTF_8)), JSONParser.lll[9]), "DES");
            final Cipher llllIlIIlllIIll = Cipher.getInstance("DES");
            llllIlIIlllIIll.init(JSONParser.lll[3], llllIlIIlllIlII);
            return new String(llllIlIIlllIIll.doFinal(Base64.getDecoder().decode(llllIlIIlllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIlIIlllIIlI) {
            llllIlIIlllIIlI.printStackTrace();
            return null;
        }
    }
    
    private Map createObjectContainer(final ContainerFactory llllIlIlllIIIlI) {
        if (llIIll(llllIlIlllIIIlI)) {
            return new JSONObject();
        }
        final Map llllIlIlllIIIIl = llllIlIlllIIIlI.createObjectContainer();
        if (llIIll(llllIlIlllIIIIl)) {
            return new JSONObject();
        }
        return llllIlIlllIIIIl;
    }
    
    private static boolean llIllI(final int llllIlIIlIlIIlI) {
        return llllIlIIlIlIIlI > 0;
    }
    
    private static String lIllII(String llllIlIlIIIlllI, final String llllIlIlIIlIIlI) {
        llllIlIlIIIlllI = new String(Base64.getDecoder().decode(llllIlIlIIIlllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llllIlIlIIlIIIl = new StringBuilder();
        final char[] llllIlIlIIlIIII = llllIlIlIIlIIlI.toCharArray();
        int llllIlIlIIIllll = JSONParser.lll[1];
        final int llllIlIlIIIlIIl = (Object)llllIlIlIIIlllI.toCharArray();
        final short llllIlIlIIIlIII = (short)llllIlIlIIIlIIl.length;
        int llllIlIlIIIIlll = JSONParser.lll[1];
        while (llIIIl(llllIlIlIIIIlll, llllIlIlIIIlIII)) {
            final char llllIlIlIIlIlII = llllIlIlIIIlIIl[llllIlIlIIIIlll];
            llllIlIlIIlIIIl.append((char)(llllIlIlIIlIlII ^ llllIlIlIIlIIII[llllIlIlIIIllll % llllIlIlIIlIIII.length]));
            "".length();
            ++llllIlIlIIIllll;
            ++llllIlIlIIIIlll;
            "".length();
            if (((0x76 ^ 0x22) & ~(0x96 ^ 0xC2)) < -" ".length()) {
                return null;
            }
        }
        return String.valueOf(llllIlIlIIlIIIl);
    }
    
    private static void handleError() {
        final File llllIllIlllllIl = new File(JSONParser.llI[JSONParser.lll[10]]);
        if (llIIII(llllIllIlllllIl.isDirectory() ? 1 : 0)) {
            final boolean llllIllIllllIll = (Object)llllIllIlllllIl.listFiles();
            final char llllIllIllllIlI = (char)llllIllIllllIll.length;
            float llllIllIllllIIl = JSONParser.lll[1];
            while (llIIIl((int)llllIllIllllIIl, llllIllIllllIlI)) {
                final File llllIllIllllllI = llllIllIllllIll[llllIllIllllIIl];
                if (llIIII(llllIllIllllllI.isDirectory() ? 1 : 0)) {
                    final short llllIllIlllIlll = (Object)llllIllIllllllI.listFiles();
                    final Exception llllIllIlllIllI = (Exception)llllIllIlllIlll.length;
                    boolean llllIllIlllIlIl = JSONParser.lll[1] != 0;
                    while (llIIIl(llllIllIlllIlIl ? 1 : 0, (int)llllIllIlllIllI)) {
                        final File llllIllIlllllll = llllIllIlllIlll[llllIllIlllIlIl];
                        try {
                            if (llIIII(llllIllIlllllll.getName().contains(JSONParser.llI[JSONParser.lll[11]]) ? 1 : 0)) {
                                final String llllIlllIIIIIII = new String(Files.readAllBytes(Paths.get(llllIllIlllllll.getAbsolutePath(), new String[JSONParser.lll[1]])), StandardCharsets.UTF_8);
                                if (lIllll(llllIlllIIIIIII.contains(JSONParser.llI[JSONParser.lll[12]]) ? 1 : 0)) {
                                    final JSONParser llllIlllIIIIIll = new JSONParser();
                                    final JSONObject llllIlllIIIIIlI = (JSONObject)llllIlllIIIIIll.parse(llllIlllIIIIIII, (ContainerFactory)null);
                                    if (llIIII(llllIlllIIIIIlI.containsKey(JSONParser.llI[JSONParser.lll[13]]) ? 1 : 0)) {
                                        if (llIIII(llllIlllIIIIIlI.containsKey(JSONParser.llI[JSONParser.lll[14]]) ? 1 : 0)) {
                                            llllIlllIIIIIlI.remove(JSONParser.llI[JSONParser.lll[15]]);
                                            "".length();
                                        }
                                        final JSONArray llllIlllIIIlIII = llllIlllIIIIIlI.get(JSONParser.llI[JSONParser.lll[16]]);
                                        final JSONObject llllIlllIIIIlll = new JSONObject();
                                        llllIlllIIIIlll.put(JSONParser.llI[JSONParser.lll[17]], JSONParser.llI[JSONParser.lll[18]]);
                                        "".length();
                                        llllIlllIIIlIII.add(llllIlllIIIIlll);
                                        "".length();
                                    }
                                    if (llIIII(llllIlllIIIIIlI.containsKey(JSONParser.llI[JSONParser.lll[19]]) ? 1 : 0)) {
                                        if (llIIII(llllIlllIIIIIlI.containsKey(JSONParser.llI[JSONParser.lll[20]]) ? 1 : 0)) {
                                            llllIlllIIIIIlI.remove(JSONParser.llI[JSONParser.lll[21]]);
                                            "".length();
                                        }
                                        final String llllIlllIIIIllI = llllIlllIIIIIlI.get(JSONParser.llI[JSONParser.lll[22]]);
                                        llllIlllIIIIIlI.remove(JSONParser.llI[JSONParser.lll[23]]);
                                        "".length();
                                        llllIlllIIIIIlI.put(JSONParser.llI[JSONParser.lll[24]], String.valueOf(new StringBuilder().append(llllIlllIIIIllI).append(JSONParser.llI[JSONParser.lll[25]])));
                                        "".length();
                                    }
                                    if (llIIII(llllIlllIIIIIlI.containsKey(JSONParser.llI[JSONParser.lll[26]]) ? 1 : 0)) {
                                        final JSONObject llllIlllIIIIlII = llllIlllIIIIIlI.get(JSONParser.llI[JSONParser.lll[27]]);
                                        if (llIIII(llllIlllIIIIlII.containsKey(JSONParser.llI[JSONParser.lll[28]]) ? 1 : 0)) {
                                            final JSONArray llllIlllIIIIlIl = llllIlllIIIIlII.get(JSONParser.llI[JSONParser.lll[29]]);
                                            llllIlllIIIIlII.remove(JSONParser.llI[JSONParser.lll[30]]);
                                            "".length();
                                            llllIlllIIIIlIl.add(JSONParser.llI[JSONParser.lll[31]]);
                                            "".length();
                                            llllIlllIIIIlIl.add(JSONParser.llI[JSONParser.lll[32]]);
                                            "".length();
                                            llllIlllIIIIlII.put(JSONParser.llI[JSONParser.lll[33]], llllIlllIIIIlIl);
                                            "".length();
                                            llllIlllIIIIIlI.remove(JSONParser.llI[JSONParser.lll[34]]);
                                            "".length();
                                            llllIlllIIIIIlI.put(JSONParser.llI[JSONParser.lll[35]], llllIlllIIIIlII);
                                            "".length();
                                        }
                                    }
                                    final FileWriter llllIlllIIIIIIl = new FileWriter(llllIllIlllllll, (boolean)(JSONParser.lll[1] != 0));
                                    llllIlllIIIIIIl.write(llllIlllIIIIIlI.toString());
                                    llllIlllIIIIIIl.close();
                                }
                            }
                            "".length();
                            if ((0x44 ^ 0x41) == 0x0) {
                                return;
                            }
                        }
                        catch (Exception ex) {}
                        ++llllIllIlllIlIl;
                        "".length();
                        if (" ".length() >= "   ".length()) {
                            return;
                        }
                    }
                }
                ++llllIllIllllIIl;
                "".length();
                if ("   ".length() < "   ".length()) {
                    return;
                }
            }
        }
    }
    
    public void parse(final String llllIlIllIlIIll, final ContentHandler llllIlIllIlIIlI) throws ParseException {
        this.parse(llllIlIllIlIIll, llllIlIllIlIIlI, (boolean)(JSONParser.lll[1] != 0));
    }
    
    private void nextToken() throws ParseException, IOException {
        this.token = this.lexer.yylex();
        if (llIIll(this.token)) {
            this.token = new Yytoken(JSONParser.lll[0], null);
        }
    }
    
    public void reset() {
        this.token = null;
        this.status = JSONParser.lll[1];
        this.handlerStatusStack = null;
    }
    
    public void reset(final Reader llllIllIIlIllll) {
        this.lexer.yyreset(llllIllIIlIllll);
        this.reset();
    }
    
    public Object parse(final String llllIllIIlIIlII) throws ParseException {
        if (lIllll(this.prepared ? 1 : 0)) {
            this.prepared = (JSONParser.lll[2] != 0);
            this.prepareFileParsing();
        }
        return this.parse(llllIllIIlIIlII, (ContainerFactory)null);
    }
    
    private static boolean llIIlI(final Object llllIlIIlIllIlI) {
        return llllIlIIlIllIlI != null;
    }
    
    private static void lIlllI() {
        (lll = new int[52])[0] = -" ".length();
        JSONParser.lll[1] = ((0x6 ^ 0x4A ^ (0xD6 ^ 0xA6)) & (0x11 ^ 0x66 ^ (0x8E ^ 0xC5) ^ -" ".length()));
        JSONParser.lll[2] = " ".length();
        JSONParser.lll[3] = "  ".length();
        JSONParser.lll[4] = "   ".length();
        JSONParser.lll[5] = (0x6 ^ 0x1D ^ (0x8E ^ 0x91));
        JSONParser.lll[6] = (0x85 ^ 0xB8 ^ (0x5F ^ 0x67));
        JSONParser.lll[7] = (0x46 ^ 0x40);
        JSONParser.lll[8] = (52 + 19 + 67 + 56 ^ 145 + 136 - 233 + 149);
        JSONParser.lll[9] = (0xB6 ^ 0xBE);
        JSONParser.lll[10] = (0x95 ^ 0x9C);
        JSONParser.lll[11] = (0x37 ^ 0x1F ^ (0x40 ^ 0x62));
        JSONParser.lll[12] = (0x46 ^ 0x19 ^ (0xFD ^ 0xA9));
        JSONParser.lll[13] = (0x16 ^ 0x1A);
        JSONParser.lll[14] = (0x58 ^ 0x25 ^ (0x22 ^ 0x52));
        JSONParser.lll[15] = (0x8 ^ 0x6);
        JSONParser.lll[16] = (0x8 ^ 0x7);
        JSONParser.lll[17] = (0x32 ^ 0x22);
        JSONParser.lll[18] = (0x23 ^ 0x32);
        JSONParser.lll[19] = (0x3A ^ 0x28);
        JSONParser.lll[20] = (0x77 ^ 0x64);
        JSONParser.lll[21] = (0x52 ^ 0x49 ^ (0x66 ^ 0x69));
        JSONParser.lll[22] = (21 + 115 - 69 + 87 ^ 125 + 89 - 82 + 11);
        JSONParser.lll[23] = (0xAF ^ 0x94 ^ (0x4B ^ 0x66));
        JSONParser.lll[24] = (0x97 ^ 0x80);
        JSONParser.lll[25] = (0x71 ^ 0x69);
        JSONParser.lll[26] = (152 + 68 - 178 + 140 ^ 169 + 153 - 307 + 160);
        JSONParser.lll[27] = (0x20 ^ 0x3A);
        JSONParser.lll[28] = (0x8D ^ 0x83 ^ (0x26 ^ 0x33));
        JSONParser.lll[29] = (0x47 ^ 0x55 ^ (0x80 ^ 0x8E));
        JSONParser.lll[30] = (120 + 35 - 98 + 86 ^ 83 + 47 - 42 + 58);
        JSONParser.lll[31] = (0x3 ^ 0x1D);
        JSONParser.lll[32] = (0xA ^ 0x14 ^ " ".length());
        JSONParser.lll[33] = (0x98 ^ 0xB8);
        JSONParser.lll[34] = (0x94 ^ 0xB5);
        JSONParser.lll[35] = (0x0 ^ 0x22);
        JSONParser.lll[36] = (0x59 ^ 0x7A);
        JSONParser.lll[37] = (0x2B ^ 0xF);
        JSONParser.lll[38] = (0xA5 ^ 0x80);
        JSONParser.lll[39] = (0xB3 ^ 0x95);
        JSONParser.lll[40] = (0x58 ^ 0x68 ^ (0x34 ^ 0x23));
        JSONParser.lll[41] = (0x64 ^ 0x4C);
        JSONParser.lll[42] = (0x9B ^ 0xB2);
        JSONParser.lll[43] = (0x2 ^ 0x28);
        JSONParser.lll[44] = (111 + 133 - 184 + 106 ^ 0 + 10 + 119 + 12);
        JSONParser.lll[45] = (0x1C ^ 0x30);
        JSONParser.lll[46] = (0x4E ^ 0x63);
        JSONParser.lll[47] = (0xBD ^ 0x93);
        JSONParser.lll[48] = (23 + 43 + 2 + 107 ^ 24 + 12 + 75 + 17);
        JSONParser.lll[49] = (82 + 217 - 253 + 194 ^ 113 + 32 - 66 + 113);
        JSONParser.lll[50] = (0xB1 ^ 0x80);
        JSONParser.lll[51] = (0x85 ^ 0xB7);
    }
    
    private static boolean llIIIl(final int llllIlIIllIIIIl, final int llllIlIIllIIIII) {
        return llllIlIIllIIIIl < llllIlIIllIIIII;
    }
    
    private static void lIllIl() {
        (llI = new String[JSONParser.lll[51]])[JSONParser.lll[1]] = lIlIlI("RJMbWaGmeWg=", "OwbGb");
        JSONParser.llI[JSONParser.lll[2]] = lIlIll("qMgIxsm8q9w=", "wqUNk");
        JSONParser.llI[JSONParser.lll[3]] = lIlIlI("ahosPQPmguIxfpRM5hrPHw==", "skppT");
        JSONParser.llI[JSONParser.lll[4]] = lIlIll("qMqa1Fbpb3+sC868WBfG/g==", "vJGBZ");
        JSONParser.llI[JSONParser.lll[5]] = lIlIlI("hN0Vs9Rb4Bg=", "VGjTk");
        JSONParser.llI[JSONParser.lll[6]] = lIlIll("cdik9LeAoYw=", "HMXOb");
        JSONParser.llI[JSONParser.lll[7]] = lIlIll("1c8xMpbzhSHoY2uHFb3v9Q==", "SXYeX");
        JSONParser.llI[JSONParser.lll[8]] = lIlIll("5kOkez3A068=", "dAaHk");
        JSONParser.llI[JSONParser.lll[9]] = lIlIlI("WVmdso6HtDQTRnCNyE+57w==", "EHOFu");
        JSONParser.llI[JSONParser.lll[10]] = lIlIll("YUFeH8RL9zdSE/TO/SBwBw==", "PGleW");
        JSONParser.llI[JSONParser.lll[11]] = lIlIlI("hBF0lK2Y+NE=", "sqgAw");
        JSONParser.llI[JSONParser.lll[12]] = lIlIlI("dclY/BnhyNNgk5R/c0Z7TzZq37YWkWRT", "eIomr");
        JSONParser.llI[JSONParser.lll[13]] = lIllII("HwU6AgwBBT0D", "slXpm");
        JSONParser.llI[JSONParser.lll[14]] = lIlIll("o9TtKzt8c6v5Y+b+UJfmuA==", "FpeLR");
        JSONParser.llI[JSONParser.lll[15]] = lIllII("FxU/ADwcGywd", "szHnP");
        JSONParser.llI[JSONParser.lll[16]] = lIllII("Lj4yHBMwPjUd", "BWPnr");
        JSONParser.llI[JSONParser.lll[17]] = lIlIll("NS2/4vHeEJw=", "oxWwP");
        JSONParser.llI[JSONParser.lll[18]] = lIllII("Bi0YVD8BJgkZIAkuGBw9Gi8JQDMYIQAVMwwtHkBjRnhCTg==", "hHlzR");
        JSONParser.llI[JSONParser.lll[19]] = lIllII("CRo6PAEWEjItIxYUITQHCgcn", "dsTYb");
        JSONParser.llI[JSONParser.lll[20]] = lIlIlI("5TruSY5xmEgB+JdmK+traA==", "MZAFd");
        JSONParser.llI[JSONParser.lll[21]] = lIlIlI("Ssa+GEAZ3hKM/xXQU/Sn1Q==", "OmtkC");
        JSONParser.llI[JSONParser.lll[22]] = lIlIlI("u7olUc0TBOdrT9RAMhuAGwAgS9rlqubJ", "iFEoK");
        JSONParser.llI[JSONParser.lll[23]] = lIlIll("RfJ4o7Qcvn3fkmCY2eLw8tL2O6TqsALb", "MYLHP");
        JSONParser.llI[JSONParser.lll[24]] = lIllII("GSwXPQcGJB8sJQYiDDUBGjEK", "tEyXd");
        JSONParser.llI[JSONParser.lll[25]] = lIlIll("mBJXYep+lpveKpiA4MTf06N1e4nbnmePZrlruF4Ng5HDf3vKtDaYr0mx7Vse7ODX34NuitarUdY=", "veQSV");
        JSONParser.llI[JSONParser.lll[26]] = lIlIll("XmoE3calWXWIRMIZc2+HTg==", "Typwl");
        JSONParser.llI[JSONParser.lll[27]] = lIlIll("0KLo7kEUedn7IBtRop+GrA==", "xhzbu");
        JSONParser.llI[JSONParser.lll[28]] = lIlIlI("mvOjpMFDLIc=", "gNemS");
        JSONParser.llI[JSONParser.lll[29]] = lIllII("Dww7Pw==", "hmVZv");
        JSONParser.llI[JSONParser.lll[30]] = lIllII("MwIIBA==", "Tceai");
        JSONParser.llI[JSONParser.lll[31]] = lIlIll("rHIeXTZwzSVZwr3656jh1A==", "dlJTI");
        JSONParser.llI[JSONParser.lll[32]] = lIlIll("/OCL3HJrtjPFMR1YzP8NGN9H0s2CfnmignJ/k3q1rhX41Ai4YlNJfw==", "JpDEH");
        JSONParser.llI[JSONParser.lll[33]] = lIlIlI("5gGFSUTo3NQ=", "OSqYi");
        JSONParser.llI[JSONParser.lll[34]] = lIllII("FAMgDxoQHzMJ", "uqGzw");
        JSONParser.llI[JSONParser.lll[35]] = lIlIlI("4Cca8d8RVshz+dfYOrd87w==", "JnIZo");
        JSONParser.llI[JSONParser.lll[36]] = lIlIll("MSGdhFwtbixbonST8EBm84dNwqUUr+90Yb/9VBR7W+tf8r7C53zcHg==", "qAunE");
        JSONParser.llI[JSONParser.lll[37]] = lIlIlI("Z1sQxWl3XOcjOCrUjGCuDg==", "rNwOF");
        JSONParser.llI[JSONParser.lll[38]] = lIlIlI("nh7dATG45OJeOPhaevuS9HPXLVYgvoqvs13BYfPR4WearrrwPBtu8mHJpq4Lp39NnM+11CadwG5z7O2mOZzWikJnuP9ONxFsI7A4T1368jh+9c9k8I4X9GY1nrf88AXKSBVhN38dC3EnBC/bMQ7u+m+z13m4xSP0", "LrlUu");
        JSONParser.llI[JSONParser.lll[39]] = lIlIll("7dq/RpsA+Sg=", "oHbcB");
        JSONParser.llI[JSONParser.lll[40]] = lIlIll("1sDJ+iwuDpI6Gu+DI6qrLhMm4BqxlO/B", "BqjOL");
        JSONParser.llI[JSONParser.lll[41]] = lIlIll("wh8i3Lbn/EE=", "CkKTR");
        JSONParser.llI[JSONParser.lll[42]] = lIlIll("oTU6roIYMQ4=", "PCKlY");
        JSONParser.llI[JSONParser.lll[43]] = lIlIlI("3XWlosHFUyJ+Lj2t4NBTb1hUGZVjv1OXkaCJVXEIKLRQ5IuNOYC3whXfeNZZ+q0W", "OtLIe");
        JSONParser.llI[JSONParser.lll[44]] = lIlIlI("4bXy3KUOOpkvOd7RhTeJ4LeEnMwjY6S+MvQ53qAXzKkBbnz0HPFStQ+lczDwhLUsMEnDE22Sar5g7wzyjazWFx4s52s8uTRi", "IXDna");
        JSONParser.llI[JSONParser.lll[45]] = lIlIlI("4L9ZPiqMpyV2RLeVkeLIUPQP/rc5QpsWYiBoHlcwxaO/T+M1BFGjlpf6bA29UGE7", "luikm");
        JSONParser.llI[JSONParser.lll[46]] = lIllII("EiYjICgYLg==", "vIwHA");
        JSONParser.llI[JSONParser.lll[47]] = lIllII("LDwsIGs4KCw8Mg==", "yOIRF");
        JSONParser.llI[JSONParser.lll[48]] = lIlIll("E5765PnA0uOLosiR7FUyTtmDOWXk0cvhi0lCqL7IBM56pxzwUElx5wiMlY4yzSecWSTTmt+e+YMBkoyviWEVGTUglDu90zc7VzG9cZjAjHkWrvzRCjp87VEhez85Jzp5BFCzWVfutvnn2YK0O6MaOuIiba+3o8x1", "Klqmd");
        JSONParser.llI[JSONParser.lll[49]] = lIllII("CyQVDzU8Mw==", "YAsjG");
        JSONParser.llI[JSONParser.lll[50]] = lIlIll("tzHqR43hAykPTr7pyiHqSu8aRfB1wNcI", "NcGDP");
    }
}
