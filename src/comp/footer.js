import React from 'react'
import './footer.css'
import { FaPiggyBank, FaShippingFast, FaHeadphonesAlt, FaWallet} from 'react-icons/fa';
const Footer = () => {
    return (
        <>
            <div className='footer'>
                <div className='container'>
                    <div className='right_box'>
                        <div className='header'>
                            {/*<img src='image/logo0.png' alt=''></img>*/}
                            <p>MUSIC LIFESTYLE</p>
                        </div>
                        <div className='bottom'>
                            <div className='box'>
                                <h3>Contact us</h3>
                                <ul>
                                    <li>distrct 6, cput, cape town campus</li>
                                    <li>+(27)62 884 6658</li>
                                    <li>217094740@mycput.ac.za</li>
                                </ul>
                            </div>
                            {/*<div className='box'>*/}
                            {/*    <h3>products</h3>*/}
                            {/*    <ul>*/}
                            {/*    <li>Delivery</li>*/}
                            {/*        <li>Track Oder</li>*/}
                            {/*        <li>New product</li>*/}
                            {/*        <li>old product</li>*/}
                            {/*    </ul>*/}
                            {/*</div>*/}
                            {/*<div className='box'>*/}
                            {/*    <h3>contact us</h3>*/}
                            {/*    <ul>*/}
                            {/*        <li>distrct 6, cput, cape town campus</li>*/}
                            {/*        <li>+(27)62 884 6658</li>*/}
                            {/*        <li>217094740@mycput.ac.za</li>*/}
                            {/*    </ul>*/}
                            {/*</div>*/}
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Footer