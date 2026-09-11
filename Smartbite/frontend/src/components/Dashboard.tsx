import { useEffect, useState } from 'react';

type Product = {
  id: string;
  productName: string;
  estimatedExpirationDate: string;
};

export default function Dashboard({ refreshTrigger }: { refreshTrigger: number }) {
  const [products, setProducts] = useState<Product[]>([]);

  // Dynamic Icon based on product name
  const getProductIcon = (name: string) => {
    const lower = name.toLowerCase();
    if (lower.includes('poulet') || lower.includes('hähnchen') || lower.includes('chicken')) return "🍗";
    if (lower.includes('milch') || lower.includes('milk')) return "🥛";
    if (lower.includes('broccoli') || lower.includes('brokkoli')) return "🥦";
    return "📦"; // default
  };

  // Calculate roughly for mock styling
  const getFreshnessStatus = (dateStr: string) => {
    if (dateStr.includes("20")) return { color: "text-amber-400", bg: "bg-amber-400/10", border: "border-amber-400/20", label: "Expiring Soon" };
    if (dateStr.includes("23")) return { color: "text-emerald-400", bg: "bg-emerald-400/10", border: "border-emerald-400/20", label: "Fresh" };
    return { color: "text-cyan-400", bg: "bg-cyan-400/10", border: "border-cyan-400/20", label: "Good" };
  };

  const fetchInventory = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/inventory');
      const data = await res.json();
      setProducts(data || []);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    fetchInventory();
  }, [refreshTrigger]);

  return (
    <div className="glass-card min-h-[400px] flex flex-col">
      <div className="flex items-center justify-between mb-8">
        <div>
          <h2 className="text-3xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-white to-white/70">Inventory</h2>
          <p className="text-cyan-100/50 text-sm mt-1">Real-time stock tracking</p>
        </div>
        <div className="bg-cyan-500/10 border border-cyan-500/20 px-5 py-2.5 rounded-xl shadow-inner">
          <span className="text-cyan-300 font-extrabold text-xl">{products.length}</span> <span className="text-cyan-100/60 text-sm font-medium">Items</span>
        </div>
      </div>

      {products.length === 0 ? (
        <div className="flex-1 flex flex-col items-center justify-center text-center p-8 animate-fade-in">
          <div className="w-24 h-24 mb-6 rounded-full bg-gradient-to-tr from-cyan-500/10 to-blue-500/5 flex items-center justify-center border border-white/5 shadow-[0_0_50px_rgba(6,182,212,0.1)]">
            <svg className="w-10 h-10 text-cyan-400/30" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"></path></svg>
          </div>
          <h3 className="text-xl font-semibold text-white/80">Your fridge is empty</h3>
          <p className="text-white/40 mt-2 max-w-[250px] text-sm leading-relaxed">Use the Vision AI scanner to digitize your receipts and track your food.</p>
        </div>
      ) : (
        <ul className="grid grid-cols-1 md:grid-cols-2 gap-4 animate-fade-in">
          {products.map(p => {
            const status = getFreshnessStatus(p.estimatedExpirationDate);
            return (
              <li key={p.id} className="group bg-white/[0.03] rounded-2xl p-5 border border-white/5 hover:border-white/10 hover:bg-white/[0.06] transition-all duration-500 flex flex-col gap-3 relative overflow-hidden">
                {/* Glowing orb on hover */}
                <div className="absolute -inset-24 bg-gradient-to-r from-cyan-400/0 via-cyan-400/0 to-cyan-400/0 group-hover:from-cyan-400/5 group-hover:via-blue-500/5 transition-all duration-700 blur-xl"></div>
                
                <div className="flex justify-between items-start z-10">
                  <div className="flex items-center gap-4">
                    <div className={`w-12 h-12 rounded-xl ${status.bg} ${status.border} border flex items-center justify-center shadow-inner`}>
                      <span className="text-2xl drop-shadow-md">{getProductIcon(p.productName)}</span>
                    </div>
                    <div>
                      <span className="font-bold text-lg text-white/90 block">{p.productName}</span>
                      <span className="text-[11px] text-white/30 font-mono tracking-widest uppercase">ID: {p.id.padStart(4, '0')}</span>
                    </div>
                  </div>
                </div>

                <div className="mt-2 pt-3 border-t border-white/5 flex justify-between items-center z-10">
                  <div className="flex items-center gap-2">
                    <svg className="w-4 h-4 text-white/30" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path></svg>
                    <span className="text-sm text-white/60 font-medium">{p.estimatedExpirationDate}</span>
                  </div>
                  <span className={`text-[11px] px-3 py-1 rounded-full border ${status.bg} ${status.border} ${status.color} font-bold tracking-wide uppercase`}>
                    {status.label}
                  </span>
                </div>
              </li>
            )
          })}
        </ul>
      )}
    </div>
  );
}
