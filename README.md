import { useState, useEffect, useRef, useMemo } from "react";

const CSS = `
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,700;0,800;0,900;1,400;1,700;1,900&family=Cormorant+Garamond:ital,wght@0,300;0,400;0,500;1,300;1,400&family=DM+Sans:ital,wght@0,300;0,400;0,500;1,300&family=JetBrains+Mono:wght@300;400&display=swap');
:root {
  --silver:#C8C8D0;--sdim:#6E6E78;--ice:#5ECBF0;--emerald:#3DFFA2;
  --amethyst:#B47EFF;--pink:#FF5FA2;--gold:#FFBE2E;--border:rgba(255,255,255,0.06);
  --serif:'Playfair Display',Georgia,serif;--body:'Cormorant Garamond',Georgia,serif;
  --mono:'DM Sans',system-ui,sans-serif;--code:'JetBrains Mono',monospace;
}
*,*::before,*::after{box-sizing:border-box;margin:0;padding:0;}
html{overflow:hidden;height:100%;}
body{overflow:hidden;height:100%;background:#000;cursor:none;}
::-webkit-scrollbar{width:3px;}
::-webkit-scrollbar-track{background:transparent;}
::-webkit-scrollbar-thumb{background:rgba(255,255,255,0.12);border-radius:4px;}
.sw{position:fixed;inset:0;overflow-y:auto;overflow-x:hidden;-webkit-overflow-scrolling:touch;}

.cr{position:fixed;pointer-events:none!important;z-index:99999;width:32px;height:32px;
  border:1px solid rgba(255,255,255,0.4);border-radius:50%;
  transition:width .3s cubic-bezier(.22,1,.36,1),height .3s cubic-bezier(.22,1,.36,1),border-color .25s,opacity .25s;
  transform:translate(-50%,-50%);opacity:0;}
.cr.v{opacity:1;}.cr.e{width:50px;height:50px;border-color:var(--ice);}
.cd{position:fixed;pointer-events:none!important;z-index:99999;width:4px;height:4px;
  background:#fff;border-radius:50%;transform:translate(-50%,-50%);opacity:0;}
.cd.v{opacity:1;}

.gg{position:fixed;inset:0;pointer-events:none!important;z-index:0;
  background-image:linear-gradient(rgba(255,255,255,.015) 1px,transparent 1px),
  linear-gradient(90deg,rgba(255,255,255,.015) 1px,transparent 1px);background-size:80px 80px;}
.orb{position:absolute;border-radius:50%;filter:blur(130px);pointer-events:none!important;z-index:0;transition:opacity 1.4s;}

.et{position:fixed;bottom:28px;right:28px;z-index:1000;display:flex;align-items:center;gap:10px;
  padding:9px 18px;background:rgba(0,0,0,.85);border:1px solid var(--border);
  cursor:pointer;transition:all .35s;backdrop-filter:blur(12px);}
.et:hover{border-color:rgba(255,255,255,.18);}.et.on{border-color:var(--ice);}

.ckw{display:flex;align-items:center;gap:11px;cursor:pointer;padding:2px 0;position:relative;user-select:none;}
.ckb{width:13px;height:13px;flex-shrink:0;border:1px solid rgba(255,255,255,.2);position:relative;transition:border-color .3s;}
.ckb::after{content:'';position:absolute;inset:3px;border-radius:1px;transform:scale(0);
  transition:transform .3s cubic-bezier(.22,1,.36,1),background .3s;}
.ckw.on .ckb{border-color:currentColor;}.ckw.on .ckb::after{transform:scale(1);background:currentColor;}
.ckw .cks{position:absolute;left:24px;top:50%;height:1px;width:0;background:currentColor;opacity:.3;
  transition:width .4s cubic-bezier(.22,1,.36,1);}
.ckw.on .cks{width:calc(100% - 28px);}
.ckw .ckt{transition:color .3s,opacity .3s;}.ckw.on .ckt{opacity:.35;}

.tinp{background:transparent;border:none;outline:none;color:var(--silver);font-family:var(--mono);width:100%;}
.tinp::placeholder{color:rgba(255,255,255,.08);font-style:italic;}
.tinp:disabled,.tinp:read-only{opacity:.85;cursor:default;}

.brow{display:grid;grid-template-columns:44px 1fr 28px;align-items:center;padding:18px 0;
  border-bottom:1px solid var(--border);transition:background .3s;}
.brow:hover{background:rgba(255,255,255,.01);}
.bnum{font-family:var(--serif);font-style:italic;font-size:20px;font-weight:300;opacity:.15;}
.brem{opacity:0;transition:opacity .3s;cursor:pointer;color:var(--sdim);text-align:center;font-size:15px;}
.brem:hover{color:#fff;}.brow:hover .brem{opacity:1;}

.ptog{font-family:var(--mono);font-size:9px;letter-spacing:.12em;text-transform:uppercase;
  padding:4px 10px;cursor:pointer;border:1px solid;transition:all .3s;user-select:none;}
.dl-row{display:flex;align-items:center;gap:10px;padding:8px 0;border-bottom:1px solid var(--border);}
.dl-rem{opacity:0;transition:opacity .2s;cursor:pointer;color:var(--sdim);font-size:13px;flex-shrink:0;}
.dl-rem:hover{color:#fff;}.dl-row:hover .dl-rem{opacity:1;}

.disc-seg{cursor:pointer;transition:opacity .3s,filter .3s;opacity:.55;fill:none;stroke-width:2.5;}
.disc-seg:hover{opacity:1;filter:drop-shadow(0 0 10px currentColor);}

.tab-btn{font-family:var(--mono);font-size:11px;letter-spacing:.1em;text-transform:uppercase;
  padding:10px 20px;cursor:pointer;border:1px solid var(--border);background:transparent;
  color:var(--sdim);transition:all .3s;outline:none;}
.tab-btn:hover{border-color:rgba(255,255,255,.15);color:var(--silver);}
.tab-btn.active{border-color:var(--emerald);color:var(--emerald);background:rgba(61,255,162,.04);}

.slot-card{display:grid;grid-template-columns:160px 1fr 1fr 120px;gap:16px;align-items:center;
  padding:16px 20px;border:1px solid var(--border);border-radius:4px;transition:background .2s;}
.slot-card:hover{background:rgba(255,255,255,.012);}

.grade-inp{background:rgba(255,255,255,.03);border:1px solid rgba(255,255,255,.06);border-radius:3px;
  outline:none;font-family:var(--code);font-size:14px;color:var(--silver);text-align:center;
  width:54px;padding:6px 4px;transition:border-color .3s,background .3s;}
.grade-inp:focus{border-color:rgba(94,203,240,.4);background:rgba(94,203,240,.04);}
.grade-inp:disabled{background:transparent;border-color:transparent;opacity:.85;}

@keyframes fadeSlow{from{opacity:0}to{opacity:1}}
@keyframes drift{from{opacity:0;transform:translateY(16px)}to{opacity:1;transform:translateY(0)}}
@keyframes pulse{0%,100%{opacity:.3}50%{opacity:1}}
@keyframes countIn{from{opacity:0;transform:translateY(20px) scale(.96)}to{opacity:1;transform:translateY(0) scale(1)}}
@keyframes orbFloat{0%,100%{transform:scale(1);opacity:.6}50%{transform:scale(1.06);opacity:.85}}

@media(max-width:900px){
  .cr,.cd{display:none!important;}body{cursor:auto!important;}
  .bento{grid-template-columns:1fr!important;}
  .slot-card{grid-template-columns:1fr!important;gap:8px!important;}
  .rope-row{grid-template-columns:1fr!important;}
  .rope-line{display:none!important;}
}
`;

