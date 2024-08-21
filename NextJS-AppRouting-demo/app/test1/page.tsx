import Link from 'next/link'

function Page() {
    return (
        <ul>
            <li>
                <Link href="/test1">/test1</Link>
            </li>
            <li>
                <Link href="/test1/test4">/test1</Link>
            </li>
            <li>
                <Link href="/test1/test3">/test1/test3</Link>
            </li>
            <li>
                <Link href="/test2">test2</Link>
            </li>
        </ul>
    )
}

export default Page