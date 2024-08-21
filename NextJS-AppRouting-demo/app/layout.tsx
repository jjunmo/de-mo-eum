import './global.css';

export const metadata = {
    title: 'Demo',
    description: 'Demo application',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="en">
        <body>
        <div className="container">
            {children}
        </div>
        </body>
        </html>
    );
}