const AC = {
  ice:{c:"#5ECBF0",g:"rgba(94,203,240,.25)",d:"rgba(94,203,240,.06)"},
  emerald:{c:"#3DFFA2",g:"rgba(61,255,162,.22)",d:"rgba(61,255,162,.05)"},
  amethyst:{c:"#B47EFF",g:"rgba(180,126,255,.22)",d:"rgba(180,126,255,.05)"},
  pink:{c:"#FF5FA2",g:"rgba(255,95,162,.22)",d:"rgba(255,95,162,.05)"},
  gold:{c:"#FFBE2E",g:"rgba(255,190,46,.22)",d:"rgba(255,190,46,.05)"},
};
const LS = {fontFamily:"var(--mono)",fontSize:9,letterSpacing:".2em",textTransform:"uppercase",color:"var(--sdim)"};
function getToday(){return new Date().toISOString().split("T")[0];}
function daysTo(d){return Math.ceil((new Date(d)-new Date(getToday()))/864e5);}
async function loadD(k,f){try{const r=await window.storage.get(k);return r?JSON.parse(r.value):f;}catch{return f;}}
async function saveD(k,v){try{await window.storage.set(k,JSON.stringify(v));}catch(e){console.warn(e);}}

/* ── Defaults ── */
const D_CT={title:"UPSC Prelims",date:"2030-06-01"};
const D_BK=[{id:1,title:"The Discovery of India",done:false},{id:2,title:"India After Gandhi",done:false},{id:3,title:"Why Nations Fail",done:false},{id:4,title:"Prisoners of Geography",done:false},{id:5,title:"The Argumentative Indian",done:false}];
const D_TL=[
  {id:1,year:"2026",phase:"Foundation",accent:"emerald",sub:"Master the fundamentals.",items:[{id:101,text:"Master C++ & Java OOP",done:false},{id:102,text:"HTML/CSS/JS (Summer)",done:false},{id:103,text:"Duke Java Specialization",done:false},{id:104,text:"NCERTs Class 6–9",done:false},{id:105,text:"First portfolio project",done:false}]},
  {id:2,year:"2027",phase:"Skill Building",accent:"amethyst",sub:"Python. Data. ML.",items:[{id:201,text:"Python + SQL + Pandas",done:false},{id:202,text:"ML — Andrew Ng",done:false},{id:203,text:"Loan Risk Scoring",done:false},{id:204,text:"NCERTs 6–12 complete",done:false},{id:205,text:"Internship apps",done:false}]},
  {id:3,year:"2028",phase:"Application",accent:"pink",sub:"Ship real projects.",items:[{id:301,text:"Deep learning basics",done:false},{id:302,text:"Internship secured",done:false},{id:303,text:"Warehouse Receipt System",done:false},{id:304,text:"UPSC answer writing",done:false}]},
  {id:4,year:"2029",phase:"Capstone",accent:"gold",sub:"Graduate. Launch.",items:[{id:401,text:"Major Project — BI System",done:false},{id:402,text:"Graduate B.Tech",done:false},{id:403,text:"Full-time UPSC prep",done:false}]},
  {id:5,year:"2030",phase:"UPSC",accent:"gold",sub:"Two attempts.",items:[{id:501,text:"UPSC Attempt 1",done:false},{id:502,text:"Attempt 2 if needed",done:false}]},
];
const D_HB=[{id:1,text:"NCERT — 30m",ch:false},{id:2,text:"Editorial — 15m",ch:false},{id:3,text:"Coursera — 1.5h",ch:false},{id:4,text:"Code practice",ch:false},{id:5,text:"Prompt eng.",ch:false}];
const D_SK=[{name:"C++",value:3},{name:"Java",value:2},{name:"Python",value:1},{name:"SQL",value:0},{name:"ML",value:0},{name:"Prompt Eng.",value:2}];
const D_PR=[{id:1,name:"Fuel Reconciliation",deployed:false},{id:2,name:"Commodity Alerts",deployed:false}];
function mkTT(){
  return["Day Order 1","Day Order 2","Day Order 3","Day Order 4","Day Order 5"].map(d=>({day:d,slots:[
    {id:Date.now()+Math.random(),time:"8:00 – 9:00",course:"",prof:"",room:""},
    {id:Date.now()+Math.random(),time:"9:00 – 10:00",course:"",prof:"",room:""},
    {id:Date.now()+Math.random(),time:"10:00 – 11:00",course:"",prof:"",room:""},
    {id:Date.now()+Math.random(),time:"11:00 – 12:00",course:"",prof:"",room:""},
    {id:Date.now()+Math.random(),time:"12:00 – 1:00",course:"Lunch",prof:"—",room:"—"},
    {id:Date.now()+Math.random(),time:"1:00 – 2:00",course:"",prof:"",room:""},
    {id:Date.now()+Math.random(),time:"2:00 – 3:00",course:"",prof:"",room:""},
    {id:Date.now()+Math.random(),time:"3:00 – 4:00",course:"",prof:"",room:""},
    {id:Date.now()+Math.random(),time:"4:00 – 5:00",course:"",prof:"",room:""},
  ]}));
}
const D_CO=[
  {code:"21PYB102J",name:"Semiconductor Physics",cr:5,int:0,ext:0},
  {code:"21CSC101T",name:"OOD & Programming",cr:3,int:0,ext:0},
  {code:"21MAB102T",name:"Adv. Calculus & Complex Analysis",cr:4,int:0,ext:0},
  {code:"21LEH101T",name:"Communicative English",cr:3,int:0,ext:0},
  {code:"21EES101T",name:"Electrical & Electronics Eng.",cr:4,int:0,ext:0},
  {code:"21CYM101T",name:"Environmental Science",cr:0,int:0,ext:0},
  {code:"21MES102L",name:"Engg. Graphics & Design",cr:2,int:0,ext:0},
];
const D_SGR=[0,0,0,0,0,0,0,0];

/* ── Hooks ── */
function useIV(t){
  const r=useRef(null);const[v,sV]=useState(false);
  useEffect(()=>{const e=r.current;if(!e)return;const o=new IntersectionObserver(([x])=>{if(x.isIntersecting)sV(true);},{threshold:t||.1});o.observe(e);return()=>o.disconnect();},[t]);
  return[r,v];
}

/* ── Editable (respects edit lock) ── */
function Ed({value,onChange,em,style,tag}){
  const T=tag||"span";
  const[editing,sE]=useState(false);
  const[d,sD]=useState(value);
  const r=useRef(null);
  useEffect(()=>{sD(value);},[value]);
  useEffect(()=>{if(editing&&r.current){r.current.focus();r.current.select();}},[editing]);
  const commit=()=>{sE(false);if(d.trim())onChange(d.trim());else sD(value);};

  if(editing&&em){
    return <input ref={r} value={d} onChange={x=>sD(x.target.value)} onBlur={commit}
      onKeyDown={x=>{if(x.key==="Enter")commit();if(x.key==="Escape"){sD(value);sE(false);}}}
      data-editable="true"
      style={{background:"transparent",border:"none",outline:"none",color:"inherit",font:"inherit",
        letterSpacing:"inherit",textTransform:"inherit",width:"100%",padding:0,lineHeight:"inherit",
        borderBottom:"1px solid rgba(255,255,255,.25)",...style}}/>;
  }
  return <T style={{...style,cursor:em?"text":"default"}} onClick={()=>{if(em)sE(true);}}
    data-editable={em?"true":undefined}>{value}</T>;
}

/* ── Checkbox (always clickable) ── */
function Ck({checked,onChange,children,accent}){
  return <div className={checked?"ckw on":"ckw"} onClick={onChange} style={{color:accent||"#fff"}} data-clickable="true">
    <div className="ckb"/><span className="ckt" style={{fontFamily:"var(--mono)",fontSize:13,letterSpacing:".03em",color:"var(--silver)"}}>{children}</span><div className="cks"/>
  </div>;
}

/* ── Cursor ── */
function Cur(){
  const rr=useRef(null),dr=useRef(null),mp=useRef({x:-100,y:-100}),rp=useRef({x:-100,y:-100});
  const[v,sV]=useState(false),[ex,sE]=useState(false),af=useRef(null);
  useEffect(()=>{
    const m=e=>{mp.current={x:e.clientX,y:e.clientY};if(!v)sV(true);
      if(dr.current){dr.current.style.left=e.clientX+"px";dr.current.style.top=e.clientY+"px";}
      const t=document.elementFromPoint(e.clientX,e.clientY);
      sE(!!(t&&(t.closest("[data-editable]")||t.closest("[data-clickable]")||t.tagName==="INPUT"||t.tagName==="TEXTAREA"||t.tagName==="BUTTON")));
    };
    const a=()=>{rp.current.x+=(mp.current.x-rp.current.x)*.11;rp.current.y+=(mp.current.y-rp.current.y)*.11;
      if(rr.current){rr.current.style.left=rp.current.x+"px";rr.current.style.top=rp.current.y+"px";}
      af.current=requestAnimationFrame(a);};
    document.addEventListener("mousemove",m);document.addEventListener("mouseleave",()=>sV(false));
    document.addEventListener("mouseenter",()=>sV(true));af.current=requestAnimationFrame(a);
    return()=>{document.removeEventListener("mousemove",m);cancelAnimationFrame(af.current);};
  },[v]);
  return <React.Fragment><div ref={rr} className={"cr"+(v?" v":"")+(ex?" e":"")}/><div ref={dr} className={"cd"+(v?" v":"")}/></React.Fragment>;
}

/* ── Smooth Scroll ── */
function SS({children,innerRef}){
  const wr=useRef(null);
  useEffect(()=>{
    const el=wr.current;if(!el)return;if(innerRef)innerRef.current=el;
    let c=0,t=0,run=true;const ease=.065;
    const cl=()=>{t=Math.max(0,Math.min(t,el.scrollHeight-el.clientHeight));};
    const wh=e=>{e.preventDefault();t+=e.deltaY*1.1;cl();};
    let ty=0;
    const ts=e=>{ty=e.touches[0].clientY;};
    const tm=e=>{t+=(ty-e.touches[0].clientY)*1.4;ty=e.touches[0].clientY;cl();};
    const kd=e=>{const m={ArrowDown:100,ArrowUp:-100,PageDown:400,PageUp:-400," ":400};if(m[e.key]!==undefined){e.preventDefault();t+=m[e.key];cl();}};
    const tk=()=>{if(!run)return;const d=t-c;if(Math.abs(d)>.5){c+=d*ease;el.scrollTop=c;}else c=t;requestAnimationFrame(tk);};
    c=el.scrollTop;t=c;
    el.addEventListener("wheel",wh,{passive:false});el.addEventListener("touchstart",ts,{passive:true});
    el.addEventListener("touchmove",tm,{passive:true});document.addEventListener("keydown",kd);tk();
    return()=>{run=false;el.removeEventListener("wheel",wh);document.removeEventListener("keydown",kd);};
  },[]);
  return <div ref={wr} className="sw">{children}</div>;
}

function Div({label}){
  return <div style={{display:"flex",alignItems:"center",justifyContent:"center",padding:"20px 48px",gap:16}}>
    <div style={{flex:1,height:1,background:"linear-gradient(90deg,transparent,var(--border))"}}/><span style={{...LS,fontSize:8,letterSpacing:".3em"}}>{label}</span><div style={{flex:1,height:1,background:"linear-gradient(90deg,var(--border),transparent)"}}/>
  </div>;
}

/* ═══════════════════════════════════════════════
   1. HERO
   ═══════════════════════════════════════════════ */
function Hero({ct,sCt,em,hb,sHb,dt,sDt,hm}){
  const[now,sN]=useState(new Date());const[ref,iv]=useIV(.05);
  useEffect(()=>{const t=setInterval(()=>sN(new Date()),1000);return()=>clearInterval(t);},[]);
  const tgt=new Date(ct.date+"T00:00:00");const diff=Math.max(0,tgt-now);
  const dd=Math.floor(diff/864e5),hh=Math.floor((diff%864e5)/36e5),mm=Math.floor((diff%36e5)/6e4),ss=Math.floor((diff%6e4)/1e3);
  const u=[{v:dd,l:"Days"},{v:hh,l:"Hrs"},{v:mm,l:"Min"},{v:ss,l:"Sec"}];
  const chk=dt.filter(x=>x.ch).length;
  const grid=useMemo(()=>{const c=[];const n=new Date();for(let i=364;i>=0;i--){const d=new Date(n);d.setDate(d.getDate()-i);c.push(d.toISOString().split("T")[0]);}return c;},[]);
  const ins=["rgba(255,255,255,.03)","rgba(94,203,240,.12)","rgba(94,203,240,.25)","rgba(94,203,240,.45)","rgba(94,203,240,.65)","rgba(94,203,240,.9)"];
  const gws=["none","none","none","0 0 3px rgba(94,203,240,.15)","0 0 5px rgba(94,203,240,.2)","0 0 8px rgba(94,203,240,.35)"];
  const t2=getToday();

  return(
    <section ref={ref} style={{minHeight:"100vh",position:"relative",display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center",padding:"60px 32px",overflow:"hidden"}}>
      <div className="orb" style={{width:600,height:600,top:"12%",left:"25%",background:"radial-gradient(circle,rgba(94,203,240,.14) 0%,transparent 68%)",opacity:iv?1:0,animation:iv?"orbFloat 9s ease infinite":"none"}}/>
      <div style={{position:"absolute",top:28,left:36,...LS,animation:iv?"fadeSlow 1s .2s both":"none"}}><div style={{color:"var(--ice)",fontSize:8,marginBottom:3}}>DATE</div>{new Date().toLocaleDateString("en-GB",{day:"2-digit",month:"short",year:"numeric"}).toUpperCase()}</div>
      <div style={{position:"absolute",top:28,right:36,textAlign:"right",...LS,animation:iv?"fadeSlow 1s .3s both":"none"}}><div style={{color:"var(--ice)",fontSize:8,marginBottom:3}}>STATUS</div>SEM 02 · B.TECH CSE</div>

      <div style={{textAlign:"center",marginBottom:36,animation:iv?"drift 1s .3s both":"none"}}>
        <Ed value={ct.title} onChange={v=>sCt(p=>({...p,title:v}))} em={em} tag="div" style={{fontFamily:"var(--mono)",fontSize:11,letterSpacing:".18em",textTransform:"uppercase",color:"var(--ice)",marginBottom:5}}/>
        <div style={{fontFamily:"var(--serif)",fontStyle:"italic",fontSize:14,color:"var(--sdim)",fontWeight:300}}>Countdown</div>
      </div>
      <div style={{display:"flex",gap:40,justifyContent:"center",marginBottom:24}}>
        {u.map((x,i)=><div key={x.l} style={{textAlign:"center",animation:iv?"countIn .7s "+(0.4+i*.1)+"s both":"none"}}><div style={{fontFamily:"var(--serif)",fontWeight:900,fontSize:i===0?80:56,color:"#fff",lineHeight:.9}}>{String(x.v).padStart(i===0?3:2,"0")}</div><div style={{fontFamily:"var(--mono)",fontSize:9,letterSpacing:".2em",textTransform:"uppercase",color:"var(--sdim)",marginTop:8}}>{x.l}</div></div>)}
      </div>
      <div style={{display:"flex",alignItems:"center",gap:14,marginBottom:40,animation:iv?"fadeSlow 1s .9s both":"none"}}>
        <div style={{width:24,height:1,background:"rgba(255,255,255,.08)"}}/><span style={LS}>TARGET</span>
        {em?<input type="date" value={ct.date} onChange={e=>sCt(p=>({...p,date:e.target.value}))} data-editable="true" style={{fontFamily:"var(--serif)",fontSize:15,color:"var(--silver)",background:"transparent",border:"none",outline:"none",borderBottom:"1px solid rgba(255,255,255,.2)"}}/>:
        <span style={{fontFamily:"var(--serif)",fontSize:15,fontStyle:"italic",color:"var(--silver)"}}>{tgt.toLocaleDateString("en-GB",{day:"numeric",month:"long",year:"numeric"})}</span>}
        <div style={{width:24,height:1,background:"rgba(255,255,255,.08)"}}/>
      </div>

      {/* Daily Engine */}
      <div style={{maxWidth:540,width:"100%",animation:iv?"fadeSlow 1s .6s both":"none"}}>
        <div style={{...LS,color:"var(--ice)",fontSize:8,marginBottom:12,textAlign:"center"}}>DAILY ENGINE · {chk}/5</div>
        <div style={{display:"flex",gap:10,marginBottom:16,flexWrap:"wrap",justifyContent:"center"}}>
          {dt.map((t,i)=><div key={i} style={{display:"flex",alignItems:"center",gap:7,flex:"1 1 45%",minWidth:200}}>
            <div data-clickable="true" onClick={()=>sDt(p=>p.map((x,j)=>j===i?{...x,ch:!x.ch}:x))} style={{width:12,height:12,flexShrink:0,border:"1px solid "+(t.ch?"var(--ice)":"rgba(255,255,255,.15)"),cursor:"pointer",position:"relative"}}>{t.ch&&<div style={{position:"absolute",inset:2,background:"var(--ice)",borderRadius:1}}/>}</div>
            <input className="tinp" value={t.text} onChange={e=>sDt(p=>p.map((x,j)=>j===i?{...x,text:e.target.value}:x))} readOnly={!em} placeholder={"Task "+(i+1)} data-editable={em?"true":undefined} style={{fontSize:12,borderBottom:"1px solid rgba(255,255,255,.04)",padding:"5px 0",textDecoration:t.ch?"line-through":"none",opacity:t.ch?.35:1}}/>
          </div>)}
        </div>
        <div style={{display:"flex",flexDirection:"column",alignItems:"center"}}>
          <div style={{display:"grid",gridTemplateRows:"repeat(7,8px)",gridAutoFlow:"column",gridAutoColumns:"8px",gap:2,overflowX:"auto",maxWidth:"100%"}}>
            {grid.map(k=>{let lv=0;if(k===t2)lv=chk;else if(hm[k]!==undefined)lv=hm[k];return <div key={k} style={{width:8,height:8,borderRadius:2,background:ins[lv],boxShadow:gws[lv],outline:k===t2?"1px solid rgba(94,203,240,.35)":"none",outlineOffset:1}}/>;})}
          </div>
          <div style={{marginTop:6,display:"flex",gap:3,alignItems:"center",justifyContent:"center"}}>
            <span style={{...LS,fontSize:7}}>Less</span>
            {ins.map((b,i)=><div key={i} style={{width:8,height:8,borderRadius:2,background:b}}/>)}
            <span style={{...LS,fontSize:7}}>More</span>
          </div>
        </div>
      </div>
    </section>
  );
}

/* ═══════════════════════════════════════════════
   2. TABBED TIMETABLE
   ═══════════════════════════════════════════════ */
function Timetable({tt,sTt,em}){
  const[ref,iv]=useIV(.06);
  const[tab,sTab]=useState(0);
  const day=tt[tab];

  const upd=(si,field,val)=>{
    sTt(p=>p.map((d,i)=>i===tab?{...d,slots:d.slots.map((s,j)=>j===si?{...s,[field]:val}:s)}:d));
  };
  const addSlot=()=>{
    sTt(p=>p.map((d,i)=>i===tab?{...d,slots:[...d.slots,{id:Date.now(),time:"",course:"",prof:"",room:""}]}:d));
  };
  const delSlot=(si)=>{
    sTt(p=>p.map((d,i)=>i===tab?{...d,slots:d.slots.filter((_,j)=>j!==si)}:d));
  };

  return(
    <section ref={ref} style={{position:"relative",padding:"40px 32px 60px",overflow:"hidden"}}>
      <div className="orb" style={{width:500,height:500,top:"-10%",right:"5%",background:"radial-gradient(circle,rgba(61,255,162,.08) 0%,transparent 65%)",opacity:iv?.5:0}}/>
      <div style={{maxWidth:800,margin:"0 auto",position:"relative",zIndex:1}}>
        <div style={{...LS,color:"var(--emerald)",marginBottom:16,opacity:iv?1:0,transition:"opacity .8s .1s"}}>WEEKLY TIMETABLE · 8AM–5PM</div>

        {/* Tabs */}
        <div style={{display:"flex",gap:4,marginBottom:24,flexWrap:"wrap",opacity:iv?1:0,transition:"opacity .8s .2s"}}>
          {tt.map((d,i)=><button key={i} className={"tab-btn"+(tab===i?" active":"")} onClick={()=>sTab(i)} data-clickable="true">{d.day}</button>)}
        </div>

        {/* Slots */}
        <div style={{display:"flex",flexDirection:"column",gap:8,opacity:iv?1:0,transition:"opacity 1s .3s"}}>
          {day && day.slots.map((s,si)=>(
            <div key={s.id || si} className="slot-card">
              <input className="tinp" value={s.time} onChange={e=>upd(si,"time",e.target.value)} readOnly={!em} placeholder="8:15 AM – 9:30 AM" data-editable={em?"true":undefined} style={{fontSize:14,color:"var(--ice)",fontFamily:"var(--code)",letterSpacing:".03em"}}/>
              <input className="tinp" value={s.course} onChange={e=>upd(si,"course",e.target.value)} readOnly={!em} placeholder="Course Name" data-editable={em?"true":undefined} style={{fontSize:15,fontWeight:500,color:"var(--silver)"}}/>
              <input className="tinp" value={s.prof} onChange={e=>upd(si,"prof",e.target.value)} readOnly={!em} placeholder="Professor" data-editable={em?"true":undefined} style={{fontSize:13,color:"var(--sdim)"}}/>
              <div style={{display:"flex",alignItems:"center",gap:8}}>
                <input className="tinp" value={s.room} onChange={e=>upd(si,"room",e.target.value)} readOnly={!em} placeholder="Room" data-editable={em?"true":undefined} style={{fontSize:13,color:"var(--sdim)"}}/>
                {em&&<span onClick={()=>delSlot(si)} data-clickable="true" style={{cursor:"pointer",color:"var(--sdim)",fontSize:16,opacity:.3,transition:"opacity .2s",flexShrink:0}} onMouseEnter={e=>{e.target.style.opacity=1;}} onMouseLeave={e=>{e.target.style.opacity=.3;}}>×</span>}
              </div>
            </div>
          ))}
          {em&&<div onClick={addSlot} data-clickable="true" style={{padding:"12px 20px",border:"1px dashed rgba(255,255,255,.08)",borderRadius:4,textAlign:"center",cursor:"pointer",...LS,fontSize:10,color:"var(--sdim)",transition:"all .3s"}}
            onMouseEnter={e=>{e.currentTarget.style.borderColor="var(--emerald)";e.currentTarget.style.color="var(--emerald)";}}
            onMouseLeave={e=>{e.currentTarget.style.borderColor="rgba(255,255,255,.08)";e.currentTarget.style.color="var(--sdim)";}}>
            + ADD SLOT
          </div>}
        </div>
      </div>
    </section>
  );
}

/* ═══════════════════════════════════════════════
   3. SGPA PREDICTOR
   ═══════════════════════════════════════════════ */
function gr(t){if(t>=90)return{g:"O",p:10};if(t>=80)return{g:"A+",p:9};if(t>=70)return{g:"A",p:8};if(t>=60)return{g:"B+",p:7};if(t>=50)return{g:"B",p:6};if(t>=40)return{g:"C",p:5};return{g:"F",p:0};}

function SGPACalc({co,sCo,em}){
  const[ref,iv]=useIV(.06);
  const sgpa=useMemo(()=>{let tc=0,tg=0;co.forEach(c=>{if(!c.cr)return;const t=Math.min(60,Math.max(0,c.int))+Math.min(40,Math.max(0,c.ext));const{p}=gr(t);tc+=c.cr;tg+=c.cr*p;});return tc?(tg/tc).toFixed(2):"0.00";},[co]);
  const upd=(i,f,v)=>sCo(p=>p.map((c,j)=>j===i?{...c,[f]:f==="int"||f==="ext"||f==="cr"?parseInt(v)||0:v}:c));

  return(
    <section ref={ref} style={{position:"relative",padding:"40px 32px 60px",overflow:"hidden"}}>
      <div style={{maxWidth:880,margin:"0 auto",position:"relative",zIndex:1}}>
        <div style={{display:"flex",justifyContent:"space-between",alignItems:"baseline",marginBottom:20,opacity:iv?1:0,transition:"opacity .8s .1s"}}>
          <div><div style={{...LS,color:"var(--amethyst)",marginBottom:6}}>SGPA PREDICTOR</div><div style={{fontFamily:"var(--serif)",fontSize:26,fontWeight:900,color:"#fff"}}>Semester 2</div></div>
          <div style={{fontFamily:"var(--serif)",fontSize:40,fontWeight:900,color:"var(--amethyst)"}}>{sgpa}</div>
        </div>
        <div style={{overflowX:"auto",border:"1px solid var(--border)",borderRadius:4,opacity:iv?1:0,transition:"opacity 1s .2s"}}>
          <table style={{width:"100%",borderCollapse:"collapse",minWidth:680}}>
            <thead><tr>{["Code","Course","Cr","Int /60","Ext /40","Total","Grade"].map(h=><th key={h} style={{...LS,fontSize:9,padding:"10px 12px",textAlign:"left",borderBottom:"1px solid var(--border)",background:"rgba(255,255,255,.015)",color:"var(--sdim)"}}>{h}</th>)}</tr></thead>
            <tbody>{co.map((c,i)=>{
              const t=Math.min(60,Math.max(0,c.int))+Math.min(40,Math.max(0,c.ext));
              const{g:gd,p}=gr(t);
              const gc=p>=9?"var(--emerald)":p>=7?"var(--ice)":p>=5?"var(--gold)":"var(--pink)";
              return <tr key={i}>
                <td style={{padding:"10px 12px",borderBottom:"1px solid var(--border)"}}><input className="tinp" value={c.code} onChange={e=>upd(i,"code",e.target.value)} readOnly={!em} data-editable={em?"true":undefined} style={{fontSize:12,color:"var(--sdim)",fontFamily:"var(--code)",width:100}}/></td>
                <td style={{padding:"10px 12px",borderBottom:"1px solid var(--border)"}}><input className="tinp" value={c.name} onChange={e=>upd(i,"name",e.target.value)} readOnly={!em} data-editable={em?"true":undefined} style={{fontSize:14,color:"var(--silver)"}}/></td>
                <td style={{padding:"10px 12px",borderBottom:"1px solid var(--border)",textAlign:"center"}}><input className="grade-inp" type="number" min="0" max="10" value={c.cr||""} onChange={e=>upd(i,"cr",e.target.value)} disabled={!em}/></td>
                <td style={{padding:"10px 12px",borderBottom:"1px solid var(--border)",textAlign:"center"}}><input className="grade-inp" type="number" min="0" max="60" value={c.int||""} onChange={e=>upd(i,"int",e.target.value)} disabled={!em} style={{borderColor:em?"rgba(94,203,240,.2)":"transparent",background:em?"rgba(94,203,240,.03)":"transparent"}}/></td>
                <td style={{padding:"10px 12px",borderBottom:"1px solid var(--border)",textAlign:"center"}}><input className="grade-inp" type="number" min="0" max="40" value={c.ext||""} onChange={e=>upd(i,"ext",e.target.value)} disabled={!em} style={{borderColor:em?"rgba(94,203,240,.2)":"transparent",background:em?"rgba(94,203,240,.03)":"transparent"}}/></td>
                <td style={{padding:"10px 12px",borderBottom:"1px solid var(--border)",textAlign:"center",fontFamily:"var(--code)",fontSize:14,color:t?"var(--silver)":"var(--sdim)"}}>{t||"—"}</td>
                <td style={{padding:"10px 12px",borderBottom:"1px solid var(--border)",textAlign:"center",fontFamily:"var(--code)",fontSize:14,fontWeight:600,color:t?gc:"var(--sdim)"}}>{t?gd:"—"}</td>
              </tr>;})}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}

/* ═══════════════════════════════════════════════
   4. BENTO GRID
   ═══════════════════════════════════════════════ */
/* Reading List */
function ReadList({bk,sBk,em}){
  const[nt,sNt]=useState("");
  const add=()=>{const t=nt.trim();if(!t)return;sBk(p=>[...p,{id:Date.now(),title:t,done:false}]);sNt("");};
  const dc=bk.filter(b=>b.done).length;
  return <div>
    <div style={{...LS,color:"var(--ice)",fontSize:8,marginBottom:10}}>READING LIST · {dc}/{bk.length}</div>
    {bk.map((b,i)=><div key={b.id} className="brow"><div className="bnum">{String(i+1).padStart(2,"0")}</div>
      <Ck checked={b.done} onChange={()=>sBk(p=>p.map(x=>x.id===b.id?{...x,done:!x.done}:x))} accent={AC.ice.c}>
        <Ed value={b.title} onChange={v=>sBk(p=>p.map(x=>x.id===b.id?{...x,title:v}:x))} em={em} style={{fontFamily:"var(--serif)",fontSize:15,fontWeight:500,color:"#fff"}}/>
      </Ck>
      {em&&<div className="brem" style={{opacity:1}} onClick={()=>sBk(p=>p.filter(x=>x.id!==b.id))}>×</div>}
    </div>)}
    {em&&<div style={{display:"flex",gap:8,paddingTop:10}}>
      <input value={nt} onChange={e=>sNt(e.target.value)} onKeyDown={e=>{if(e.key==="Enter")add();}} placeholder="Add book..." data-editable="true" className="tinp" style={{fontSize:14,borderBottom:"1px solid rgba(255,255,255,.08)",padding:"4px 0"}}/>
      <span onClick={add} data-clickable="true" style={{...LS,fontSize:9,cursor:"pointer",padding:"5px 10px",border:"1px solid var(--border)",transition:"all .3s"}} onMouseEnter={e=>{e.target.style.borderColor="var(--ice)";e.target.style.color="var(--ice)";}} onMouseLeave={e=>{e.target.style.borderColor="var(--border)";e.target.style.color="var(--sdim)";}}>ADD</span>
    </div>}
  </div>;
}

/* Nav Disc — wireframe, with scrollIntoView */
function NavDisc(){
  const segs=[{year:"2026",color:AC.emerald.c},{year:"2027",color:AC.amethyst.c},{year:"2028",color:AC.pink.c},{year:"2029",color:AC.gold.c},{year:"2030",color:AC.gold.c}];
  const n=segs.length;const R=78;const cx=100;const cy=100;
  const goTo=yr=>{const el=document.getElementById("phase-"+yr);if(el)el.scrollIntoView({behavior:"smooth",block:"start"});};
  const arcs=segs.map((_,i)=>{const a1=(2*Math.PI*i/n)-Math.PI/2+.06;const a2=(2*Math.PI*(i+1)/n)-Math.PI/2-.06;return "M "+(cx+R*Math.cos(a1))+" "+(cy+R*Math.sin(a1))+" A "+R+" "+R+" 0 0 1 "+(cx+R*Math.cos(a2))+" "+(cy+R*Math.sin(a2));});
  const lbls=segs.map((_,i)=>{const mid=(2*Math.PI*(i+.5)/n)-Math.PI/2;return{x:cx+60*Math.cos(mid),y:cy+60*Math.sin(mid)};});
  return <div style={{display:"flex",flexDirection:"column",alignItems:"center",padding:"12px 0"}}>
    <div style={{...LS,color:"var(--ice)",fontSize:8,marginBottom:10}}>NAVIGATE</div>
    <svg width="200" height="200" viewBox="0 0 200 200" style={{cursor:"default"}}>
      {arcs.map((d,i)=><path key={i} d={d} className="disc-seg" stroke={segs[i].color} style={{color:segs[i].color,cursor:"pointer"}} onClick={()=>goTo(segs[i].year)} data-clickable="true"/>)}
      {lbls.map((p,i)=><text key={i} x={p.x} y={p.y} textAnchor="middle" dominantBaseline="middle" style={{fontFamily:"var(--serif)",fontSize:12,fontWeight:700,fill:"var(--silver)",pointerEvents:"none"}}>{segs[i].year}</text>)}
      <circle cx={cx} cy={cy} r="34" fill="none" stroke="rgba(255,255,255,.06)" strokeWidth="1"/>
      <text x={cx} y={cy+3} textAnchor="middle" style={{fontFamily:"var(--serif)",fontSize:11,fontWeight:700,fill:"var(--silver)",fontStyle:"italic",pointerEvents:"none"}}>Arc</text>
    </svg>
  </div>;
}

/* SGPA Record */
function SGPARec({sgR,sSgR,em}){
  return <div><div style={{...LS,color:"var(--amethyst)",fontSize:8,marginBottom:12}}>SGPA RECORD</div>
    <div style={{display:"grid",gridTemplateColumns:"repeat(4,1fr)",gap:8}}>
      {sgR.map((v,i)=><div key={i} style={{display:"flex",flexDirection:"column",alignItems:"center",gap:4}}>
        <span style={{fontFamily:"var(--mono)",fontSize:9,color:"var(--sdim)"}}>S{i+1}</span>
        <input className="grade-inp" type="number" min="0" max="10" step=".01" value={v||""} onChange={e=>sSgR(p=>p.map((x,j)=>j===i?parseFloat(e.target.value)||0:x))} disabled={!em} style={{fontSize:18,fontFamily:"var(--serif)",fontWeight:700,width:52,color:v>0?"var(--amethyst)":"var(--sdim)",padding:"6px 4px"}}/>
      </div>)}
    </div>
    <div style={{marginTop:10,textAlign:"center",fontFamily:"var(--serif)",fontSize:13,color:"var(--sdim)"}}>CGPA: <span style={{color:"var(--amethyst)",fontWeight:700,fontSize:16}}>{(sgR.filter(x=>x>0).reduce((a,b)=>a+b,0)/(sgR.filter(x=>x>0).length||1)).toFixed(2)}</span></div>
  </div>;
}

/* Academic Radar */
function AcadR({dl,sDl,em,accent}){
  const ac=AC[accent]||AC.emerald;const[nn,sN]=useState("");const[nd,sD]=useState("");const t2=getToday();
  useEffect(()=>{const f=dl.filter(d=>d.date>=t2);if(f.length!==dl.length)sDl(f);},[t2]);
  const sorted=useMemo(()=>[...dl].sort((a,b)=>new Date(a.date)-new Date(b.date)),[dl]);
  const add=()=>{if(!nn.trim()||!nd)return;sDl(p=>[...p,{id:Date.now(),name:nn.trim(),date:nd}]);sN("");sD("");};
  return <div><div style={{...LS,color:ac.c,fontSize:8,marginBottom:12}}>ACADEMIC RADAR</div>
    {sorted.length===0&&<div style={{fontFamily:"var(--mono)",fontSize:11,color:"var(--sdim)",fontStyle:"italic",padding:"6px 0"}}>No deadlines</div>}
    {sorted.map(d=>{const dl2=daysTo(d.date);const urg=dl2<=3;return <div key={d.id} className="dl-row"><div style={{width:5,height:5,borderRadius:"50%",flexShrink:0,background:urg?"#FF5F5F":ac.c,animation:urg?"pulse 1.5s infinite":"none"}}/><span style={{fontFamily:"var(--mono)",fontSize:12,color:"var(--silver)",flex:1,overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap"}}>{d.name}</span><span style={{fontFamily:"var(--code)",fontSize:11,flexShrink:0,color:urg?"#FF5F5F":ac.c}}>{dl2===0?"TODAY":dl2+"d"}</span><span className="dl-rem" onClick={()=>sDl(p=>p.filter(x=>x.id!==d.id))} data-clickable="true">×</span></div>;})}
    {em&&<div style={{display:"flex",gap:6,alignItems:"center",paddingTop:10}}>
      <input value={nn} onChange={e=>sN(e.target.value)} onKeyDown={e=>{if(e.key==="Enter")add();}} placeholder="Name..." data-editable="true" className="tinp" style={{fontSize:12,flex:1,borderBottom:"1px solid rgba(255,255,255,.06)",padding:"4px 0"}}/>
      <input type="date" value={nd} onChange={e=>sD(e.target.value)} data-editable="true" className="tinp" style={{fontSize:10,width:90,borderBottom:"1px solid rgba(255,255,255,.06)"}}/>
      <span onClick={add} data-clickable="true" style={{...LS,fontSize:9,cursor:"pointer",padding:"4px 8px",border:"1px solid var(--border)"}} onMouseEnter={e=>{e.target.style.borderColor=ac.c;}} onMouseLeave={e=>{e.target.style.borderColor="var(--border)";}}>+</span>
    </div>}
  </div>;
}

/* Projects */
function ProjB({pr,sPr,em,accent}){
  const ac=AC[accent]||AC.pink;const[nn,sN]=useState("");
  const add=()=>{if(!nn.trim())return;sPr(p=>[...p,{id:Date.now(),name:nn.trim(),deployed:false}]);sN("");};
  return <div><div style={{...LS,color:ac.c,fontSize:8,marginBottom:12}}>ACTIVE PROJECTS</div>
    {pr.map((p,i)=><div key={p.id} style={{display:"flex",alignItems:"center",gap:8,padding:"8px 0",borderBottom:i<pr.length-1?"1px solid var(--border)":"none"}}>
      {p.deployed&&<div style={{width:5,height:5,borderRadius:"50%",background:ac.c,boxShadow:"0 0 6px "+ac.g,flexShrink:0}}/>}
      <span style={{fontFamily:"var(--mono)",fontSize:12,flex:1,color:p.deployed?ac.c:"var(--sdim)"}}>{p.name}</span>
      <span className="ptog" data-clickable="true" onClick={()=>sPr(x=>x.map(y=>y.id===p.id?{...y,deployed:!y.deployed}:y))} style={{color:p.deployed?"#000":ac.c,borderColor:p.deployed?ac.c:"rgba(255,255,255,.1)",background:p.deployed?ac.c:"transparent"}}>{p.deployed?"Deployed":"Building"}</span>
      {em&&<span onClick={()=>sPr(x=>x.filter(y=>y.id!==p.id))} data-clickable="true" style={{cursor:"pointer",color:"var(--sdim)",fontSize:13,opacity:.3}} onMouseEnter={e=>{e.target.style.opacity=1;}} onMouseLeave={e=>{e.target.style.opacity=.3;}}>×</span>}
    </div>)}
    {em&&<div style={{display:"flex",gap:6,paddingTop:10}}>
      <input value={nn} onChange={e=>sN(e.target.value)} onKeyDown={e=>{if(e.key==="Enter")add();}} placeholder="Project..." data-editable="true" className="tinp" style={{fontSize:12,flex:1,borderBottom:"1px solid rgba(255,255,255,.06)",padding:"4px 0"}}/>
      <span onClick={add} data-clickable="true" style={{...LS,fontSize:9,cursor:"pointer",padding:"4px 8px",border:"1px solid var(--border)"}} onMouseEnter={e=>{e.target.style.borderColor=ac.c;}} onMouseLeave={e=>{e.target.style.borderColor="var(--border)";}}>+</span>
    </div>}
  </div>;
}

function BentoGrid({bk,sBk,em,sgR,sSgR,dl,sDl,pr,sPr}){
  const[ref,iv]=useIV(.06);
  const card={background:"rgba(255,255,255,.015)",border:"1px solid var(--border)",borderRadius:6,padding:"24px 22px"};
  return <section ref={ref} style={{position:"relative",padding:"20px 32px 60px",overflow:"hidden"}}>
    <div style={{maxWidth:1100,margin:"0 auto",position:"relative",zIndex:1}}>
      <div className="bento" style={{display:"grid",gridTemplateColumns:"1fr 1fr 1fr",gap:20,opacity:iv?1:0,transition:"opacity 1s .2s"}}>
        <div style={{...card,gridColumn:"span 2"}}><ReadList bk={bk} sBk={sBk} em={em}/></div>
        <div style={{...card}}><NavDisc/></div>
        <div style={{...card}}><SGPARec sgR={sgR} sSgR={sSgR} em={em}/></div>
        <div style={{...card}}><AcadR dl={dl} sDl={sDl} em={em} accent="emerald"/></div>
        <div style={{...card}}><ProjB pr={pr} sPr={sPr} em={em} accent="pink"/></div>
      </div>
    </div>
  </section>;
}

/* ═══════════════════════════════════════════════
   5. ROPE TIMELINE — Proper alternating grid
   ═══════════════════════════════════════════════ */
function RopeTimeline({tl,em,onUpd}){
  const[ref,iv]=useIV(.05);

  return <section ref={ref} style={{position:"relative",padding:"40px 32px 80px",overflow:"hidden"}}>
    <div style={{maxWidth:960,margin:"0 auto",position:"relative",zIndex:1,opacity:iv?1:0,transition:"opacity 1s .1s"}}>
      {tl.map((ph,idx)=>{
        const ac=AC[ph.accent]||AC.emerald;
        const isLeft=idx%2===0;
        const dc=ph.items.filter(i=>i.done).length;

        return <div key={ph.id} id={"phase-"+ph.year} className="rope-row" style={{
          display:"grid",gridTemplateColumns:"1fr 40px 1fr",gap:0,marginBottom:48,minHeight:120
        }}>
          {/* Left content or empty */}
          <div style={{display:"flex",justifyContent:"flex-end",paddingRight:32,textAlign:"right"}}>
            {isLeft && <PhaseCard ph={ph} ac={ac} dc={dc} em={em} onUpd={onUpd} align="right"/>}
          </div>

          {/* Center line + dot */}
          <div className="rope-line" style={{display:"flex",flexDirection:"column",alignItems:"center",position:"relative"}}>
            <div style={{width:2,flex:1,background:"linear-gradient(180deg,"+ac.d+",rgba(255,255,255,.06))"}}/>
            <div style={{width:14,height:14,borderRadius:"50%",border:"2px solid "+ac.c,background:"#000",flexShrink:0,zIndex:2,boxShadow:"0 0 8px "+ac.d}}/>
            <div style={{width:2,flex:1,background:"linear-gradient(180deg,rgba(255,255,255,.06),"+ac.d+")"}}/>
          </div>

          {/* Right content or empty */}
          <div style={{display:"flex",justifyContent:"flex-start",paddingLeft:32}}>
            {!isLeft && <PhaseCard ph={ph} ac={ac} dc={dc} em={em} onUpd={onUpd} align="left"/>}
          </div>
        </div>;
      })}
    </div>
  </section>;
}

function PhaseCard({ph,ac,dc,em,onUpd,align}){
  const ta=align==="right"?"right":"left";
  return <div style={{maxWidth:380,width:"100%",textAlign:ta}}>
    <div style={{...LS,color:ac.c,fontSize:8,marginBottom:6}}>{ph.phase.toUpperCase()}</div>
    <div style={{fontFamily:"var(--serif)",fontSize:36,fontWeight:900,color:"#fff",lineHeight:1,marginBottom:4}}>{ph.year}</div>
    <Ed value={ph.sub} onChange={v=>onUpd({...ph,sub:v})} em={em} tag="div" style={{fontFamily:"var(--body)",fontSize:14,fontStyle:"italic",color:"var(--sdim)",marginBottom:16,lineHeight:1.4}}/>
    <div style={{...LS,fontSize:8,color:ac.c,marginBottom:10}}>{dc}/{ph.items.length}</div>
    <div style={{textAlign:"left"}}>
      {ph.items.map((it,ii)=><div key={it.id} style={{display:"flex",alignItems:"center",gap:10,padding:"6px 0",borderBottom:ii<ph.items.length-1?"1px solid var(--border)":"none"}}>
        <Ck checked={it.done} onChange={()=>onUpd({...ph,items:ph.items.map(x=>x.id===it.id?{...x,done:!x.done}:x)})} accent={ac.c}>
          <Ed value={it.text} onChange={v=>onUpd({...ph,items:ph.items.map(x=>x.id===it.id?{...x,text:v}:x)})} em={em} style={{fontFamily:"var(--body)",fontSize:14,display:"inline"}}/>
        </Ck>
        {em&&<span onClick={()=>onUpd({...ph,items:ph.items.filter(x=>x.id!==it.id)})} data-clickable="true" style={{cursor:"pointer",color:"var(--sdim)",fontSize:12,marginLeft:"auto",opacity:.3}} onMouseEnter={e=>{e.target.style.opacity=1;}} onMouseLeave={e=>{e.target.style.opacity=.3;}}>×</span>}
      </div>)}
    </div>
    {em&&<div onClick={()=>onUpd({...ph,items:[...ph.items,{id:Date.now(),text:"New milestone",done:false}]})} data-clickable="true" style={{marginTop:8,...LS,fontSize:9,color:"var(--sdim)",cursor:"pointer",textAlign:"left"}} onMouseEnter={e=>{e.currentTarget.style.color=ac.c;}} onMouseLeave={e=>{e.currentTarget.style.color="var(--sdim)";}}>+ Add</div>}
  </div>;
}

/* ═══════════════════════════════════════════════
   APP
   ═══════════════════════════════════════════════ */
export default function App(){
  const[loaded,sLd]=useState(false);const[em,sEm]=useState(false);
  const[ct,sCt]=useState(D_CT);const[bk,sBk]=useState(D_BK);const[tl,sTl]=useState(D_TL);
  const[hb,sHb]=useState(D_HB);const[pr,sPr]=useState(D_PR);
  const[dl,sDl]=useState([]);const[co,sCo]=useState(D_CO);const[tt,sTt]=useState(mkTT());
  const[dt,sDt]=useState([{text:"",ch:false},{text:"",ch:false},{text:"",ch:false},{text:"",ch:false},{text:"",ch:false}]);
  const[hm,sHm]=useState({});const[sgR,sSgR]=useState(D_SGR);
  const sRef=useRef(null);

  useEffect(()=>{(async()=>{const d=await loadD("kunal-v8",null);if(d){if(d.ct)sCt(d.ct);if(d.bk)sBk(d.bk);if(d.tl)sTl(d.tl);if(d.hb)sHb(d.hb);if(d.pr)sPr(d.pr);if(d.dl)sDl(d.dl);if(d.co)sCo(d.co);if(d.tt)sTt(d.tt);if(d.dt)sDt(d.dt);if(d.hm)sHm(d.hm);if(d.sgR)sSgR(d.sgR);}sLd(true);})();},[]);

  useEffect(()=>{if(!loaded)return;(async()=>{const t=getToday();const last=await loadD("kunal-v8-d","");if(last&&last!==t){sHm(p=>({...p,[last]:dt.filter(x=>x.ch).length}));sDt([{text:"",ch:false},{text:"",ch:false},{text:"",ch:false},{text:"",ch:false},{text:"",ch:false}]);sHb(p=>p.map(h=>({...h,ch:false})));}await saveD("kunal-v8-d",t);})();},[loaded]);

  useEffect(()=>{if(!loaded)return;const t=setTimeout(()=>saveD("kunal-v8",{ct,bk,tl,hb,pr,dl,co,tt,dt,hm,sgR}),500);return()=>clearTimeout(t);},[ct,bk,tl,hb,pr,dl,co,tt,dt,hm,sgR,loaded]);

  if(!loaded)return <div style={{minHeight:"100vh",background:"#000",display:"flex",alignItems:"center",justifyContent:"center"}}><style>{CSS}</style><div style={{fontFamily:"var(--serif)",fontStyle:"italic",fontSize:16,color:"var(--sdim)",animation:"fadeSlow 1s both"}}>Loading...</div></div>;

  return(
    <div className={em?"edon":""} style={{height:"100vh",overflow:"hidden",background:"#000",cursor:"none"}}>
      <style>{CSS}</style>
      <Cur/>
      <div className="gg"/>
      <div className={em?"et on":"et"} onClick={()=>sEm(m=>!m)} data-clickable="true">
        <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke={em?"var(--ice)":"var(--sdim)"} strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="11" width="18" height="11" rx="2"/>{em?<path d="M7 11V7a5 5 0 0110 0v4"/>:<path d="M7 11V7a5 5 0 019.9-1"/>}</svg>
        <span style={{fontFamily:"var(--mono)",fontSize:9,letterSpacing:".2em",textTransform:"uppercase",color:em?"var(--ice)":"var(--sdim)"}}>{em?"Editing":"Locked"}</span>
      </div>
      <SS innerRef={sRef}>
        <Hero ct={ct} sCt={sCt} em={em} hb={hb} sHb={sHb} dt={dt} sDt={sDt} hm={hm}/>
        <Div label="TIMETABLE"/>
        <Timetable tt={tt} sTt={sTt} em={em}/>
        <Div label="GRADES"/>
        <SGPACalc co={co} sCo={sCo} em={em}/>
        <Div label="MISSION CONTROL"/>
        <BentoGrid bk={bk} sBk={sBk} em={em} sgR={sgR} sSgR={sSgR} dl={dl} sDl={sDl} pr={pr} sPr={sPr}/>
        <Div label="THE ARC · 2026–2030"/>
        <RopeTimeline tl={tl} em={em} onUpd={u=>sTl(p=>p.map(x=>x.id===u.id?u:x))}/>
        <footer style={{textAlign:"center",padding:"60px 32px 36px"}}><div style={{width:1,height:36,background:"linear-gradient(180deg,rgba(255,255,255,.08),transparent)",margin:"0 auto 20px"}}/><div style={{fontFamily:"var(--serif)",fontStyle:"italic",fontSize:14,color:"var(--sdim)",maxWidth:440,margin:"0 auto",lineHeight:1.7}}>"Top 1% is built in silence."</div><div style={{marginTop:20,...LS,fontSize:8,letterSpacing:".3em"}}>KUNAL · 2026–2031</div></footer>
      </SS>
    </div>
  );
}
